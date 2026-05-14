package pl.iam.simon.discountcouponservice.domain.exception;

import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;

public class DiscountCodeValidationException extends RuntimeException {

    public DiscountCodeValidationException(DiscountCodeValidationError validationError) {
        super(validationError.getMessage());
    }
}
