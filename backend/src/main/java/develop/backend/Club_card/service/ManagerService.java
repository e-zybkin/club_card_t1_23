package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.*;
import develop.backend.Club_card.entity.ArchivedUser;
import develop.backend.Club_card.entity.DeletionRequest;
import develop.backend.Club_card.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ManagerService {
    List<GetUserPayload> findAllUsers();
    List<GetDeletionRequestPayload> findAllDeletionRequests();
    List<ArchivedUser> findAllArchivedUsers();
    void addUserToArchive(ArchivedUserPayload archivedUserPayload);
    ArchivedUserPayload deleteUserFromUserTable(UserNamePayload userNamePayload);
    void deleteUserFromArchive(UserNamePayload userNamePayload);
    void updateUserRole(UserUpdateRolePayload userUpdateRolePayload);
    void updateUserPrivilege(UserUpdatePrivilegePayload userUpdatePrivilegePayload);
}
