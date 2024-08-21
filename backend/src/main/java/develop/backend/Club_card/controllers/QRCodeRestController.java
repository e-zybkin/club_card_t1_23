package develop.backend.Club_card.controllers;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import develop.backend.Club_card.controllers.payload.UserNamePayload;
import develop.backend.Club_card.controllers.payload.UserUpdatePrivilegePayload;
import develop.backend.Club_card.exceptions.CustomException;
import develop.backend.Club_card.models.User;
import develop.backend.Club_card.repositories.UserRepository;
import develop.backend.Club_card.services.QRCodeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/club_card/api/qr")
public class QRCodeRestController {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @PostMapping("generate")
    public ResponseEntity<Map<String, String>> generateQRCode(@Valid @RequestBody UserNamePayload userNamePayload) {
//        String json = new Gson().toJson(userNamePayload);

        Optional<User> mayBeUser = userRepository.findUserByUsername(userNamePayload.username());

        if (mayBeUser.isEmpty()) {
            throw new CustomException(this.messageSource.getMessage(
                "security.auth.errors.username.not.found", null, Locale.getDefault()
            ), HttpStatus.NOT_FOUND);
        }

        try {
            byte[] qrCodeImage = QRCodeServiceImpl.generateQRCodeImage(mayBeUser.toString(), 300, 300);
            String base64QRCode = Base64.getEncoder().encodeToString(qrCodeImage);
            return ResponseEntity.ok(Map.of("qrCode", base64QRCode));
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error generating QR Code: " + e.getMessage()));
        }
    }
}
