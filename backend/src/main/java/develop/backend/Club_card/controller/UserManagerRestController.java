package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.*;
import develop.backend.Club_card.entity.ArchivedUser;
import develop.backend.Club_card.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "user_manager_endpoints")
@RestController
@RequestMapping("/club-card/api/manager")
@RequiredArgsConstructor
public class UserManagerRestController {

    private final ManagerService managerService;

    @Operation(
            summary = "Обновление роли пользователя",
            description =
                    "Данная ручка может быть вызвана только суперадином (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В теле указывается имя пользователя и желаемая роль с префиксом ROLE_ из entity.enums. " +
                    "В случае успеха - ответ с 204 статусом. ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("update/role")
    public ResponseEntity<?> updateUserRole(
            @Valid @RequestBody UserUpdateRolePayload userUpdateRolePayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        managerService.updateUserRole(userUpdateRolePayload);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Обновление уровня привелегий пользователя",
            description =
                    "Данная ручка может быть вызвата только суперадином (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В теле указывается имя пользователя и желаемый уровень привелегий. " +
                    " с префиксом PRIVILEGE_ из entity.enums. " +
                    "В случае успеха - ответ с 204 статусом. ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("update/privilege")
    public ResponseEntity<?> updateUserPrivilege(
            @Valid @RequestBody UserUpdatePrivilegePayload userUpdatePrivilegePayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        managerService.updateUserPrivilege(userUpdatePrivilegePayload);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Просмотр архива пользователей",
            description =
                    "Данная ручка может быть вызвата только суперадином (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха - список с данными архивированных пользователей.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("get/archive")
    public List<ArchivedUser> findAllArchivedUsers() {
        return managerService.findAllArchivedUsers();
    }

    @Operation(
            summary = "Получение списка всех пользователей",
            description =
                    "Данная ручка может быть вызвана суперадином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха - список с данными действующих пользователей.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @GetMapping("get/users")
    public List<GetUserPayload> findAllUsers() {
        return this.managerService.findAllUsers();
    }

    @Operation(
            summary = "Получение списка заявок на удаление учётной записи",
            description = "Данная ручка может быть вызвана суперадином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха - список с данными по заявкам пользователей на удаление аккаунта.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @GetMapping("get/deletion-requests")
    public List<GetDeletionRequestPayload> findAllDeletionRequests() {
        return managerService.findAllDeletionRequests();
    }

    @Operation(
            summary = "Добавление пользователя в архив",
            description =
                    "Данная ручка может быть вызвана суперадином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В теле указывается username, password, email, role, privilege пользователя. " +
                    "В случае успеха - 201 статус.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @PostMapping("post/archive")
    public ResponseEntity<?> addUserToArchive(
            @Valid @RequestBody ArchivedUserPayload archivedUserPayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        managerService.addUserToArchive(archivedUserPayload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Удаление пользователя из основной таблицы",
            description = "Удаляет данные пользователя. " +
                    "Данная ручка может быть вызвана суперадином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха - 200 статус и JSON с данными для добавления пользователя в архив.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @DeleteMapping("delete/user")
    public ResponseEntity<?> deleteUserFromUserTable(
            @Valid @RequestBody UserNamePayload userNamePayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        ArchivedUserPayload archivedUserPayload = managerService.deleteUserFromUserTable(userNamePayload);
        return ResponseEntity.ok().body(archivedUserPayload);
    }

    @Operation(
            summary = "Удаление пользователя из архива",
            description = "Удаляет данные архивированного пользователя. " +
                    "Данная ручка может быть вызвана суперадином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В случае успеха - 204 статус.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("delete/archive")
    public ResponseEntity<?> deleteUserFromArchive(
            @Valid @RequestBody UserNamePayload userNamePayload,
            BindingResult bindingResult
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        managerService.deleteUserFromArchive(userNamePayload);
        return ResponseEntity.noContent().build();
    }
}
