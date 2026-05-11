package pl.iam.simon.discountcouponservice.infrastructure.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;

@RestController
@RequestMapping("/discount-codes")
public class DiscountCodeController {

    private CreateDiscountCodeUseCase createDiscountCodeUseCase;
    private RedeemDiscountCodeUseCase redeemDiscountCodeUseCase;

    @Autowired
    public DiscountCodeController(CreateDiscountCodeUseCase createDiscountCodeUseCase, RedeemDiscountCodeUseCase redeemDiscountCodeUseCase) {
        this.createDiscountCodeUseCase = createDiscountCodeUseCase;
        this.redeemDiscountCodeUseCase = redeemDiscountCodeUseCase;
    }
}
