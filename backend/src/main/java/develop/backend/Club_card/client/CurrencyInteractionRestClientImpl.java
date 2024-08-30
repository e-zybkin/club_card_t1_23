package develop.backend.Club_card.client;

import develop.backend.Club_card.controller.payload.currency.GetUserFromCurrencyServicePayload;
import develop.backend.Club_card.controller.payload.currency.WithDrawRequestPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CurrencyInteractionRestClientImpl implements CurrencyInteractionRestClient {

    ParameterizedTypeReference<GetUserFromCurrencyServicePayload> TYPE_REF =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public BigDecimal getUserBalanceFromCurrencyService(String email, WithDrawRequestPayload withDrawRequestPayload) {
        return restClient
                .post()
                .uri("http://10.4.56.90:3000/api/user/{email}/withdraw", email)
                .header("ServiceIntegration", "mega-secret-integration-token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(withDrawRequestPayload)
                .exchange(((clientRequest, clientResponse) -> {
                    if (clientResponse.getStatusCode().is2xxSuccessful()) {
                        return clientResponse.bodyTo(GetUserFromCurrencyServicePayload.class).coins();
                    }

                    return new BigDecimal(-1);
                }));
    }

    @Override
    public GetUserFromCurrencyServicePayload getUserDataFromCurrencyService(String email) {
        return restClient
                .get()
                .uri("http://10.4.56.90:3000/api/user/{email}", email)
                .header("ServiceIntegration", "mega-secret-integration-token")
                .retrieve()
                .toEntity(TYPE_REF)
                .getBody();
    }
}
