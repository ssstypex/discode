package pl.iam.simon.discountcouponservice.infrastructure.adapter.out;


import org.junit.jupiter.api.Test;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence.DiscountCodeEntity;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence.DiscountCodeEntityMapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountCodeEntityMapperTest {

    private final DiscountCodeEntityMapper mapper = DiscountCodeEntityMapper.INSTANCE;

    @Test
    public void ShouldMapDiscountCodeDomainToEntity() {
        //given
        DiscountCode discountCode = DiscountCode.create(
                new DiscountCodeValue("MAJ30"), Country.POLAND, 100);

        //when
        DiscountCodeEntity discountCodeEntity = mapper.toEntity(discountCode);

        //then
        assertAll(
                () -> assertEquals(discountCodeEntity.getId(), discountCode.getId()),
                () -> assertEquals(discountCodeEntity.getCode(), discountCode.getCode().value()),
                () -> assertEquals(discountCodeEntity.getCountryCode(), discountCode.getCountry().getCode()),
                () -> assertEquals(discountCodeEntity.getLastUsage(), discountCode.getLastUsage()),
                () -> assertEquals(discountCodeEntity.getMaxUsages(), discountCode.getMaxUsages()),
                () -> assertEquals(discountCodeEntity.getCurrentUsages(), discountCode.getCurrentUsages())
        );
    }
}