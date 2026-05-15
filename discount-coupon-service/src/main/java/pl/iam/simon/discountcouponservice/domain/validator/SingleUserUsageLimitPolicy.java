package pl.iam.simon.discountcouponservice.domain.validator;

import lombok.RequiredArgsConstructor;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.domain.model.UserId;
import pl.iam.simon.discountcouponservice.domain.port.out.GetCurrentUserProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.GetDiscountCodeUsageProvider;

@RequiredArgsConstructor
public class SingleUserUsageLimitPolicy implements DiscountCodePolicy {

    private final GetCurrentUserProvider currentUserProvider;
    private final GetDiscountCodeUsageProvider usageProvider;

    @Override
    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {
        if(!validateWithResult(discountCode).isValid()) {
          throw new DiscountCodeValidationException(DiscountCodeValidationError.USER_USAGE_LIMIT);
        }
    }

    @Override
    public DiscountCodePolicyValidationResult validateWithResult(DiscountCode discountCode) {
        UserId userId = currentUserProvider.getCurrentUserId();
        return usageProvider.hasUserUsedCode(userId, discountCode)
                ? DiscountCodePolicyValidationResult.invalid(
                        new DiscountCodePolicyError(DiscountCodeValidationError.USER_USAGE_LIMIT))
                : DiscountCodePolicyValidationResult.valid();
    }
}
