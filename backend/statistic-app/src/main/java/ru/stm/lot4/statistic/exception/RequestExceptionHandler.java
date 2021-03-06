package ru.stm.lot4.statistic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.stm.lot4.statistic.dto.ApiValidationError;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
        log.error("Validation exception {}", ex.getMessage());
        ApiValidationError validationError = new ApiValidationError();
        ((ConstraintViolationException) ex).getConstraintViolations()
                .forEach(constraintViolation -> validationError.addError(
                        String.valueOf(constraintViolation.getPropertyPath()),
                        String.valueOf(constraintViolation.getInvalidValue()),
                        constraintViolation.getMessage()));
        validationError.setMessage(ex.getMessage());
        validationError.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)/**/.body(validationError);
    }
}
