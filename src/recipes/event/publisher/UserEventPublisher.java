package recipes.event.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import recipes.domain.User;
import recipes.event.type.UnblockUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Async
    public void publishUnlockUserEvent(String email) {
        this.applicationEventPublisher.publishEvent(new UnblockUser(email));
    }
}
