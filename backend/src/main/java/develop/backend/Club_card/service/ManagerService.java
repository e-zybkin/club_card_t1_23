package develop.backend.Club_card.service;

import develop.backend.Club_card.entity.ArchivedUser;
import develop.backend.Club_card.entity.DeletionRequest;
import develop.backend.Club_card.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ManagerService {
    List<User> findAllUsers();
    List<DeletionRequest> findAllDeletionRequests();
    List<ArchivedUser> findAllArchivedUsers();
    void moveUserToArchive(String username);
    void deleteUserFromArchive(String username);
    void updateUserRole(String username, String role);
    void updateUserPrivilege(String username, String privilege);
}
