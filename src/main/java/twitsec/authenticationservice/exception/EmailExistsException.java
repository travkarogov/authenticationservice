package twitsec.authenticationservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailExistsException extends RuntimeException {
    public EmailExistsException(final String message) {super(message);}
}