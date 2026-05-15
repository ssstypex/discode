package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCodeEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DiscountCodeEntity> findByCodeAndCountryCode(String code, String  countryCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByCodeAndCountryCode(String value, String code);
}
