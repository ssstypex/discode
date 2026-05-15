package pl.iam.simon.discountcouponservice.domain.validator;

import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;

public class UsageLimitPolicy implements DiscountCodePolicy {

    @Override
    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {
        if(!validateWithResult(discountCode).isValid()) {
            throw new DiscountCodeValidationException(DiscountCodeValidationError.USAGE_LIMIT);
        }
    }

    @Override
    public DiscountCodePolicyValidationResult validateWithResult(DiscountCode discountCode) {
        return discountCode.isAvailable()
                ? DiscountCodePolicyValidationResult.valid()
                : DiscountCodePolicyValidationResult.invalid(new DiscountCodePolicyError(DiscountCodeValidationError.USAGE_LIMIT));
    }
}
