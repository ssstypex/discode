package pl.iam.simon.discountcouponservice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;

@Service
public class DiscountCodeCreationFacade implements CreateDiscountCodeUseCase {

    private final CreateDiscountCodeDataSourceProvider dataSourceProvider;

    @Autowired
    public DiscountCodeCreationFacade(CreateDiscountCodeDataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public void createDiscountCode(CreateDiscountCodeInput createDiscountCodeInput) {
        var discountCode = DiscountCode.create(createDiscountCodeInput.getCodeValue(), createDiscountCodeInput.getCountry(), createDiscountCodeInput.getMaxUsages());
        dataSourceProvider.createDiscountCode(discountCode);
    }
}
