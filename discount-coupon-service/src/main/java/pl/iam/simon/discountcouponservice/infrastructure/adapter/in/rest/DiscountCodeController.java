package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;

@RestController
@RequestMapping("/v1/discount-codes")
public class DiscountCodeController {

    private CreateDiscountCodeUseCase createDiscountCodeUseCase;
    private RedeemDiscountCodeUseCase redeemDiscountCodeUseCase;

    @Autowired
    public DiscountCodeController(CreateDiscountCodeUseCase createDiscountCodeUseCase, RedeemDiscountCodeUseCase redeemDiscountCodeUseCase) {
        this.createDiscountCodeUseCase = createDiscountCodeUseCase;
        this.redeemDiscountCodeUseCase = redeemDiscountCodeUseCase;
    }

    @PostMapping
    public ResponseEntity<?>  createDiscountCode(@Valid @RequestBody CreateDiscountCodeDTO discountCode) {
        createDiscountCodeUseCase.createDiscountCode(
                new CreateDiscountCodeInput(
                        new DiscountCodeValue(discountCode.code()),
                        Country.fromCode(discountCode.countryCode()),
                        discountCode.maxUsages())
        );
        return new ResponseEntity<>(
                HttpStatus.CREATED);
    }

}
