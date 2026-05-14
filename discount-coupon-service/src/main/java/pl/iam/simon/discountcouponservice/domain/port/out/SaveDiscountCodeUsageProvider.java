package pl.iam.simon.discountcouponservice.domain.port.out;

import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.UserId;

public interface SaveDiscountCodeUsageProvider {
    void save(DiscountCode discountCode, UserId userId);
}
