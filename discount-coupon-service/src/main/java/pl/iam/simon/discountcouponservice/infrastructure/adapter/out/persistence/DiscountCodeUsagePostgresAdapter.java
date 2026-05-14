package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.model.UserId;
import pl.iam.simon.discountcouponservice.domain.port.out.SaveDiscountCodeUsageProvider;
import pl.iam.simon.discountcouponservice.domain.validator.GetDiscountCodeUsageProvider;

@Component
@RequiredArgsConstructor
public class DiscountCodeUsagePostgresAdapter implements GetDiscountCodeUsageProvider, SaveDiscountCodeUsageProvider {

    private final DiscountCodeUsageRepository usageRepository;
    private final DiscountCodeEntityMapper mapper;

    @Override
    public boolean hasUserUsedCode(UserId userId, DiscountCode discountCode) {
        return usageRepository.existsByUserIdAndDiscountCode_id(userId.id(), discountCode.getId());
    }

    @Override
    public void save(DiscountCode discountCode, UserId userId) {
        usageRepository.save(new DiscountCodeUsageEntity(mapper.toEntity(discountCode), userId.id()));
    }
}
