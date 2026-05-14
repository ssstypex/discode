package pl.iam.simon.discountcouponservice.domain.validator;

import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;

public interface DiscountCodePolicy {
    void validate(DiscountCode discountCode) throws DiscountCodeValidationException;
}
