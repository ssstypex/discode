package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CreateDiscountCodeDTO(@NotBlank @NotNull @Length(min = 1, max = 50) String code,
                                    @NotBlank @NotNull @Length(min = 2, max = 2) String countryCode,
                                    @Positive int maxUsages) {
}
