package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.currency.GetUserToCurrencyServicePayload;
import develop.backend.Club_card.controller.payload.user.UserLogInPayload;

public interface CurrencyInteractionService {
    GetUserToCurrencyServicePayload logInUserFromCurrencyService(UserLogInPayload userLogInPayload);
}
