package pl.iam.simon.discountcouponservice.domain.port.out;

import pl.iam.simon.discountcouponservice.domain.model.Country;

public interface GetUserCountryProvider {
    Country getUserCountry();
}