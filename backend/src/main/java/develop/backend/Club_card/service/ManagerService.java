package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ManagerService {
    List<GetUserPayload> findAllUsers();
    List<GetUserPayload> findAllUsersWhoSentDeletionRequest();
    void deleteUser(UserIdPayload userIdPayload, UserDetails userDetails);
    void updateUserRole(UserUpdateRolePayload userUpdateRolePayload);
    void updateUserPrivilege(UserUpdatePrivilegePayload userUpdatePrivilegePayload);
}
