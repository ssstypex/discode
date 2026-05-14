package pl.iam.simon.discountcouponservice.application.redeem;

import lombok.RequiredArgsConstructor;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.RedeemDiscountCodeDataSourceProvider;

@RequiredArgsConstructor
public class RedeemDiscountCodeFacade implements RedeemDiscountCodeUseCase {

    private final RedeemDiscountCodeDataSourceProvider dataSourceProvider;
    private final RedeemDiscountCodePolicy policy;

    @Override
    public void redeemDiscountCode(RedeemDiscountCodeInput discountCodeInput) {
        final DiscountCode discountCode = this.getDiscountCode(discountCodeInput);
        discountCode.incrementUsages();
        dataSourceProvider.redeemDiscountCode(discountCode);
    }

    @Override
    public void canBeUsed(RedeemDiscountCodeInput discountCodeInput) {
        DiscountCode discountCode = this.getDiscountCode(discountCodeInput);
        policy.validate(discountCode);
    }

    private DiscountCode getDiscountCode(RedeemDiscountCodeInput discountCodeInput) {
        return dataSourceProvider.findByCodeAndCountry(discountCodeInput.codeValue(),
                discountCodeInput.country()).orElseThrow(() -> new DiscountCodeValidationException(DiscountCodeValidationError.UNKNOWN));
    }
}
