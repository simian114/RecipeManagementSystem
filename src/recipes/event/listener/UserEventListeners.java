package recipes.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import recipes.event.type.UnblockUser;
import recipes.service.UserService;
import recipes.web.exception.exceptions.notFound.NotFoundBasicException;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserEventListeners {
    private final UserService userService;

    @EventListener
    public void unblockUserHandler(UnblockUser unblockUser) {
        try {
            this.userService.unblockUser(unblockUser.email());
        } catch (NotFoundBasicException e) {
            log.error("unblockUser failed", e);
        }
    }
}
