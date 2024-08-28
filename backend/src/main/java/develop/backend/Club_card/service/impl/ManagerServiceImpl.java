package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.user.ArchivedUserPayload;
import develop.backend.Club_card.controller.payload.user.GetDeletionRequestPayload;
import develop.backend.Club_card.controller.payload.user.GetUserPayload;
import develop.backend.Club_card.controller.payload.user.UserNamePayload;
import develop.backend.Club_card.controller.payload.user.UserUpdatePrivilegePayload;
import develop.backend.Club_card.controller.payload.user.UserUpdateRolePayload;
import develop.backend.Club_card.entity.ArchivedUser;
import develop.backend.Club_card.entity.DeletionRequest;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.entity.enums.user.UserPrivilegesEnum;
import develop.backend.Club_card.entity.enums.user.UserRolesEnum;
import develop.backend.Club_card.repository.ArchivedUserRepository;
import develop.backend.Club_card.repository.DeletionRequestRepository;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<GetUserPayload> findAllUsers() {
         List<User> userList = userRepository.findAll();
         List<GetUserPayload> getUserPayloadList = new ArrayList<>();

         for (User user : userList) {
            getUserPayloadList.add(new GetUserPayload(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getMiddleName(),
                    user.getRole().getRoleInString(),
                    user.getPrivilege().getPrivilegeInString()
            ));
         }

         return getUserPayloadList;
    }

    @Override
    public List<GetDeletionRequestPayload> findAllDeletionRequests() {
        List<DeletionRequest> deletionRequestList = deletionRequestRepository.findAll();
        List<GetDeletionRequestPayload> getDeletionRequestPayloadList = new ArrayList<>();

        for (DeletionRequest deletionRequest : deletionRequestList) {
            getDeletionRequestPayloadList.add(new GetDeletionRequestPayload(
                    deletionRequest.getId(),
                    deletionRequest.getUser().getUsername()
            ));
        }

        return getDeletionRequestPayloadList;
    }

    @Override
    public List<ArchivedUser> findAllArchivedUsers() {
        return archivedUserRepository.findAll();
    }

    @Override
    @Transactional
    public void addUserToArchive(ArchivedUserPayload archivedUserPayload) {
        String username = archivedUserPayload.username();
        String password = archivedUserPayload.password();
        String email = archivedUserPayload.email();
        UserRolesEnum role = getUserRolesEnumFromString(archivedUserPayload.role());
        UserPrivilegesEnum privilege = getUserPrivilegeEnumFromString(archivedUserPayload.privilege());

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
    public ArchivedUserPayload deleteUserFromUserTable(UserNamePayload userNamePayload) {
        User user = userRepository.findUserByUsername(userNamePayload.username())
                        .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                                "security.auth.errors.username.not.found", null, Locale.getDefault()
                        ), HttpStatus.NOT_FOUND));

        userRepository.deleteUserByUsername(user.getUsername());
        return new ArchivedUserPayload(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getRoleInString(),
                user.getPrivilege().getPrivilegeInString()
        );
    }

    @Override
    @Transactional
    public void deleteUserFromArchive(UserNamePayload userNamePayload) {
        archivedUserRepository.deleteArchivedUserByUsername(userNamePayload.username());
    }

    @Override
    @Transactional
    public void updateUserRole(UserUpdateRolePayload userUpdateRolePayload) {
        String username = userUpdateRolePayload.username();
        UserRolesEnum newRole = getUserRolesEnumFromString(userUpdateRolePayload.role());

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
    public void updateUserPrivilege(UserUpdatePrivilegePayload userUpdatePrivilegePayload) {
        String username = userUpdatePrivilegePayload.username();
        UserPrivilegesEnum newPrivilege = getUserPrivilegeEnumFromString(userUpdatePrivilegePayload.privilege());

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

    private UserRolesEnum getUserRolesEnumFromString(String role) {
        return switch (role) {
            case "ROLE_MANAGER" -> UserRolesEnum.ROLE_MANAGER;
            case "ROLE_MEMBER" -> UserRolesEnum.ROLE_MEMBER;
            default -> throw new CustomException(this.messageSource.getMessage(
                    "validation.errors.role.does.not.exist", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        };
    }

    private UserPrivilegesEnum getUserPrivilegeEnumFromString(String privilege) {
        return switch (privilege) {
            case "PRIVILEGE_STANDARD" -> UserPrivilegesEnum.PRIVILEGE_STANDARD;
            case "PRIVILEGE_HIGH" -> UserPrivilegesEnum.PRIVILEGE_HIGH;
            case "PRIVILEGE_VIP" -> UserPrivilegesEnum.PRIVILEGE_VIP;
            default -> throw new CustomException(this.messageSource.getMessage(
                    "validation.errors.privilege.does.not.exist", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        };
    }
}
