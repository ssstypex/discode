package pl.iam.simon.discountcouponservice.domain.port.out;

import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;

import java.util.Optional;

public interface RedeemDiscountCodeDataSourceProvider {

    Optional<DiscountCode> findByCodeAndCountry(DiscountCodeValue discountCodeValue, Country country);
    void redeemDiscountCode(DiscountCode discountCode);
}
