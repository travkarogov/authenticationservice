package twitsec.authenticationservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Order
public class LastResortControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleLastResortException(final Exception e) {
        log.error(String.format("LastResortException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleLastResortRuntimeException(final RuntimeException e) {
        log.error(String.format("LastResortRuntimeException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
