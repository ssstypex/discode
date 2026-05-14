package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCodeEntity, UUID> {
    Optional<DiscountCodeEntity> findByCodeAndCountryCode(String code, String  countryCode);
}
