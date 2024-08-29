package develop.backend.Club_card.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CurrencyInteractionRestClientImpl implements CurrencyInteractionRestClient {

    private final RestClient restClient;

    @Override
    public BigDecimal getUserBalanceFromCurrencyService(String email) {
        return null;
    }
}
