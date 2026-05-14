package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;

@Mapper(componentModel = "spring")
public interface DiscountCodeEntityMapper {

    DiscountCodeEntityMapper INSTANCE = Mappers.getMapper(DiscountCodeEntityMapper.class);

    @Mapping(target = "code", source = "discountCode.code.value")
    @Mapping(target = "countryCode", source = "discountCode.country.code")
    @Mapping(target = "createdAt", ignore = true)
    DiscountCodeEntity toEntity(DiscountCode discountCode);


    @Mapping(target = "code",  qualifiedByName = "toDiscountCodeValue")
    @Mapping(target = "country", source = "discountCodeEntity.countryCode", qualifiedByName = "toCountry")
    DiscountCode toDomain(DiscountCodeEntity discountCodeEntity);

    @Named("toDiscountCodeValue")
    default DiscountCodeValue toDiscountCodeValue(String value) {
        if (value == null) return null;
        return new DiscountCodeValue(value);
    }

    @Named("toCountry")
    default Country toCountry(String country) {
        return Country.fromCode(country);
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "lastUsage", source = "lastUsage")
    @Mapping(target = "currentUsages", source = "currentUsages")
    void updateEntity(@MappingTarget DiscountCodeEntity entity, DiscountCode domain);
}
