package recipes.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.domain.User;
import recipes.event.type.UnblockUser;
import recipes.repository.UserRepository;
import recipes.security.adapter.UserAdapter;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("load user by username email: {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " is not found"));
        if (user.getBlock() != null && user.getBlock().isBefore(LocalDateTime.now())) {
            this.applicationEventPublisher.publishEvent(new UnblockUser(email));
        }
        return new UserAdapter(user);
    }
}
