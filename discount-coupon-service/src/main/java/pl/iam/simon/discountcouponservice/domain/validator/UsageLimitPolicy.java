package pl.iam.simon.discountcouponservice.domain.validator;

import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;

public class UsageLimitPolicy implements DiscountCodePolicy {

    @Override
    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {
        if(!discountCode.isAvailable()) {
            throw new DiscountCodeValidationException(DiscountCodeValidationError.USAGE_LIMIT);
        }
    }
}
