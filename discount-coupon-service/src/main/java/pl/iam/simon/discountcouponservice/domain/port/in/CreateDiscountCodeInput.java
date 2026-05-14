package pl.iam.simon.discountcouponservice.domain.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;


@AllArgsConstructor
@Getter
public class CreateDiscountCodeInput {
    private DiscountCodeValue codeValue;
    private Country country;
    private int maxUsages;
}
