package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.currency.GetUserToCurrencyServicePayload;
import develop.backend.Club_card.controller.payload.user.UserLogInPayload;
import develop.backend.Club_card.service.CurrencyInteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/club-card/api/currency")
@RequiredArgsConstructor
public class CurrencyInteractionRestController {

    private final CurrencyInteractionService currencyInteractionService;

    @PostMapping("/login")
    ResponseEntity<?> logInUserFromCurrencyService(
            @Valid @RequestBody UserLogInPayload userLogInPayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        GetUserToCurrencyServicePayload payload =
                currencyInteractionService.logInUserFromCurrencyService(userLogInPayload);

        return ResponseEntity.ok().body(payload);
    }

}
