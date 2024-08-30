package develop.backend.Club_card.controller;

import develop.backend.Club_card.client.CurrencyInteractionRestClient;
import develop.backend.Club_card.controller.payload.currency.WithDrawRequestPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/club-card/api/catalogue")
@RequiredArgsConstructor
public class CatalogueInteractionRestController {

    private final CurrencyInteractionRestClient currencyInteractionRestClient;

    @GetMapping("/money/{amount:\\d+}")
    public ResponseEntity<?> getMoneyCount(
            @PathVariable("amount") BigDecimal amount,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok().body(currencyInteractionRestClient.getUserBalanceFromCurrencyService(
                userDetails.getUsername(),
                new WithDrawRequestPayload(
                        amount,
                        "comment",
                        "club card service"
                ))
        );
    }

    @GetMapping("/money/info")
    public ResponseEntity<?> getCurrencyServiceUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok()
                .body(currencyInteractionRestClient.getUserDataFromCurrencyService(
                        userDetails.getUsername()
                ));
    }
}
