package pl.iam.simon.discountcouponservice.application.create;

import lombok.RequiredArgsConstructor;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;


@RequiredArgsConstructor
public class DiscountCodeCreationFacade implements CreateDiscountCodeUseCase {

    private final CreateDiscountCodeDataSourceProvider dataSourceProvider;

    @Override
    public void createDiscountCode(CreateDiscountCodeInput createDiscountCodeInput) {
        var discountCode = DiscountCode.create(createDiscountCodeInput.codeValue(), createDiscountCodeInput.country(), createDiscountCodeInput.maxUsages());
        dataSourceProvider.createDiscountCode(discountCode);
    }
}
