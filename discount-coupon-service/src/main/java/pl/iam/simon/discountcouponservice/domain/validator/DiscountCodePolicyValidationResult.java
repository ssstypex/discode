package pl.iam.simon.discountcouponservice.domain.validator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DiscountCodePolicyValidationResult {

    private final List<DiscountCodePolicyError> errorList;

    public boolean isValid() {
        return errorList.isEmpty();
    }

    public static DiscountCodePolicyValidationResult valid() {
        return new DiscountCodePolicyValidationResult(List.of());
    }

    public static DiscountCodePolicyValidationResult invalid(DiscountCodePolicyError discountCodePolicyError) {
        return new DiscountCodePolicyValidationResult(List.of(discountCodePolicyError));
    }

    public static DiscountCodePolicyValidationResult invalid(List<DiscountCodePolicyError> discountCodePolicyErrors) {
        return new DiscountCodePolicyValidationResult(discountCodePolicyErrors);
    }

    public DiscountCodePolicyValidationResult merge(DiscountCodePolicyValidationResult differentPolicyValidationResult) {
        List<DiscountCodePolicyError> combinedErrorList = Stream.concat(
                this.errorList.stream(), differentPolicyValidationResult.getErrorList().stream()).toList();
        return new DiscountCodePolicyValidationResult(combinedErrorList);
    }
}
