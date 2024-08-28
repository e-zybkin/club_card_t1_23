package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.user.ArchivedUserPayload;
import develop.backend.Club_card.controller.payload.user.GetDeletionRequestPayload;
import develop.backend.Club_card.controller.payload.user.GetUserPayload;
import develop.backend.Club_card.controller.payload.user.UserNamePayload;
import develop.backend.Club_card.controller.payload.user.UserUpdatePrivilegePayload;
import develop.backend.Club_card.controller.payload.user.UserUpdateRolePayload;
import develop.backend.Club_card.entity.ArchivedUser;

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
