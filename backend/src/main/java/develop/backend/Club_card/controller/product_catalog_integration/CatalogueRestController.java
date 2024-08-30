package develop.backend.Club_card.controller.product_catalog_integration;

import develop.backend.Club_card.client.CurrencyInteractionRestClientImpl;
import develop.backend.Club_card.controller.payload.catalogue.GetMoneyPayload;
import develop.backend.Club_card.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("club-card/outer/api/catalogue")
public class CatalogueRestController {

    private final CurrencyInteractionRestClientImpl currencyInteractionRestClient;

    @PostMapping("buy")
    public ResponseEntity<?> buyProduct(@AuthenticationPrincipal UserDetails userDetails,
                                        @Valid @RequestBody GetMoneyPayload getMoneyPayload,
                                        BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        BigDecimal restAmount;

        try{
//            restAmount = currencyInteractionRestClient.getUserBalanceFromCurrencyService(getMoneyPayload.email(), getMoneyPayload.count());
        } catch (CustomException ex) {
            throw new CustomException(ex.getMessage(), ex.getHttpStatus());
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("return")
    public ResponseEntity<?> returnProduct(@AuthenticationPrincipal UserDetails userDetails,
                                        @Valid @RequestBody GetMoneyPayload getMoneyPayload,
                                        BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        BigDecimal restAmount;

        try{
//            restAmount = currencyInteractionRestClient.getUserBalanceFromCurrencyService(String.valueOf(getMoneyPayload));
        } catch (CustomException ex) {
            throw new CustomException(ex.getMessage(), ex.getHttpStatus());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}