package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/discount-codes")
public class DiscountCodeController {

    private final CreateDiscountCodeUseCase createDiscountCodeUseCase;
    private final RedeemDiscountCodeUseCase redeemDiscountCodeUseCase;

    @PostMapping
    public ResponseEntity<?> createDiscountCode(@Valid @RequestBody CreateDiscountCodeDTO discountCode) {
        createDiscountCodeUseCase.createDiscountCode(
                new CreateDiscountCodeInput(
                        new DiscountCodeValue(discountCode.code()),
                        Country.fromCode(discountCode.countryCode()),
                        discountCode.maxUsages())
        );
        return new ResponseEntity<>(
                HttpStatus.CREATED);
    }

    @GetMapping("/{countryCode}/{code}/validate")
    public ResponseEntity<Void> validateDiscountCode(
            @PathVariable("countryCode") String countryCode,
            @PathVariable("code") String code) {
        redeemDiscountCodeUseCase.canBeUsed(
                new RedeemDiscountCodeInput(
                        new DiscountCodeValue(code),
                        Country.fromCode(countryCode))
        );

        return ResponseEntity.ok().build();
    }


    @PostMapping("/{countryCode}/{code}/redeem")
    public ResponseEntity<Void> redeemDiscountCode(
            @PathVariable("countryCode") String countryCode,
            @PathVariable("code") String code) {
        redeemDiscountCodeUseCase.redeemDiscountCode(
                new RedeemDiscountCodeInput(
                        new DiscountCodeValue(code),
                        Country.fromCode(countryCode)
                )
        );

        return ResponseEntity.ok().build();
    }


}
