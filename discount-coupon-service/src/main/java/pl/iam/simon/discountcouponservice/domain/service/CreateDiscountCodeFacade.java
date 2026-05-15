package pl.iam.simon.discountcouponservice.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;


@RequiredArgsConstructor
public class CreateDiscountCodeFacade implements CreateDiscountCodeUseCase {

    private final CreateDiscountCodeDataSourceProvider dataSourceProvider;

    @Transactional
    @Override
    public void createDiscountCode(CreateDiscountCodeInput createDiscountCodeInput) {
        if (dataSourceProvider.codeExist(createDiscountCodeInput.codeValue(), createDiscountCodeInput.country())) {
            throw new DiscountCodeValidationException(DiscountCodeValidationError.CODE_EXIST);
        }
        var discountCode = DiscountCode.create(createDiscountCodeInput.codeValue(), createDiscountCodeInput.country(), createDiscountCodeInput.maxUsages());
        dataSourceProvider.createDiscountCode(discountCode);
    }
}
