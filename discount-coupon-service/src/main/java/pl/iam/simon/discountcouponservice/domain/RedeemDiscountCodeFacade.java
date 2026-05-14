package pl.iam.simon.discountcouponservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;

@Service
@RequiredArgsConstructor
public class RedeemDiscountCodeFacade implements RedeemDiscountCodeUseCase {

    private final CreateDiscountCodeDataSourceProvider dataSourceProvider;

    @Override
    public void redeemDiscountCode() {

    }
}
