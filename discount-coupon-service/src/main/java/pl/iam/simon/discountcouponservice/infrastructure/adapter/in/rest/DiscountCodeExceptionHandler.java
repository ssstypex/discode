package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;

import java.util.List;

@RestControllerAdvice
public class DiscountCodeExceptionHandler {

    public record ErrorResponse(List<String> errors) {}

    @ExceptionHandler(DiscountCodeValidationException.class)
    public ResponseEntity<ErrorResponse> handleDiscountCodeValidationException(
            DiscountCodeValidationException ex) {

        return ResponseEntity
                .status(resolveStatus(ex.getValidationError()))
                .body(new ErrorResponse( List.of(ex.getMessage())));
    }

    private HttpStatus resolveStatus(DiscountCodeValidationError error) {
        return switch (error) {
            case UNKNOWN -> HttpStatus.NOT_FOUND;
            case CODE_EXIST -> HttpStatus.CONFLICT;
            case WRONG_COUNTRY, USER_USAGE_LIMIT, USAGE_LIMIT -> HttpStatus.UNPROCESSABLE_ENTITY;
        };
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
