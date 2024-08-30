package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.user.UserUpdatePayload;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${security.auth.super.admin.email}")
    private String superAdminEmail;

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public User getCurrentUser(UserDetails userDetails) {
        log.info("Entered get current user data user service method");
        return userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "security.auth.errors.email.not.found", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void makeDeletionRequest(UserDetails userDetails) {

        log.info("Entered make deletion user service method");

        if (userDetails.getUsername().equals(superAdminEmail)) {
            this.userRepository.deleteUserByEmail(superAdminEmail);
            return;
        }

        User user = this.getCurrentUser(userDetails);
        user.setIsPendingDeletion(true);

        userRepository.save(user);

        log.info("Completed deletion request user service method");
    }

    @Override
    @Transactional
    public User updateCurrentUserData(UserDetails userDetails, UserUpdatePayload userUpdatePayload) {

        log.info("Entered update user data user service method");

        User user = this.getCurrentUser(userDetails);
        String newEmail = userUpdatePayload.email();
        String oldEmail = user.getEmail();

        if (!newEmail.equals(oldEmail) && userRepository.existsUserByEmail(newEmail)) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.email.already.exists", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setEmail(newEmail);
        user.setDateOfBirth(userUpdatePayload.dateOfBirth());
        user.setFirstName(userUpdatePayload.firstName());
        user.setLastName(userUpdatePayload.lastName());
        user.setMiddleName(userUpdatePayload.middleName());

        log.info("Entered update user data user service method");

        return userRepository.save(user);
    }
}
