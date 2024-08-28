package develop.backend.Club_card.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendRegistrationCompleteEmail(String email);
    void sendQrCodeEmail();
}
