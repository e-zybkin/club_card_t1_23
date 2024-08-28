package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.card.CreationCardPayload;
import develop.backend.Club_card.entity.Card;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.entity.enums.card.CardColoursEnum;
import develop.backend.Club_card.entity.enums.card.CardTemplatesEnum;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.repository.CardRepository;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public Card createCard(UserDetails userDetails,
                           CreationCardPayload creationCardPayload){
        User user = userRepository.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                "security.auth.errors.username.not.found", null, Locale.getDefault()
            ), HttpStatus.NOT_FOUND));

        Card card = new Card();

        Integer cvc = Math.toIntExact(CardService.generateRandomNumber(3));
        long number = CardService.generateRandomNumber(16);
        Date currentDate = new Date();

        card.setOpeningDate(currentDate);
        card.setCvcCode(cvc);
        card.setNumber(number);
        card.setScore(0);
        card.setIsBlocked(false);
        card.setUser(user);
        card.setColour(switch (creationCardPayload.colour()){
            case "BLUE" -> CardColoursEnum.BLUE;
            case "RED" -> CardColoursEnum.RED;
            case "GREEN" -> CardColoursEnum.GREEN;
            default -> throw new CustomException(this.messageSource.getMessage(
                "validation.errors.colour.does.not.exist", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        });
        card.setPattern(switch (creationCardPayload.pattern()){
            case "FULL" -> CardTemplatesEnum.FULL;
            case "MIDDLE" -> CardTemplatesEnum.MIDDLE;
            case "LOW" -> CardTemplatesEnum.LOW;
            default -> throw new CustomException(this.messageSource.getMessage(
                "validation.errors.pattern.does.not.exist", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        });

        cardRepository.save(card);

    return card;

    }

    public void addScore(Card card, int inc) {
        card.setScore(card.getScore()+inc);
        cardRepository.save(card);
    }


}
