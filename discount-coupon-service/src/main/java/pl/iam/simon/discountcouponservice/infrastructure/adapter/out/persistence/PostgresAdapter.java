package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.RedeemDiscountCodeDataSourceProvider;

@Component
@RequiredArgsConstructor
public class PostgresAdapter implements CreateDiscountCodeDataSourceProvider, RedeemDiscountCodeDataSourceProvider {
    private final DiscountCodeRepository discountCodeRepository;
    private final DiscountCodeEntityMapper mapper;


    @Override
    public void createDiscountCode(DiscountCode discountCode) {
        discountCodeRepository.save(mapper.toEntity(discountCode));
    }

    @Override
    public void redeemDiscountCode() {

    }
}
