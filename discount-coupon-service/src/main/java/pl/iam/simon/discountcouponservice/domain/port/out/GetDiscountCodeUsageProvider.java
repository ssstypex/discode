package pl.iam.simon.discountcouponservice.domain.port.out;

import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.UserId;

public interface GetDiscountCodeUsageProvider {
    boolean hasUserUsedCode(UserId userId, DiscountCode discountCode);
}
