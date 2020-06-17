package twitsec.authenticationservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import twitsec.authenticationservice.exception.*;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpControllerAdvice {

    private static final HttpStatus CONFLICT = HttpStatus.CONFLICT;
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<HttpStatus> emailExistsException(final EmailExistsException e){
        log.error(String.format("EmailExistsException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(CONFLICT).body(CONFLICT);
    }

    @ExceptionHandler(EmailInvalidException.class)
    public ResponseEntity<HttpStatus> emailInvalidException(final EmailInvalidException e){
        log.error(String.format("EmailInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(BAD_REQUEST).body(BAD_REQUEST);
    }

    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<HttpStatus> inputInvalidException(final InputInvalidException e){
        log.error(String.format("InputInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(BAD_REQUEST).body(BAD_REQUEST);
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<HttpStatus> passwordInvalidException(final PasswordInvalidException e){
        log.error(String.format("PasswordInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<HttpStatus> tokenInvalidException(final TokenInvalidException e){
        log.error(String.format("TokenInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }

    @ExceptionHandler(TwoFactorInvalidException.class)
    public ResponseEntity<HttpStatus> twoFactorInvalidException(final TwoFactorInvalidException e){
        log.error(String.format("TwoFactorInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }
}
