package pl.iam.simon.discountcouponservice.application.redeem;

import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.port.out.GetUserCountryProvider;
import pl.iam.simon.discountcouponservice.domain.validator.DiscountCodePolicy;
import pl.iam.simon.discountcouponservice.domain.validator.SingleUserUsageLimitPolicy;
import pl.iam.simon.discountcouponservice.domain.validator.UsageLimitPolicy;
import pl.iam.simon.discountcouponservice.domain.validator.UserCountryPolicy;

import java.util.List;

public class RedeemDiscountCodePolicy {

    private final List<DiscountCodePolicy> validators;

    public RedeemDiscountCodePolicy(GetUserCountryProvider countryProvider) {
        this.validators = List.of(
                new UsageLimitPolicy(),
                new UserCountryPolicy(countryProvider),
                new SingleUserUsageLimitPolicy()
        );
    }

    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {
        validators.forEach(validator -> validator.validate(discountCode));
    }
}
