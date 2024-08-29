package develop.backend.Club_card.client;

import develop.backend.Club_card.controller.payload.currency.WithDrawRequestPayload;

import java.math.BigDecimal;

public interface CurrencyInteractionRestClient {
    BigDecimal getUserBalanceFromCurrencyService(String email, WithDrawRequestPayload withDrawRequestPayload);
}
