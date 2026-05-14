package pl.iam.simon.discountcouponservice.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountCode {

    private UUID id;
    private DiscountCodeValue code;
    private Country country;
    private int maxUsages;
    private int currentUsages;
    private Instant lastUsage;

    public static DiscountCode create( DiscountCodeValue codeValue, Country country, int maxUsages) {
        var discountCode = new DiscountCode();
        discountCode.code = codeValue;
        discountCode.country = country;
        discountCode.maxUsages = maxUsages;
        discountCode.currentUsages = 0;
        return discountCode;
    }

    public boolean isAvailable() {
        return maxUsages > 0 && currentUsages < maxUsages;
    }

    public void incrementUsages() {
        this.currentUsages++;
        this.lastUsage = Instant.now();
    }
}
