package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.user.UserLogInPayload;
import develop.backend.Club_card.controller.payload.user.UserSignUpPayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.EmailService;
import develop.backend.Club_card.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "user_auth_endpoints")
@RestController
@RequestMapping("/club-card/api/auth")
@RequiredArgsConstructor
public class UserAuthRestController {

    private final UserAuthService userAuthService;
    private final EmailService emailService;

    @Operation(
            summary = "Аутентификация пользователя",
            description =
                    "Выполняет аутентификацию по email и паролю. " +
                    "В случае успеха возвращает JSON с именем и JWT."
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

        String jwtToken = userAuthService.login(userLogInPayload);

        return ResponseEntity.ok()
                .body(Map.of(
                        "token", jwtToken,
                        "email", userLogInPayload.email()
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
        emailService.sendRegistrationCompleteEmail(user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "email", user.getEmail()
        ));
    }

}
