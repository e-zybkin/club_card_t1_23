package develop.backend.Club_card.client;

import develop.backend.Club_card.controller.payload.currency.GetUserFromCurrencyServicePayload;

import java.math.BigDecimal;

public interface CurrencyInteractionRestClient {
    BigDecimal getUserBalanceFromCurrencyService(String email);
}
