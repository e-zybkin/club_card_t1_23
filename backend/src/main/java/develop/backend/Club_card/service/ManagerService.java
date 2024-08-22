package develop.backend.Club_card.service;

import develop.backend.Club_card.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ManagerService {
    List<User> findAllUsers();
    void deleteUser(String username, UserDetails managerDetails);
    void updateUserRole(String username, String role);
    void updateUserPrivilege(String username, String privilege);
}
