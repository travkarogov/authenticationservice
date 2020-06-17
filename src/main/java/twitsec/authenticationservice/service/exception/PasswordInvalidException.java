package twitsec.authenticationservice.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(final String message) {super(message);}
}
