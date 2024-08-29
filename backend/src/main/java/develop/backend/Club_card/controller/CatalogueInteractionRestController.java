package develop.backend.Club_card.controller;

import develop.backend.Club_card.client.CurrencyInteractionRestClient;
import develop.backend.Club_card.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/club-card/api/catalogue")
@RequiredArgsConstructor
public class CatalogueInteractionRestController {
}
