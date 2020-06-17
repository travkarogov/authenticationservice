package twitsec.authenticationservice.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TwoFactorInvalidException extends RuntimeException {
    public TwoFactorInvalidException(final String message) {super(message);}
}
