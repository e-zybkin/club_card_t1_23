package develop.backend.Club_card.controllers;

import develop.backend.Club_card.controllers.payload.UserDeletePayload;
import develop.backend.Club_card.controllers.payload.UserUpdatePrivilegePayload;
import develop.backend.Club_card.controllers.payload.UserUpdateRolePayload;
import develop.backend.Club_card.models.User;
import develop.backend.Club_card.services.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club-card/api/manager")
@RequiredArgsConstructor
public class UserManagerRestController {

    private final ManagerService managerService;

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("update/role")
    public ResponseEntity<?> updateUserRole(@Valid @RequestBody UserUpdateRolePayload userUpdateRolePayload) {
        managerService.updateUserRole(
                userUpdateRolePayload.username(), userUpdateRolePayload.role()
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("update/privilege")
    public ResponseEntity<?> updateUserPrivilege(@Valid @RequestBody UserUpdatePrivilegePayload userUpdatePrivilegePayload) {
        managerService.updateUserPrivilege(
                userUpdatePrivilegePayload.username(), userUpdatePrivilegePayload.privilege()
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDeletePayload userDeletePayload,
                                        @AuthenticationPrincipal UserDetails managerDetails
    ) {
        managerService.deleteUser(userDeletePayload.username(), managerDetails);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    @GetMapping
    public List<User> findAllUsers() {
        return this.managerService.findAllUsers();
    }
}
