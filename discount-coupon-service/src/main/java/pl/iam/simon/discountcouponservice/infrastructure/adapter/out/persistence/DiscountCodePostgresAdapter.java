package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.RedeemDiscountCodeDataSourceProvider;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DiscountCodePostgresAdapter implements CreateDiscountCodeDataSourceProvider, RedeemDiscountCodeDataSourceProvider {

    private final DiscountCodeRepository discountCodeRepository;
    private final DiscountCodeEntityMapper mapper;

    @Override
    public void createDiscountCode(DiscountCode discountCode) {
        discountCodeRepository.save(mapper.toEntity(discountCode));
    }

    @Override
    public void redeemDiscountCode(DiscountCode discountCode) {
        DiscountCodeEntity discountCodeEntity = this.discountCodeRepository.findByCodeAndCountryCode(discountCode.getCode().value(), discountCode.getCountry().getCode()).orElseThrow();
        this.mapper.updateEntity(discountCodeEntity, discountCode);
        this.discountCodeRepository.save(discountCodeEntity);
    }

    @Override
    public Optional<DiscountCode> findByCodeAndCountry(DiscountCodeValue discountCodeValue, Country country) {
        return this.discountCodeRepository
                .findByCodeAndCountryCode(discountCodeValue.value(), country.getCode())
                .map(this.mapper::toDomain);
    }

    @Override
    public boolean codeExist(DiscountCodeValue discountCodeValue, Country country) {
        return this.discountCodeRepository.existsByCodeAndCountryCode(discountCodeValue.value(), country.getCode());
    }
}
