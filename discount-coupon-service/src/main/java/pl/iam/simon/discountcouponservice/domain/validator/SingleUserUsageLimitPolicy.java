package pl.iam.simon.discountcouponservice.domain.validator;

import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;

public class SingleUserUsageLimitPolicy implements DiscountCodePolicy {

    @Override
    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {

    }

}
