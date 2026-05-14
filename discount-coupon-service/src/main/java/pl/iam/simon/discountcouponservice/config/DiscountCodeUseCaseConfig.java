package pl.iam.simon.discountcouponservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.iam.simon.discountcouponservice.application.create.DiscountCodeCreationFacade;
import pl.iam.simon.discountcouponservice.application.redeem.RedeemDiscountCodeFacade;
import pl.iam.simon.discountcouponservice.application.redeem.RedeemDiscountCodePolicy;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;
import pl.iam.simon.discountcouponservice.domain.port.out.RedeemDiscountCodeDataSourceProvider;

@Configuration
public class DiscountCodeUseCaseConfig {

    @Bean
    public RedeemDiscountCodeUseCase redeemDiscountCodeUseCase(
            RedeemDiscountCodeDataSourceProvider provider, RedeemDiscountCodePolicy policy) {

        return new RedeemDiscountCodeFacade(provider, policy);
    }

    @Bean
    public CreateDiscountCodeUseCase createDiscountCodeUseCase(
            CreateDiscountCodeDataSourceProvider provider) {
        return new DiscountCodeCreationFacade(provider);
    }
}
