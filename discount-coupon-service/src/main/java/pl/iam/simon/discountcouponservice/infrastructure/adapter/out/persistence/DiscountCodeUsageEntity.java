package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "discount_code_usages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"discount_code_id", "user_id"})
)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountCodeUsageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_code_id", nullable = false, updatable = false)
    private DiscountCodeEntity discountCode;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @CreationTimestamp
    @Column(name = "used_at", nullable = false, updatable = false)
    private LocalDateTime usedAt;


    public DiscountCodeUsageEntity(DiscountCodeEntity discountCode, UUID userId) {
        this.discountCode = discountCode;
        this.userId = userId;
    }
}