package develop.backend.Club_card.controller;

import develop.backend.Club_card.entity.Card;
import develop.backend.Club_card.service.impl.CardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
