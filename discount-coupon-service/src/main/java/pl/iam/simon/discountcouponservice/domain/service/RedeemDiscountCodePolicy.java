package pl.iam.simon.discountcouponservice.domain.service;

import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.port.out.GetCurrentUserProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.GetDiscountCodeUsageProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.GetUserCountryProvider;
import pl.iam.simon.discountcouponservice.domain.validator.*;

import java.util.List;

public class RedeemDiscountCodePolicy implements DiscountCodePolicy {

    private final List<DiscountCodePolicy> validators;

    public RedeemDiscountCodePolicy(GetUserCountryProvider countryProvider, GetCurrentUserProvider currentUserProvider,
                                    GetDiscountCodeUsageProvider usageProvider) {
        this.validators = List.of(
                new UsageLimitPolicy(),
                new UserCountryPolicy(countryProvider),
                new SingleUserUsageLimitPolicy(currentUserProvider, usageProvider)
        );
    }

    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {
        validators.forEach(validator -> validator.validate(discountCode));
    }

    @Override
    public DiscountCodePolicyValidationResult validateWithResult(DiscountCode discountCode) {
        return this.validators.stream()
                .map(validator -> validator.validateWithResult(discountCode))
                .reduce(DiscountCodePolicyValidationResult.valid(), DiscountCodePolicyValidationResult::merge);
    }
}
