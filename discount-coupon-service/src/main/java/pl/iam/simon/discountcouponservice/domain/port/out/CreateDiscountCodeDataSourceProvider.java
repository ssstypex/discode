package pl.iam.simon.discountcouponservice.domain.port.out;

import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;

public interface CreateDiscountCodeDataSourceProvider {

    void createDiscountCode(DiscountCode discountCode);
}
