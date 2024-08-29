package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.user.GetUserPayload;
import develop.backend.Club_card.controller.payload.user.UserIdPayload;
import develop.backend.Club_card.controller.payload.user.UserUpdatePrivilegePayload;
import develop.backend.Club_card.controller.payload.user.UserUpdateRolePayload;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ManagerService {
    List<GetUserPayload> findAllUsers(UserDetails userDetails);
    List<GetUserPayload> findAllUsersWhoSentDeletionRequest();
    void deleteUser(UserIdPayload userIdPayload, UserDetails userDetails);
    void updateUserRole(UserUpdateRolePayload userUpdateRolePayload);
    void updateUserPrivilege(UserUpdatePrivilegePayload userUpdatePrivilegePayload);
}
