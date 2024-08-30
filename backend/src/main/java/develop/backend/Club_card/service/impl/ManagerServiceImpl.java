package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.user.GetUserPayload;
import develop.backend.Club_card.controller.payload.user.UserUpdatePrivilegePayload;
import develop.backend.Club_card.controller.payload.user.UserUpdateRolePayload;
import develop.backend.Club_card.entity.enums.user.UserPrivilegesEnum;
import develop.backend.Club_card.entity.enums.user.UserRolesEnum;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public List<User> findAllUsers(UserDetails userDetails) {

        log.info("Entered find all users manager service method");

        User requestSender = userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                "security.auth.errors.email.not.found", null, Locale.getDefault()
        ), HttpStatus.NOT_FOUND));

        List<User> userList = userRepository.findAll();
        List<User> getUserList = new ArrayList<>();

        for (User user : userList) {
            if (!user.getId().equals(requestSender.getId()) && roleFilter(requestSender.getRole(), user.getRole())) {
                getUserList.add(user);
            }
        }

        log.info("Completed find all users manager service method");

        return getUserList;
    }

    @Override
    public List<GetUserPayload> findAllUsersWhoSentDeletionRequest() {

        log.info("Entered find all users who sent deletion request manager service method");

        List<User> userList = userRepository.findAll();
        List<GetUserPayload> usersWhoSentDeletionReqList = new ArrayList<>();

        for (User user : userList) {
            if (user.getIsPendingDeletion()) {
                usersWhoSentDeletionReqList.add(new GetUserPayload(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getMiddleName(),
                        user.getRole().getRoleInString(),
                        user.getPrivilege().getPrivilegeInString(),
                        user.getDateOfBirth()
                ));
            }
        }

        log.info("Completed find all users who sent deletion request manager service method");

        return usersWhoSentDeletionReqList;
    }

    @Override
    @Transactional
    public void updateUserRole(Integer id, UserUpdateRolePayload userUpdateRolePayload) {

        log.info("Entered update user role manager service method");

        UserRolesEnum newRole = getUserRolesEnumFromString(userUpdateRolePayload.role());

        userRepository.findById(id).ifPresentOrElse(
                user -> {
                    user.setRole(newRole);
                    userRepository.save(user);
                    log.info("Completed update user role manager service method");
                },
                () -> {
                    log.info("User with this id does not exist");
                    throw new CustomException(this.messageSource.getMessage(
                            "security.auth.errors.id.not.found", null, Locale.getDefault()
                    ), HttpStatus.NOT_FOUND);
                }
        );
    }

    @Override
    @Transactional
    public void updateUserPrivilege(Integer id, UserUpdatePrivilegePayload userUpdatePrivilegePayload) {

        log.info("Entered update user privilege manager service method");

        UserPrivilegesEnum newPrivilege = getUserPrivilegeEnumFromString(userUpdatePrivilegePayload.privilege());

        userRepository.findById(id).ifPresentOrElse(
                user -> {
                    user.setPrivilege(newPrivilege);
                    userRepository.save(user);
                    log.info("Completed update user privilege manager service method");
                },
                () -> {
                    log.info("User with that id does not exist");
                    throw new CustomException(this.messageSource.getMessage(
                            "security.auth.errors.id.not.found", null, Locale.getDefault()
                    ), HttpStatus.NOT_FOUND);
                }
        );
    }

    @Override
    @Transactional
    public void deleteUser(Integer id, UserDetails userDetails) {

        log.info("Entered delete user manager service method");

        User requestCaller = userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "security.auth.errors.email.not.found", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));

        User deletingUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "delete.errors.user.not.found", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));

        if (requestCaller.getRole().getRoleInString().equals("ROLE_MANAGER") &&
                (deletingUser.getRole().getRoleInString().equals("ROLE_MANAGER") ||
                deletingUser.getRole().getRoleInString().equals("ROLE_ADMIN"))
        ) {
            throw new CustomException(this.messageSource.getMessage(
                    "delete.errors.delete.user.with.inappropriate.role", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        userRepository.deleteById(id);

        log.info("Completed delete user manager service method");
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

    private boolean roleFilter(UserRolesEnum requestSenderRole, UserRolesEnum userRole) {
        if (requestSenderRole.equals(UserRolesEnum.ROLE_MANAGER)) {
            return userRole.equals(UserRolesEnum.ROLE_MEMBER);
        }

        return true;
    }
}
