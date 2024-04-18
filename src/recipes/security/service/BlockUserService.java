package recipes.security.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recipes.service.UserService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * if fail more than 5, then block user
 * use cache... -> this is bad for
 */
@Slf4j
@Component
public class BlockUserService {
    private static final int MAX_FAIL_COUNT = 5;
    private final LoadingCache<String, Integer> attemptCache;
    private final UserService userService;

    @Autowired
    public BlockUserService(UserService userService) {
        this.attemptCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
        this.userService = userService;
    }

    public void loginSucceeded(String email, final String ipAddress) {
        this.attemptCache.put(this.getCacheKey(email, ipAddress), 0);
    }

    // use email & ip address
    public void loginFailed(String email, final String ipAddress) {
        String key = getCacheKey(email, ipAddress);
        int attempt;
        try {
            attempt = attemptCache.get(key);
        } catch (ExecutionException e) {
            attempt = 0;
        }
        attempt++;
        attemptCache.put(key, attempt);
        if (attempt < MAX_FAIL_COUNT) {
            return;
        }
        try {
            this.userService.blockUser(email);
        } catch (Exception e) {
            log.error("failed lock user", e);
        } finally {
            this.attemptCache.put(key, 0);
        }
    }

    private String getCacheKey(final String email, final String ipAddress) {
        return email + ipAddress;
    }
}
