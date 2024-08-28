package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

//    @Value("${custom.email.from}")
//    private String customEmailFrom;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendRegistrationCompleteEmail(String email) {
        String subject = "Registration completed";
        String mailContent = "<p> Hi, dear User! </p>"+
                "<p>Thank you for registering with us,"+
                "<p> Thank you <br> Club Card service";
        sendEmail(subject, mailContent, email);
    }

    @Override
    public void sendQrCodeEmail() {

    }

    private void sendEmail(
            String subject,
            String mailContent,
            String email
    ) {
        String senderName = "Club card service";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setFrom("miroshnickovdmitrij@yandex.ru", senderName);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        javaMailSender.send(message);
    }
}
