package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.card.CreationCardPayload;
import develop.backend.Club_card.entity.Card;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public interface CardService{

    Card createCard(UserDetails userDetails, CreationCardPayload creationCardPayload);
    void addScore(Card card, int inc);

    static String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

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

    static long generateRandomNumber(int length) {

        long timestamp = System.currentTimeMillis();

        Random random = new Random();
        int randomValue = random.nextInt();

        String input = timestamp + "_" + randomValue;

        String hash = generateHash(input);

        String hashSubstring = hash.substring(0, length);

        BigInteger hashAsNumber = new BigInteger(hashSubstring, 16);

        return hashAsNumber.longValue();
    }
    
}
