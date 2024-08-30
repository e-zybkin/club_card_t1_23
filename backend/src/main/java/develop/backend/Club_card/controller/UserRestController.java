package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.user.GetUserPayload;
import develop.backend.Club_card.controller.payload.user.UserUpdatePayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user_rest_controller")
@RestController
@RequestMapping("/club-card/api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://10.4.56.74")
public class UserRestController {

    private final UserService userService;

    @Operation(
            summary = "Получение данных текущего пользователя",
            description =
                    "В случае успеха - возвращает JSON с данными текущего пользователя. " +
                    "В Authorization хэдере необходим JWT-токен",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные текущего пользователя получены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным логином не найден"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @GetMapping
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {

        log.info("Entered get user info user controller method");

        User user = userService.getCurrentUser(userDetails);

        log.info("Completed get user info user controller method");

        return ResponseEntity.ok().body(new GetUserPayload(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getRole().getRoleInString(),
                user.getPrivilege().getPrivilegeInString(),
                user.getDateOfBirth()
        ));
    }

    @Operation(
            summary = "Обновление данных текущего пользователя",
            description =
                    "В теле принимает ФИО, дату рождения, email. " +
                    "В Authorization хэдере необходим JWT-токен",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные текущего пользователя обновлены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным логином не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных в теле запроса"),
            @ApiResponse(responseCode = "422", description = "Пользователь с данным логином / email " +
                    "уже существует"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @PatchMapping
    public ResponseEntity<?> updateCurrentUserData(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserUpdatePayload userUpdatePayload,
            BindingResult bindingResult
    ) throws BindException {

        log.info("Entered update current user data user controller method");

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        User updatedUser = userService.updateCurrentUserData(userDetails, userUpdatePayload);

        log.info("Completed update current user data user controller method");

        return ResponseEntity.ok().body(updatedUser);
    }

    @Operation(
            summary = "Отправка заявки на удаление учётной записи пользователя",
            description = "В Authorization хэдере необходим JWT-токен.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос на удаление аккаунта успешно отправлен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @PostMapping
    public ResponseEntity<?> makeDeletionRequest(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Entered make deletion request for current user controller method");
        userService.makeDeletionRequest(userDetails);
        log.info("Completed making deletion request for current user controller method");
        return new ResponseEntity<>("Заявка на удаление отправлена успешно", HttpStatus.OK);
    }

}
