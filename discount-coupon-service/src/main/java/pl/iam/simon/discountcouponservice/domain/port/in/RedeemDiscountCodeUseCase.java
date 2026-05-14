package pl.iam.simon.discountcouponservice.domain.port.in;

public interface RedeemDiscountCodeUseCase {
    void redeemDiscountCode(RedeemDiscountCodeInput discountCodeInput);
    void canBeUsed(RedeemDiscountCodeInput discountCodeInput);
}