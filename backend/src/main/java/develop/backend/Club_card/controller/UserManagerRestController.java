package develop.backend.Club_card.controller;

import develop.backend.Club_card.controller.payload.UserDeletePayload;
import develop.backend.Club_card.controller.payload.UserUpdatePrivilegePayload;
import develop.backend.Club_card.controller.payload.UserUpdateRolePayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

        managerService.updateUserRole(
                userUpdateRolePayload.username(), userUpdateRolePayload.role()
        );
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

        managerService.updateUserPrivilege(
                userUpdatePrivilegePayload.username(), userUpdatePrivilegePayload.privilege()
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Удаление пользователя по имени",
            description =
                    "Данная ручка может быть вызвана суперадином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. " +
                    "В теле указывается имя пользователя. В случае успеха - 204 ответ. ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(
            @Valid @RequestBody UserDeletePayload userDeletePayload,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails managerDetails
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            }

            throw new BindException(bindingResult);
        }

        managerService.deleteUser(userDeletePayload.username(), managerDetails);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получение списка всех пользователей",
            description =
                    "Данная ручка может быть вызвана суперадином (\"ROLE_OWNER\") или менеджером (\"ROLE_OWNER\"). " +
                    "В Authorization хэдере необходим JWT-токен. ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @GetMapping
    public List<User> findAllUsers() {
        return this.managerService.findAllUsers();
    }
}
