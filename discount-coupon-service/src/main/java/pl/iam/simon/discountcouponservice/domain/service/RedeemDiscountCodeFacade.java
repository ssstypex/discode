package pl.iam.simon.discountcouponservice.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.domain.port.out.SaveDiscountCodeUsageProvider;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.RedeemDiscountCodeDataSourceProvider;
import pl.iam.simon.discountcouponservice.domain.validator.DiscountCodePolicyValidationResult;

@RequiredArgsConstructor
public class RedeemDiscountCodeFacade implements RedeemDiscountCodeUseCase {

    private final RedeemDiscountCodeDataSourceProvider dataSourceProvider;
    private final SaveDiscountCodeUsageProvider saveDiscountCodeUsageProvider;
    private final RedeemDiscountCodePolicy policy;

    @Transactional
    @Override
    public void redeemDiscountCode(RedeemDiscountCodeInput discountCodeInput) {
        final DiscountCode discountCode = this.getDiscountCode(discountCodeInput);
        policy.validate(discountCode);
        discountCode.incrementUsages();
        dataSourceProvider.redeemDiscountCode(discountCode);
        saveDiscountCodeUsageProvider.save(discountCode, discountCodeInput.userId());
    }

    @Override
    public DiscountCodePolicyValidationResult canBeUsed(RedeemDiscountCodeInput discountCodeInput) {
        DiscountCode discountCode = this.getDiscountCode(discountCodeInput);
        return policy.validateWithResult(discountCode);
    }

    private DiscountCode getDiscountCode(RedeemDiscountCodeInput discountCodeInput) {
        return dataSourceProvider.findByCodeAndCountry(discountCodeInput.codeValue(),
                discountCodeInput.country()).orElseThrow(() -> new DiscountCodeValidationException(DiscountCodeValidationError.UNKNOWN));
    }
}
