package pl.iam.simon.discountcouponservice.domain.model;

public record DiscountCodeValue(String value) {
    public DiscountCodeValue {
        value = value.toUpperCase().strip();
    }
}
