package develop.backend.Club_card.services;

import develop.backend.Club_card.exceptions.CustomException;
import develop.backend.Club_card.models.User;
import develop.backend.Club_card.models.enums.UserPrivilegesEnum;
import develop.backend.Club_card.models.enums.UserRolesEnum;
import develop.backend.Club_card.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    protected final UserRepository userRepository;
    protected final MessageSource messageSource;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(String username, UserDetails managerDetails) {
        String managerRole = managerDetails.getAuthorities().toArray()[0].toString();
        Optional<User> mayBeUser = userRepository.findUserByUsername(username);

        if (mayBeUser.isPresent()) {
            String userRole = mayBeUser.get().getRole().getRoleInString();

            if (userRole.equals(managerRole) || (managerRole.equals("ROLE_MANAGER") && userRole.equals("ROLE_OWNER"))) {
                throw new CustomException(this.messageSource.getMessage(
                        "security.manager.errors.delete.user.with.inappropriate.role", null, Locale.getDefault()
                ), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }

        userRepository.deleteUserByUsername(username);
    }

    @Override
    @Transactional
    public void updateUserRole(String username, String role) {
        UserRolesEnum newRole = switch (role) {
            case "ROLE_MANAGER" -> UserRolesEnum.ROLE_MANAGER;
            case "ROLE_MEMBER" -> UserRolesEnum.ROLE_MEMBER;
            default -> UserRolesEnum.ROLE_UNKNOWN;
        };

        userRepository.findUserByUsername(username).ifPresentOrElse(
                user -> {
                    user.setRole(newRole);
                    userRepository.save(user);
                },
                () -> {
                    throw new CustomException(this.messageSource.getMessage(
                            "security.auth.errors.username.not.found", null, Locale.getDefault()
                    ), HttpStatus.NOT_FOUND);
                }
        );
    }

    @Override
    @Transactional
    public void updateUserPrivilege(String username, String privilege) {
        UserPrivilegesEnum newPrivilege = switch (privilege) {
            case "PRIVILEGE_STANDARD" -> UserPrivilegesEnum.PRIVILEGE_STANDARD;
            case "PRIVILEGE_HIGH" -> UserPrivilegesEnum.PRIVILEGE_HIGH;
            case "PRIVILEGE_VIP" -> UserPrivilegesEnum.PRIVILEGE_VIP;
            default -> UserPrivilegesEnum.PRIVILEGE_UNKNOWN;
        };

        userRepository.findUserByUsername(username).ifPresentOrElse(
                user -> {
                    user.setPrivilege(newPrivilege);
                    userRepository.save(user);
                },
                () -> {
                    throw new CustomException(this.messageSource.getMessage(
                            "security.auth.errors.username.not.found", null, Locale.getDefault()
                    ), HttpStatus.NOT_FOUND);
                }
        );
    }
}
