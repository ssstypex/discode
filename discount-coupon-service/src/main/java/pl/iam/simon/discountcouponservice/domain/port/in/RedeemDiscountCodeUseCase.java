package pl.iam.simon.discountcouponservice.domain.port.in;

import pl.iam.simon.discountcouponservice.domain.validator.DiscountCodePolicyValidationResult;

public interface RedeemDiscountCodeUseCase {
    void redeemDiscountCode(RedeemDiscountCodeInput discountCodeInput);
    DiscountCodePolicyValidationResult canBeUsed(RedeemDiscountCodeInput discountCodeInput);
}