package pl.iam.simon.discountcouponservice.infrastructure.adapter.out;

import org.springframework.stereotype.Service;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.RedeemDiscountCodeDataSourceProvider;

@Service
public class PostgresAdapter implements CreateDiscountCodeDataSourceProvider, RedeemDiscountCodeDataSourceProvider
{

    @Override
    public void createDiscountCode() {

    }


    @Override
    public void redeemDiscountCode() {

    }
}
