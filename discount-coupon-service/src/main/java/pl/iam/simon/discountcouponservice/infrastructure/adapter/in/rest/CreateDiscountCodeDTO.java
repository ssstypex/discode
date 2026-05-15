package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CreateDiscountCodeDTO(
        @NotBlank(message = "Discount code must not be blank")
        @NotNull(message = "Discount code must not be null")
        @Length(min = 1, max = 50, message = "Discount code must be between 1 and 50 characters")
        String code,

        @NotBlank(message = "Country code must not be blank")
        @NotNull(message = "Country code must not be null")
        @Length(min = 2, max = 2, message = "Country code must be exactly 2 characters")
        String countryCode,

        @Positive(message = "Max usages must be greater than 0")
        int maxUsages
) {}