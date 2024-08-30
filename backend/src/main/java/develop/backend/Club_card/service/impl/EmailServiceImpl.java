package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.entity.VerificationToken;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public void sendVerificationEmail(User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "Подтверждение регистрации";
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token;
        String message = "Перейдите по ссылке для подтверждения регистрации: " + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("miroshnickovdmitrij@yandex.ru");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

    public boolean confirmUser(User user, String token) {


        VerificationToken verificationToken = verificationTokenOpt.get();
        User user = verificationToken.getUser();


        user.setIsEmailVerified(true);
        userRepository.save(user);
        return true;
    }

    public String createToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(new Date(System.currentTimeMillis() + 86400000));
        tokenRepository.save(verificationToken);

        return verificationToken.getToken();
    }


}
