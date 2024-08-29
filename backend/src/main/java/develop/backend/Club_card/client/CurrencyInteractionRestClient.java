package develop.backend.Club_card.client;

import java.math.BigDecimal;

public interface CurrencyInteractionRestClient {
    BigDecimal getUserBalanceFromCurrencyService(String email);
}
