package pl.iam.simon.discountcouponservice.domain.port.out;

import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;

public interface CreateDiscountCodeDataSourceProvider {

    void createDiscountCode(DiscountCode discountCode);
    boolean codeExist(DiscountCodeValue discountCodeValue, Country country);
}
