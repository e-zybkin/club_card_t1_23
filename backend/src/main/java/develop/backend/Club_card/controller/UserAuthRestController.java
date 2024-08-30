package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.user.UserLogInPayload;
import develop.backend.Club_card.controller.payload.user.UserSignUpPayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.UserAuthService;
import develop.backend.Club_card.service.UserService;
import develop.backend.Club_card.service.impl.EmailServiceImpl;
import develop.backend.Club_card.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Map;

@Tag(name = "user_auth_endpoints")
@RestController
@RequestMapping("/club-card/api/auth")
@RequiredArgsConstructor
public class UserAuthRestController {

    private final UserAuthService userAuthService;
    private final EmailServiceImpl emailService;
    private final UserServiceImpl userService;

    @Operation(
            summary = "Аутентификация пользователя",
            description =
                    "Выполняет аутентификацию по email и паролю. " +
                            "В случае успеха возвращает JSON с email, ФИО и JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь аутентифицирован"),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных в теле запроса"),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным email не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь ожидает удаления аккаунта и" +
                    " не может быть аутентифицирован")
    })
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLogInPayload userLogInPayload,
                                   BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        AbstractMap.SimpleEntry<User, String> userToTokenMapping = userAuthService.login(userLogInPayload);

        return ResponseEntity.ok()
                .body(Map.of(
                        "token", userToTokenMapping.getValue(),
                        "email", userLogInPayload.email(),
                        "firstName", userToTokenMapping.getKey().getFirstName(),
                        "lastName", userToTokenMapping.getKey().getLastName(),
                        "middleName", userToTokenMapping.getKey().getMiddleName()
                ));
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Выполняет регистрацию пользователя по логину, паролю и email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных в теле запроса"),
            @ApiResponse(responseCode = "422", description = "Пользователь с таким email " +
                    "уже существует")
    })
    @PostMapping("signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody UserSignUpPayload userSignUpPayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        User user = userAuthService.signup(userSignUpPayload);

        String token = emailService.createToken(user);
        emailService.sendVerificationEmail(user, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "email", user.getEmail()
        ));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestParam("token") String token) {

        User user = userService.getCurrentUser(userDetails);

        boolean isVerified = emailService.confirmUser(user, token);
        if (isVerified) {
            return ResponseEntity.ok("Электронная почта подтверждена.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный или просроченный токен.");
        }
    }

}
