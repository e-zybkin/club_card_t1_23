package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.UserLogInPayload;
import develop.backend.Club_card.controller.payload.UserSignUpPayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @Operation(
            summary = "Аутентификация пользователя",
            description =
                    "Выполняет аутентификацию по логину и паролю. " +
                    "В случае успеха возвращает JSON с именем и JWT."
    )
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

        String jwtToken = userAuthService.login(userLogInPayload.username(), userLogInPayload.password());
        return ResponseEntity.ok()
                .body(Map.of(
                        "token", jwtToken,
                        "username", userLogInPayload.username()
                ));
    }

    @Operation(
            summary = "Регистрация пользователя",
            description =
                    "Выполняет регистрацию пользователя по логину, паролю и email. " +
                    "В случае успеха возвращает JSON с именем пользователя и email."
    )
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

        User user = userAuthService.signup(
                userSignUpPayload.username(),
                userSignUpPayload.password(),
                userSignUpPayload.email()
        );

        return ResponseEntity.ok().body(Map.of(
                "username", user.getUsername(),
                "email", user.getEmail()
        ));
    }

}
