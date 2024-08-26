package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.GetUserPayload;
import develop.backend.Club_card.controller.payload.UserUpdatePayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);

        return ResponseEntity.ok().body(new GetUserPayload(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getRole().getRoleInString(),
                user.getPrivilege().getPrivilegeInString()
        ));
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
            summary = "Отправка заявки на удаление учётной записи пользователя",
            description = "В Authorization хэдере необходим JWT-токен. В случае успеха - 201 статус",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<?> makeDeletionRequest(@AuthenticationPrincipal UserDetails userDetails) {
        userService.makeDeletionRequest(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
