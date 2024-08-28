package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.user.UserLogInPayload;
import develop.backend.Club_card.controller.payload.user.UserSignUpPayload;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.entity.enums.UserPrivilegesEnum;
import develop.backend.Club_card.entity.enums.UserRolesEnum;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.security.JwtTokenProvider;
import develop.backend.Club_card.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    @Value("${security.auth.super.admin.username}")
    private String superAdminUsername;

    @Value("${security.auth.super.admin.password}")
    private String superAdminPassword;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;

    @Override
    public String login(UserLogInPayload userLogInPayload) {
        String username = userLogInPayload.username();
        String password = userLogInPayload.password();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username);
        } catch (CustomException ex) {
            throw new CustomException(ex.getMessage(), ex.getHttpStatus());
        }
    }

    @Override
    public User signup(UserSignUpPayload userSignUpPayload) {
        String username = userSignUpPayload.username();
        String password = userSignUpPayload.password();
        String email = userSignUpPayload.email();

        if (userRepository.existsUserByUsername(username)) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.username.already.exists", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (userRepository.existsUserByEmail(email)) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.email.already.exists", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String passwordEncoded = passwordEncoder.encode(password);
        UserRolesEnum userRolesEnum = UserRolesEnum.ROLE_MEMBER;
        UserPrivilegesEnum userPrivilegesEnum = UserPrivilegesEnum.PRIVILEGE_STANDARD;

        if (username.equals(superAdminUsername) && password.equals(superAdminPassword)) {
            userRolesEnum = UserRolesEnum.ROLE_OWNER;
            userPrivilegesEnum = UserPrivilegesEnum.PRIVILEGE_VIP;
        }

        return userRepository.save(new User(
                -1,
                username,
                passwordEncoded,
                email,
                "",
                "",
                "",
                new Date(),
                false,
                false,
                userRolesEnum,
                userPrivilegesEnum,
                null,
                null
        ));
    }
}
