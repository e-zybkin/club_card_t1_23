package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.card.CreationCardPayload;
import develop.backend.Club_card.entity.Card;
import develop.backend.Club_card.repository.CardRepository;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.impl.CardServiceImpl;
import develop.backend.Club_card.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/club-card/api/card")
public class CardRestController {

    private final CardServiceImpl cardService;
    private final CardRepository cardRepository;
    private final UserServiceImpl userService;


    @Operation(
        summary = "Создание карты",
        description =
            "Выполняет создание карты." +
                "В случае успеха возвращает JSON с информацией о карте, пользователе и QR"
    )
    @PostMapping("create")
    public ResponseEntity<?> createCard(
        @AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestBody CreationCardPayload creationCardPayload
    ) {

        Card card = cardService.createCard(userDetails, creationCardPayload);

        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @Operation(
        summary = "Создание карты",
        description =
            "Выполняет создание карты." +
                "В случае успеха возвращает JSON с информацией о карте, пользователе и QR"
    )
    @GetMapping()
    public ResponseEntity<?> getCardInfo(
        @AuthenticationPrincipal UserDetails userDetails
    ) {

        Card card = cardRepository.findCardByUser(userService.getCurrentUser(userDetails));

        return new ResponseEntity<>(card, HttpStatus.OK);
    }


}
