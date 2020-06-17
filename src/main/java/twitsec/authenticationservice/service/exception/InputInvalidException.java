package twitsec.authenticationservice.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InputInvalidException extends RuntimeException {
    public InputInvalidException(final String message) {super(message);}
}
