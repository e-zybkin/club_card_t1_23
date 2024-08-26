package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.entity.Card;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.repository.CardRepository;
import develop.backend.Club_card.repository.UserRepository;
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
public class CardServiceImpl {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public Card createCard(UserDetails userDetails){
        User user = userRepository.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                "security.auth.errors.username.not.found", null, Locale.getDefault()
            ), HttpStatus.NOT_FOUND));

        Card card = new Card();

        Random random = new Random();
        Integer cvc = Math.toIntExact(generateRandomNumber(3));
        long number = generateRandomNumber(16);
        Date currentDate = new Date();

        card.setOpeningDate(currentDate);
        card.setCvcCode(cvc);
        card.setNumber(number);
        card.setScore(0);
        card.setIsBlocked(false);
        card.setUser(user);

        cardRepository.save(card);

    return card;

    }

    public void addScore(Card card, int inc) {
        card.setScore(card.getScore()+inc);
        cardRepository.save(card);
    }




    public static String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Преобразуем байты в шестнадцатеричную строку
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    public static long generateRandomNumber(int length) {
        // Получаем текущую временную метку
        long timestamp = System.currentTimeMillis();

        // Генерируем случайное число для дополнительной энтропии
        Random random = new Random();
        int randomValue = random.nextInt();

        // Формируем строку для хеширования
        String input = timestamp + "_" + randomValue;

        // Генерируем хеш
        String hash = generateHash(input);

        // Извлекаем нужное количество символов из хеша и преобразуем в число
        String hashSubstring = hash.substring(0, length);

        // Преобразуем шестнадцатеричную строку в десятичное число
        BigInteger hashAsNumber = new BigInteger(hashSubstring, 16);

        // Возвращаем числовое значение, обрезанное до нужного размера
        return hashAsNumber.longValue();
    }

}
