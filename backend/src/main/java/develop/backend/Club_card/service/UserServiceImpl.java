package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.UserUpdatePayload;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
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
    public void deleteCurrentUser(UserDetails userDetails) {
        userRepository.deleteUserByUsername(userDetails.getUsername());
    }

    @Override
    @Transactional
    public void updateCurrentUserData(UserDetails userDetails, UserUpdatePayload userUpdatePayload) {
        User user = userRepository.findUserByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "security.auth.errors.username.not.found", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));

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
