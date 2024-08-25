package develop.backend.Club_card.service;

import develop.backend.Club_card.entity.ArchivedUser;
import develop.backend.Club_card.entity.DeletionRequest;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.entity.enums.UserPrivilegesEnum;
import develop.backend.Club_card.entity.enums.UserRolesEnum;
import develop.backend.Club_card.repository.ArchivedUserRepository;
import develop.backend.Club_card.repository.DeletionRequestRepository;
import develop.backend.Club_card.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final UserRepository userRepository;
    private final DeletionRequestRepository deletionRequestRepository;
    private final ArchivedUserRepository archivedUserRepository;
    private final MessageSource messageSource;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<DeletionRequest> findAllDeletionRequests() {
        return deletionRequestRepository.findAll();
    }

    @Override
    public List<ArchivedUser> findAllArchivedUsers() {
        return archivedUserRepository.findAll();
    }

    @Override
    @Transactional
    public void moveUserToArchive(String username) {
        DeletionRequest deletionRequest = deletionRequestRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "delete.errors.deletion.request.does.not.exits", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "security.auth.errors.username.not.found", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));

        String password = user.getPassword();
        String email = user.getEmail();
        UserRolesEnum role = user.getRole();
        UserPrivilegesEnum privilege = user.getPrivilege();

        deletionRequestRepository.delete(deletionRequest);
        userRepository.delete(user);

        if (archivedUserRepository.existsArchivedUserByUsername(username)) {
            archivedUserRepository.deleteArchivedUserByUsername(username);
        }

        if (archivedUserRepository.existsArchivedUserByEmail(email)) {
            archivedUserRepository.deleteArchivedUserByEmail(email);
        }

        archivedUserRepository.save(new ArchivedUser(
                -1,
                username,
                password,
                email,
                role,
                privilege
        ));
    }

    @Override
    @Transactional
    public void deleteUserFromArchive(String username) {
        archivedUserRepository.deleteArchivedUserByUsername(username);
    }

    @Override
    @Transactional
    public void updateUserRole(String username, String role) {
        UserRolesEnum newRole = switch (role) {
            case "ROLE_MANAGER" -> UserRolesEnum.ROLE_MANAGER;
            case "ROLE_MEMBER" -> UserRolesEnum.ROLE_MEMBER;
            default -> throw new CustomException(this.messageSource.getMessage(
                    "validation.errors.role.does.not.exist", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
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
            default -> throw new CustomException(this.messageSource.getMessage(
                    "validation.errors.privilege.does.not.exist", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
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
