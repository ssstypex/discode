package pl.iam.simon.discountcouponservice.domain.validator;

import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;

public record DiscountCodePolicyError(DiscountCodeValidationError error) {
}
