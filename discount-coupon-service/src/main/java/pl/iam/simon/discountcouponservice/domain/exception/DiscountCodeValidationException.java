package pl.iam.simon.discountcouponservice.domain.exception;

import lombok.Getter;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;

@Getter
public class DiscountCodeValidationException extends RuntimeException {
    private final DiscountCodeValidationError validationError;

    public DiscountCodeValidationException(DiscountCodeValidationError validationError) {
        super(validationError.getMessage());
        this.validationError = validationError;
    }
}
