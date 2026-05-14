package pl.iam.simon.discountcouponservice.domain.port.in;

import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;

public record RedeemDiscountCodeInput(DiscountCodeValue codeValue, Country country){}
