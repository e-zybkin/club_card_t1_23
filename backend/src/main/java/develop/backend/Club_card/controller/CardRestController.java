package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.card.CreationCardPayload;
import develop.backend.Club_card.entity.Card;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.repository.CardRepository;
import develop.backend.Club_card.service.impl.CardServiceImpl;
import develop.backend.Club_card.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Tag(name = "Контроллер для взаимодействия с картой")
@RequiredArgsConstructor
@RestController
@RequestMapping("/club-card/api/card")
public class CardRestController {

    private final CardServiceImpl cardService;
    private final UserServiceImpl userService;
    private final CardRepository cardRepository;
    private final MessageSource messageSource;

    @Operation(
        summary = "Создание карты",
        description =
            "Выполняет создание карты." +
                "В случае успеха возвращает JSON с информацией о карте, пользователе и QR"
    )
    @PostMapping("create")
    public ResponseEntity<?> createCard(
        @AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestBody CreationCardPayload creationCardPayload,
        BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

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

        Card card = userService.getCurrentUser(userDetails).getCard();

        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @Operation(
        summary = "Блокировка карты",
        description =
            "Выполняет блокировку карты."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта успешно заблокирована"),
        @ApiResponse(responseCode = "422", description = "Невозможно выполнить операцию. Карта уже заблокирована"),
    })
    @PatchMapping("block")
    public ResponseEntity<?> blockCard(
        @AuthenticationPrincipal UserDetails userDetails
    ){
        Card card = userService.getCurrentUser(userDetails).getCard();
        if(card.getIsBlocked())
            throw new CustomException(this.messageSource.getMessage(
                "card.error.block.already.done", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);

        card.setIsBlocked(true);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
        summary = "Разблокировка карты",
        description =
            "Разблокировка карты, доступная админу и суперадмину."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Карта успешно разблокирована"),
        @ApiResponse(responseCode = "422", description = "Невозможно выполнить операцию. Карта уже разблокирована"),
    })
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @PatchMapping("unblock")
    public ResponseEntity<?> unblockCard(
        @AuthenticationPrincipal UserDetails userDetails
    ){
        Card card = userService.getCurrentUser(userDetails).getCard();
        if(!card.getIsBlocked())
            throw new CustomException(this.messageSource.getMessage(
                "card.error.unblock.already.done", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);

        card.setIsBlocked(false);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
