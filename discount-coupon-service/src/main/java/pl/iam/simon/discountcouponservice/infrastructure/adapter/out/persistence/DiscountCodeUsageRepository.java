package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscountCodeUsageRepository extends JpaRepository<DiscountCodeUsageEntity, UUID> {
    boolean existsByUserIdAndDiscountCode_id(UUID userId, UUID discountCodeId);
}
