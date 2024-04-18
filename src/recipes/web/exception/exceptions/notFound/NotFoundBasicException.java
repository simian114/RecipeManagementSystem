package recipes.web.exception.exceptions.notFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundBasicException extends RuntimeException {
    public NotFoundBasicException() {
        super("not found");
    }

    public NotFoundBasicException(String message) {
        super(message);
    }
}
