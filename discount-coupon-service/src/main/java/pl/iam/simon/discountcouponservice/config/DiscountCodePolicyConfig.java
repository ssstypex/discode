package pl.iam.simon.discountcouponservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.iam.simon.discountcouponservice.application.redeem.RedeemDiscountCodePolicy;
import pl.iam.simon.discountcouponservice.domain.port.out.GetCurrentUserProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.GetUserCountryProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.GetDiscountCodeUsageProvider;

@Configuration
public class DiscountCodePolicyConfig {

    @Bean
    public RedeemDiscountCodePolicy redeemDiscountCodePolicy(GetUserCountryProvider provider,
                                                             GetCurrentUserProvider currentUserProvider,
                                                             GetDiscountCodeUsageProvider usageProvider) {
        return new RedeemDiscountCodePolicy(provider, currentUserProvider, usageProvider);
    }
}
