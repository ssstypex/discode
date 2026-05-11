package pl.iam.simon.discountcouponservice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;

@Service
public class RedeemDiscountCodeFacade implements RedeemDiscountCodeUseCase {

    private CreateDiscountCodeDataSourceProvider dataSourceProvider;

    @Autowired
    public RedeemDiscountCodeFacade(CreateDiscountCodeDataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public void redeemDiscountCode() {

    }
}
