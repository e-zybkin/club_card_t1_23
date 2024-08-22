package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.UserUpdatePayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user_endpoints")
@RestController
@RequestMapping("/club-card/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Operation(
            summary = "Получение данный текущего пользователя",
            description =
                    "В случае успеха - возвращает JSON с данными текущего пользователя. " +
                    "В Authorization хэдере необходим JWT-токен",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getCurrentUser(userDetails);
    }

    @Operation(
            summary = "Обновление данных текущего пользователя",
            description =
                    "В теле принимает ФИО, дату рождения, имя пользователя, email. " +
                    "В Authorization хэдере необходим JWT-токен",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping
    public ResponseEntity<?> updateCurrentUserData(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserUpdatePayload userUpdatePayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        userService.updateCurrentUserData(userDetails, userUpdatePayload);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Удаление аккаунта текущего пользователя",
            description =
                    "В случае успеха - 204 статус. " +
                    "В Authorization хэдере необходим JWT-токен",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping
    public ResponseEntity<?> deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteCurrentUser(userDetails);
        return ResponseEntity.noContent().build();
    }
}
