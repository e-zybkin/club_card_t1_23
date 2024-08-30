package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.user.GetUserPayload;
import develop.backend.Club_card.controller.payload.user.UserUpdatePrivilegePayload;
import develop.backend.Club_card.controller.payload.user.UserUpdateRolePayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "user_manager_rest_controller")
@RestController
@RequestMapping("/club-card/api/manager")
@RequiredArgsConstructor
@Slf4j
public class UserManagerRestController {

    private final ManagerService managerService;

    @Operation(
            summary = "Обновление роли пользователя",
            description = "Данная ручка может быть вызвана только супер-адином (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В url запроса указывается id пользователя, в теле запроса - желаемая роль " +
                    "с префиксом ROLE_ из entity.enums",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных в теле запроса"),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным id не найден"),
            @ApiResponse(responseCode = "401", description = "Отправитель не аутентифицирован / " +
                    "имеет недостаточно прав доступа"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("update/role/{id:\\d+}")
    public ResponseEntity<?> updateUserRole(
            @Valid @RequestBody UserUpdateRolePayload userUpdateRolePayload,
            @PathVariable("id") Integer id,
            BindingResult bindingResult
    ) throws BindException {

        log.info("Entered update role manager controller method");

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        managerService.updateUserRole(id, userUpdateRolePayload);

        log.info("Role update success");

        return ResponseEntity.ok().body(Map.of(
                "role", userUpdateRolePayload.role()
        ));
    }

    @Operation(
            summary = "Обновление уровня привилегий пользователя",
            description =
                    "Данная ручка может быть вызвана супер-адином (\"ROLE_OWNER\") или менеджером (\"ROLE_MANAGER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В url запроса указывается id пользователя, в теле запроса - желаемый уровень привелегий. " +
                    " с префиксом PRIVILEGE_ из entity.enums.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Привилегия успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных в теле запроса"),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным id не найден"),
            @ApiResponse(responseCode = "401", description = "Отправитель не аутентифицирован / " +
                    "имеет недостаточно прав доступа"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @PatchMapping("update/privilege/{id:\\d+}")
    public ResponseEntity<?> updateUserPrivilege(
            @Valid @RequestBody UserUpdatePrivilegePayload userUpdatePrivilegePayload,
            @PathVariable("id") Integer id,
            BindingResult bindingResult
    ) throws BindException {

        log.info("Entered update privilege manager controller method");

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        managerService.updateUserPrivilege(id, userUpdatePrivilegePayload);

        log.info("Update privilege success");

        return ResponseEntity.ok().body(Map.of(
                "privilege", userUpdatePrivilegePayload.privilege()
        ));
    }

    @Operation(
            summary = "Получение списка всех пользователей",
            description =
                    "Данная ручка может быть вызвана супер-адином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха возвращает список с данными действующих пользователей.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен"),
            @ApiResponse(responseCode = "401", description = "Отправитель не аутентифицирован / " +
                    "имеет недостаточно прав доступа"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @GetMapping("get/users")
    public List<User> findAllUsers(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Entered get all users manager controller method");
        return this.managerService.findAllUsers(userDetails);
    }

    @Operation(
            summary = "Получение списка заявок на удаление учётной записи",
            description = "Данная ручка может быть вызвана супер-адином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха возвращает список с данными по заявкам пользователей на удаление аккаунта.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заявок на удаление аккаунта получен"),
            @ApiResponse(responseCode = "401", description = "Отправитель не аутентифицирован / " +
                    "имеет недостаточно прав доступа"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @GetMapping("get/deletion-requests")
    public List<GetUserPayload> findAllDeletionRequests() {
        log.info("Entered get deletion requests from users manager controller method");
        return managerService.findAllUsersWhoSentDeletionRequest();
    }

    @Operation(
            summary = "Удаление пользователя из основной таблицы",
            description = "Удаляет данные пользователя. " +
                    "Данная ручка может быть вызвана супер-админом (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха возвращает JSON с данными для добавления пользователя в архив. " +
                    "В url указывается id удаляемого пользователя",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удалён из основной таблицы"),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных в теле запроса"),
            @ApiResponse(responseCode = "401", description = "Отправитель не аутентифицирован / " +
                    "имеет недостаточно прав доступа"),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным логином не найден"),
            @ApiResponse(responseCode = "500", description = "Недействительный JWT-токен")
    })
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @DeleteMapping("delete/user/{id:\\d+}")
    public ResponseEntity<?> deleteUserFromUserTable(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Integer id
    ) {
        log.info("Entered delete user manager controller method");
        managerService.deleteUser(id, userDetails);
        log.info("Delete user success");
        return ResponseEntity.ok().body("Пользователь был успешно удален");
    }
}
