package recipes.security.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import recipes.domain.User;
import recipes.security.adapter.UserAdapter;
import recipes.security.service.BlockUserService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEventsListener {
    private final BlockUserService blockUserService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = ((UserAdapter) event.getAuthentication().getPrincipal()).getUser();
        this.blockUserService.loginSucceeded(user.getEmail(), request.getRequestURI());
        log.info("authentication success!");
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        log.info("authentication failure!");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String email = event.getAuthentication().getName();
        this.blockUserService.loginFailed(email, request.getRequestURI());
    }
}
