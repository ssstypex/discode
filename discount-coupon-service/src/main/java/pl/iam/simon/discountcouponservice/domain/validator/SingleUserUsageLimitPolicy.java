package pl.iam.simon.discountcouponservice.domain.validator;

import lombok.RequiredArgsConstructor;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.domain.model.UserId;
import pl.iam.simon.discountcouponservice.domain.port.out.GetCurrentUserProvider;

@RequiredArgsConstructor
public class SingleUserUsageLimitPolicy implements DiscountCodePolicy {

    private final GetCurrentUserProvider currentUserProvider;
    private final GetDiscountCodeUsageProvider usageProvider;

    @Override
    public void validate(DiscountCode discountCode) throws DiscountCodeValidationException {
        UserId userId = currentUserProvider.getCurrentUserId();
        if(usageProvider.hasUserUsedCode(userId, discountCode)) {
          throw new DiscountCodeValidationException(DiscountCodeValidationError.USER_USAGE_LIMIT);
        }
    }

}
