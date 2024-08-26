package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.UserUpdatePayload;
import develop.backend.Club_card.entity.DeletionRequest;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.repository.DeletionRequestRepository;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${security.auth.super.admin.username}")
    private String superAdminUsername;

    private final UserRepository userRepository;
    private final DeletionRequestRepository deletionRequestRepository;
    private final MessageSource messageSource;

    @Override
    public User getCurrentUser(UserDetails userDetails) {
        return userRepository.findUserByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "security.auth.errors.username.not.found", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void makeDeletionRequest(UserDetails userDetails) {
        if (userDetails.getUsername().equals(superAdminUsername)) {
            this.userRepository.deleteUserByUsername(superAdminUsername);
            return;
        }

        User user = this.getCurrentUser(userDetails);
        user.setIsPendingDeletion(true);

        DeletionRequest deletionRequest = new DeletionRequest(-1, user);
        user.setDeletionRequest(deletionRequest);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateCurrentUserData(UserDetails userDetails, UserUpdatePayload userUpdatePayload) {
        User user = this.getCurrentUser(userDetails);

        String newUsername = userUpdatePayload.username();
        String newEmail = userUpdatePayload.email();
        String oldUsername = user.getUsername();
        String oldEmail = user.getEmail();

        if (!newUsername.equals(oldUsername) && userRepository.existsUserByUsername(newUsername)) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.username.already.exists", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!newEmail.equals(oldEmail) && userRepository.existsUserByEmail(newEmail)) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.email.already.exists", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setUsername(newUsername);
        user.setEmail(newEmail);
        user.setDateOfBirth(userUpdatePayload.dateOfBirth());
        user.setFirstName(userUpdatePayload.firstName());
        user.setLastName(userUpdatePayload.lastName());
        user.setMiddleName(userUpdatePayload.middleName());

        userRepository.save(user);
    }
}
