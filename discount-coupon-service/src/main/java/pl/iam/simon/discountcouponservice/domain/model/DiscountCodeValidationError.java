package pl.iam.simon.discountcouponservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountCodeValidationError {

    WRONG_COUNTRY("Discount code is not available in your country"),
    USER_USAGE_LIMIT("Discount code can only be used once per user"),
    USAGE_LIMIT("Discount code is no longer available"),
    UNKNOWN("Discount code does not exist");

    private final String message;
}
