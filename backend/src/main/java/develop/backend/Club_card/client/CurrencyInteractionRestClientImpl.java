package develop.backend.Club_card.client;

import develop.backend.Club_card.controller.payload.currency.DepositRequestPayload;
import develop.backend.Club_card.controller.payload.currency.GetUserFromCurrencyServicePayload;
import develop.backend.Club_card.controller.payload.currency.WithDrawRequestPayload;
import develop.backend.Club_card.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
public class CurrencyInteractionRestClientImpl implements CurrencyInteractionRestClient {

    ParameterizedTypeReference<GetUserFromCurrencyServicePayload> TYPE_REF =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public BigDecimal getUserBalanceFromCurrencyService(String email, WithDrawRequestPayload withDrawRequestPayload) {
        log.info("Entered get user balance from currency service interaction controller method");
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

                    return BigDecimal.valueOf(-1.0);
                }));
    }

    @Override
    public void returnMoneyToCurrencyService(String email, DepositRequestPayload depositRequestPayload) {
        log.info("Entered return money to currency service method");
        restClient
                .post()
                .uri("http://10.4.56.90:3000/api/user/{email}/deposit", email)
                .header("ServiceIntegration", "mega-secret-integration-token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(depositRequestPayload)
                .retrieve();
    }

    @Override
    public GetUserFromCurrencyServicePayload getUserDataFromCurrencyService(String email) {
        log.info("Entered get user money data from currency service method");
        return restClient
                .get()
                .uri("http://10.4.56.90:3000/api/user/{email}", email)
                .header("ServiceIntegration", "mega-secret-integration-token")
                .retrieve()
                .toEntity(TYPE_REF)
                .getBody();
    }
}
