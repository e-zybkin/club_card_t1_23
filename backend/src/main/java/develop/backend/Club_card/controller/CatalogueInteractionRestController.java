package develop.backend.Club_card.controller;

import develop.backend.Club_card.client.CurrencyInteractionRestClient;
import develop.backend.Club_card.controller.payload.catalogue.MoneyAmountPayload;
import develop.backend.Club_card.controller.payload.currency.DepositRequestPayload;
import develop.backend.Club_card.controller.payload.currency.WithDrawRequestPayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.CurrencyInteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://10.4.56.74")
@RequestMapping("/club-card/api/catalogue")
public class CatalogueInteractionRestController {

    private final CurrencyInteractionRestClient currencyInteractionRestClient;
    private final CurrencyInteractionService currencyInteractionService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(
            @Valid @RequestBody MoneyAmountPayload moneyAmountPayload,
            @AuthenticationPrincipal UserDetails userDetails,
            BindingResult bindingResult
    ) throws BindException {

        log.info("Entered buy product catalogue service interaction controller method");

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        BigDecimal receivedBalance = currencyInteractionRestClient
                .getUserBalanceFromCurrencyService(
                        userDetails.getUsername(),
                        new WithDrawRequestPayload(
                                moneyAmountPayload.amount(),
                                "Take money request",
                                "Club card service"
                        )
                );

        if (receivedBalance.equals(BigDecimal.valueOf(-1.0))) {
            log.info("Completed buy catalogue service interaction controller method with insufficient funds");
            return ResponseEntity.badRequest().build();
        }

        log.info("Completed buy catalogue service interaction controller method with successful withdrawal");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return/{id:\\d+}")
    public ResponseEntity<?> returnProduct(
            @PathVariable("id") Integer userId,
            @Valid @RequestBody MoneyAmountPayload moneyAmountPayload,
            BindingResult bindingResult
    ) throws BindException {

        log.info("Entered return money product catalogue service interaction controller method");

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        User controlledUser = currencyInteractionService.getUserById(userId);

        currencyInteractionRestClient.returnMoneyToCurrencyService(
                controlledUser.getEmail(),
                new DepositRequestPayload(
                        moneyAmountPayload.amount(),
                        "Запрос на возврат денег валютному сервису",
                        "Club card service"
                )
        );

        log.info("Completed return money product catalogue service interaction controller method");

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/money/info")
    public ResponseEntity<?> getCurrencyServiceUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Entered get user money info from currency service product catalogue service interaction controller method");
        return ResponseEntity.ok()
                .body(currencyInteractionRestClient.getUserDataFromCurrencyService(
                        userDetails.getUsername()
                ));
    }
}
