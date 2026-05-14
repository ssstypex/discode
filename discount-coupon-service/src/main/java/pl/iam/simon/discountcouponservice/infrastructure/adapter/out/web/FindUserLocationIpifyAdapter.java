package pl.iam.simon.discountcouponservice.infrastructure.adapter.out.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.port.out.GetUserCountryProvider;

@Component
public class FindUserLocationIpifyAdapter implements GetUserCountryProvider {

    private static final String TARGET_URL =
            "https://geo.ipify.org/api/v2/country?apiKey={apiKey}";

    private final RestClient restClient;
    private final String apiKey;

    public FindUserLocationIpifyAdapter(RestClient.Builder restClientBuilder,
                                        @Value("${ipify.api-key}") String apiKey) {
        this.restClient = restClientBuilder.build();
        this.apiKey = apiKey;
    }

    @Override
    public Country getUserCountry() {
        IpifyResponse response = restClient.get()
                .uri(TARGET_URL, apiKey)
                .retrieve()
                .body(IpifyResponse.class);

        return Country.fromCode(response.location().country());
    }
}
