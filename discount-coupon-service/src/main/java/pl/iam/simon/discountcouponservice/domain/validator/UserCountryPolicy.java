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
        if(!userCountryProvider.getUserCountry().equals(discountCode.getCountry())) {
            throw new DiscountCodeValidationException(DiscountCodeValidationError.WRONG_COUNTRY);
        }
    }
}
