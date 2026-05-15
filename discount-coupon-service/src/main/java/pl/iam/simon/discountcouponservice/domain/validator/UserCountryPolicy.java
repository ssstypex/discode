package pl.iam.simon.discountcouponservice.domain.validator;

import lombok.RequiredArgsConstructor;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.domain.port.out.GetUserCountryProvider;

@RequiredArgsConstructor
public class UserCountryPolicy implements DiscountCodePolicy {

    private final GetUserCountryProvider userCountryProvider;

    @Override
    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {
        if(!validateWithResult(discountCode).isValid()) {
            throw new DiscountCodeValidationException(DiscountCodeValidationError.WRONG_COUNTRY);
        }
    }

    @Override
    public DiscountCodePolicyValidationResult validateWithResult(DiscountCode discountCode) {
        return userCountryProvider.getUserCountry().equals(discountCode.getCountry())
                ? DiscountCodePolicyValidationResult.valid()
                : DiscountCodePolicyValidationResult.invalid(
                        new DiscountCodePolicyError(DiscountCodeValidationError.WRONG_COUNTRY));
    }
}
