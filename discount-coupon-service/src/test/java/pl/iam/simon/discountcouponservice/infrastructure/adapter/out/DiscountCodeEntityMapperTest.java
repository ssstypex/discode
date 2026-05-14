package pl.iam.simon.discountcouponservice.infrastructure.adapter.out;


import org.junit.jupiter.api.Test;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence.DiscountCodeEntity;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence.DiscountCodeEntityMapper;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountCodeEntityMapperTest {

    private final DiscountCodeEntityMapper mapper = DiscountCodeEntityMapper.INSTANCE;


    @Test
    public void ShouldMapDiscountCodeDomainToEntity() {
        //given
        DiscountCode domain = DiscountCode.create(
                new DiscountCodeValue("MAJ30"), Country.POLAND, 100);

        //when
        DiscountCodeEntity entity = mapper.toEntity(domain);

        //then
        assertAll(
                () -> assertEquals(entity.getId(), domain.getId()),
                () -> assertEquals(entity.getCode(), domain.getCode().value()),
                () -> assertEquals(entity.getCountryCode(), domain.getCountry().getCode()),
                () -> assertEquals(entity.getLastUsage(), domain.getLastUsage()),
                () -> assertEquals(entity.getMaxUsages(), domain.getMaxUsages()),
                () -> assertEquals(entity.getCurrentUsages(), domain.getCurrentUsages())
        );
    }

    @Test
    public void ShouldMapDiscountCodeEntityToDomainObject() {
        //given
        DiscountCodeEntity entity = new DiscountCodeEntity(
                UUID.randomUUID(), "MAJ30", "PL", 100, 0, Instant.now(), Instant.now()
        );

        //when
        DiscountCode domain = mapper.toDomain(entity);

        //then
        assertAll(
                () -> assertEquals(entity.getId(), domain.getId()),
                () -> assertEquals( entity.getCode(), domain.getCode().value()),
                () -> assertEquals(Country.POLAND, domain.getCountry()),
                () -> assertEquals(entity.getLastUsage(), domain.getLastUsage()),
                () -> assertEquals(entity.getMaxUsages(), domain.getMaxUsages()),
                () -> assertEquals(entity.getCurrentUsages(), domain.getCurrentUsages())
        );
    }

    @Test
    public void ShouldUpdateOnlyUsagesNumberAndLastUsageTime() {
        //given
        DiscountCodeEntity entity = new DiscountCodeEntity(
                UUID.randomUUID(), "MAJ30", "PL", 100, 0, null, Instant.now()
        );
        DiscountCode domain = new DiscountCode(UUID.randomUUID(), new DiscountCodeValue("MAJ40"), Country.POLAND, 100, 1, Instant.now());

        //when
        mapper.updateEntity(entity, domain);

        //then
        assertAll(
                () -> assertNotNull(entity.getId()),
                () -> assertEquals("MAJ30", entity.getCode()),
                () -> assertEquals(100, entity.getMaxUsages()),
                () -> assertEquals(1, entity.getCurrentUsages()),
                () -> assertNotNull(entity.getLastUsage())
        );
    }
}