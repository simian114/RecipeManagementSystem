package recipes.web.exception.exceptions.badRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestBasicException extends RuntimeException {
    public BadRequestBasicException() {
        super("bad request");
    }

    public BadRequestBasicException(String message) {
        super(message);
    }
}
