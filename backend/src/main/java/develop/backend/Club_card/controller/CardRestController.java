package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.UserUpdatePayload;
import develop.backend.Club_card.entity.Card;
import develop.backend.Club_card.service.CardServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/club-card/api/card")
public class CardRestController {

    private final CardServiceImpl cardService;

    @PostMapping
    public ResponseEntity<?> createCard(
        @AuthenticationPrincipal UserDetails userDetails
        //image
    ) {


        Card card = cardService.createCard(userDetails);

        return new ResponseEntity<>(card, HttpStatus.OK);

}}
