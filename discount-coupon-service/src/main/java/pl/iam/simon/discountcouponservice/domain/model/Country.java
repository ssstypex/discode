package pl.iam.simon.discountcouponservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Country {
    POLAND("PL"),
    GERMANY("DE");

    private final String code;
    private static final Map<String, Country> CODE_MAP;

    static {
        CODE_MAP = Arrays.stream(Country.values()).collect(Collectors.toMap(c -> c.code, c -> c));
    }

    public static Country fromCode(String code) {
        return CODE_MAP.get(code);
    }
}
