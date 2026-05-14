package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.auth;

import org.springframework.stereotype.Component;
import pl.iam.simon.discountcouponservice.domain.model.UserId;
import pl.iam.simon.discountcouponservice.domain.port.out.GetCurrentUserProvider;

import java.util.UUID;

@Component
public class CurrentUserProvider implements GetCurrentUserProvider {

    @Override
    public UserId getCurrentUserId() {
        return new UserId(UUID.randomUUID());
    }
}
