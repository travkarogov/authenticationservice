package twitsec.authenticationservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import twitsec.authenticationservice.service.exception.*;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvice {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity emailExistsException(final EmailExistsException e){
        log.error(String.format("EmailExistsException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(EmailInvalidException.class)
    public ResponseEntity emailInvalidException(final EmailInvalidException e){
        log.error(String.format("EmailInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity inputInvalidException(final InputInvalidException e){
        log.error(String.format("InputInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity passwordInvalidException(final PasswordInvalidException e){
        log.error(String.format("PasswordInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity tokenInvalidException(final TokenInvalidException e){
        log.error(String.format("TokenInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(TwoFactorInvalidException.class)
    public ResponseEntity twoFactorInvalidException(final TwoFactorInvalidException e){
        log.error(String.format("TwoFactorInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
