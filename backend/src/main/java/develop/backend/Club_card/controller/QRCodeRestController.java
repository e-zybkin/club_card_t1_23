package develop.backend.Club_card.controller;

import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.QRCodeService;
import develop.backend.Club_card.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Tag(name = "qr_code_rest_controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/club-card/api/qr")
@Slf4j
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

        log.info("Entered generate qr code for card qr code controller method");

        User user = userService.getCurrentUser(userDetails);

        log.info("Completed generate qr code for card qr code controller method");

        return QRCodeService.encodeQR(user);
    }

    @Operation(
        summary = "Генерация QR-кода по запросу админа",
        description =
            "Генерируется QR-код с информацией о пользователе." +
                "Информация о пользователе берется на основе username, который отправляется с frontend в json." +
                "Доступно только для админа и супер-админа."
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @PostMapping("generate/{id:\\d+}")
    public ResponseEntity<Map<String, String>> generateQRCodeForAdmin(@PathVariable("id") Integer id,
                                                                      BindingResult bindingResult) throws BindException {

        log.info("Entered generate qr code from user data qr code controller method");

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        Optional<User> mayBeUser = userRepository.findById(id);

        if (mayBeUser.isEmpty()) {
            throw new CustomException(this.messageSource.getMessage(
                "security.auth.errors.username.not.found", null, Locale.getDefault()
            ), HttpStatus.NOT_FOUND);
        }

        User user = mayBeUser.get();

        log.info("Completed generate qr code from user data qr code controller method");

        return QRCodeService.encodeQR(user);
    }
}