package pl.iam.simon.discountcouponservice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;

@Service
public class DiscountCodeCreationFacade implements CreateDiscountCodeUseCase {

    private CreateDiscountCodeDataSourceProvider dataSourceProvider;

    @Autowired
    public DiscountCodeCreationFacade(CreateDiscountCodeDataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    public void createNewClientDiscountCode() {
        dataSourceProvider.createDiscountCode();
    }
}
