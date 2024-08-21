package develop.backend.Club_card.controllers;

import develop.backend.Club_card.controllers.payload.UserLogInPayload;
import develop.backend.Club_card.controllers.payload.UserSignUpPayload;
import develop.backend.Club_card.models.User;
import develop.backend.Club_card.services.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/club-card/api/auth")
public class UserAuthRestController {

    private final UserAuthService userAuthService;

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

    @PostMapping("signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignUpPayload userSignUpPayload,
                                    BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        AbstractMap.SimpleEntry<User, String> userToTokenMapping = userAuthService.signup(
                userSignUpPayload.username(),
                userSignUpPayload.password(),
                userSignUpPayload.email()
        );

        User user = userToTokenMapping.getKey();
        String jwtToken = userToTokenMapping.getValue();

        return ResponseEntity.ok().body(Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "token", jwtToken
        ));
    }

}
