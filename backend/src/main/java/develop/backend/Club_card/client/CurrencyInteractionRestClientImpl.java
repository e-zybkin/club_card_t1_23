package develop.backend.Club_card.client;

import develop.backend.Club_card.controller.payload.currency.GetUserFromCurrencyServicePayload;
import develop.backend.Club_card.controller.payload.currency.WithDrawRequestPayload;
import develop.backend.Club_card.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CurrencyInteractionRestClientImpl implements CurrencyInteractionRestClient {

    private final RestClient restClient;

    @Override
    public BigDecimal getUserBalanceFromCurrencyService(String email, WithDrawRequestPayload withDrawRequestPayload) {
        ResponseEntity<GetUserFromCurrencyServicePayload> responseEntity = restClient
                .post()
                .uri("http://localhost:8081/api/user/{email}/withdraw", email)
                .header("")
                .body(withDrawRequestPayload)
                .retrieve()
                .toEntity(GetUserFromCurrencyServicePayload.class);

        int statusCode = responseEntity.getStatusCode().value();

        if (statusCode == 400) {
            throw new CustomException("Недостаточно средств в денежном хранилище", HttpStatus.BAD_REQUEST);
        }

        GetUserFromCurrencyServicePayload responseBody = responseEntity.getBody();

        return responseBody.coins();
    }
}
