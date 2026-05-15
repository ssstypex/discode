package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;
import pl.iam.simon.discountcouponservice.domain.model.UserId;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.validator.DiscountCodePolicyValidationResult;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/discount-codes")
public class DiscountCodeController {

    private final CreateDiscountCodeUseCase createDiscountCodeUseCase;
    private final RedeemDiscountCodeUseCase redeemDiscountCodeUseCase;

    @PostMapping
    public ResponseEntity<?> createDiscountCode(@Valid @RequestBody CreateDiscountCodeDTO discountCode) {
        createDiscountCodeUseCase.createDiscountCode(new CreateDiscountCodeInput(
                new DiscountCodeValue(discountCode.code()), Country.fromCode(discountCode.countryCode()),
                discountCode.maxUsages()));

        return new ResponseEntity<>(
                HttpStatus.CREATED);
    }

    @GetMapping("/{countryCode}/{code}/validate")
    public ResponseEntity<DiscountCodeExceptionHandler.ErrorResponse> validateDiscountCode(
            @PathVariable("countryCode") String countryCode,
            @PathVariable("code") String code) {

        DiscountCodePolicyValidationResult discountCodePolicyValidationResult =
                redeemDiscountCodeUseCase.canBeUsed(new RedeemDiscountCodeInput(
                        new DiscountCodeValue(code), Country.fromCode(countryCode), new UserId(UUID.randomUUID()))
        );
        DiscountCodeExceptionHandler.ErrorResponse response = new DiscountCodeExceptionHandler.ErrorResponse(
                discountCodePolicyValidationResult.getErrorList().stream().map(error -> error.error().getMessage()).toList());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{countryCode}/{code}/redeem")
    public ResponseEntity<Void> redeemDiscountCode(
            @PathVariable("countryCode") String countryCode,
            @PathVariable("code") String code) {
        redeemDiscountCodeUseCase.redeemDiscountCode(
                new RedeemDiscountCodeInput(new DiscountCodeValue(code), Country.fromCode(countryCode),
                        new UserId(UUID.randomUUID())));

        return ResponseEntity.ok().build();
    }


}
