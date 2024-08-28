package develop.backend.Club_card.controller;

import com.google.zxing.WriterException;
import develop.backend.Club_card.controller.payload.user.UserNamePayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.impl.QRCodeServiceImpl;
import develop.backend.Club_card.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/club-card/api/qr")
public class QRCodeRestController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final UserRepository userRepository;


    @Operation(
        summary = "Генерация QR-кода для пользователя",
        description =
            "Генерируется QR-код с информацией о пользователе." +
                "Информация о пользователе берется на основе JWT-токена: кто сделал запрос, того информация и вернулась."
    )
    @GetMapping("generate")
    public ResponseEntity<Map<String, String>> generateQRCode(@AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getCurrentUser(userDetails);

        try {
            byte[] qrCodeImage = QRCodeServiceImpl.generateQRCodeImage(user.toString(), 300, 300);
            String base64QRCode = Base64.getEncoder().encodeToString(qrCodeImage);
            return ResponseEntity.ok(Map.of("qrCode", base64QRCode));
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error generating QR Code: " + e.getMessage()));
        }
    }

    @Operation(
        summary = "Генерация QR-кода по запросу админа",
        description =
            "Генерируется QR-код с информацией о пользователе." +
                "Информация о пользователе берется на основе username, который отправляется с frontend в json." +
                "Доступно только для админа и суперадмина."
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @PostMapping("generate")
    public ResponseEntity<Map<String, String>> generateQRCodeForAdmin(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @Valid @RequestBody UserNamePayload usernamePayload,
                                                                      BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        Optional<User> mayBeUser = userRepository.findUserByUsername(usernamePayload.username());

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