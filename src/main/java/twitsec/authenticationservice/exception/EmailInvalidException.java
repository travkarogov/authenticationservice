package twitsec.authenticationservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailInvalidException extends RuntimeException {
    public EmailInvalidException(final String message) {super(message);}
}
