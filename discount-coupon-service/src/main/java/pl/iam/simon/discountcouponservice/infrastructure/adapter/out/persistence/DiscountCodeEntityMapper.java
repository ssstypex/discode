package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;

@Mapper(componentModel = "spring")
public interface DiscountCodeEntityMapper {

    DiscountCodeEntityMapper INSTANCE = Mappers.getMapper(DiscountCodeEntityMapper.class);

    @Mapping(target = "code", source = "discountCode.code.value")
    @Mapping(target = "countryCode", source = "discountCode.country.code")
    DiscountCodeEntity toEntity(DiscountCode discountCode);
}
