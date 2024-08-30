package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.user.UserLogInPayload;
import develop.backend.Club_card.controller.payload.user.UserSignUpPayload;
import develop.backend.Club_card.entity.enums.user.UserPrivilegesEnum;
import develop.backend.Club_card.entity.enums.user.UserRolesEnum;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.security.JwtTokenProvider;
import develop.backend.Club_card.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

    @Value("${security.auth.super.admin.email}")
    private String superAdminEmail;

    @Value("${security.auth.super.admin.password}")
    private String superAdminPassword;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;

    @Override
    public User signup(UserSignUpPayload userSignUpPayload) {

        log.info("Entered sign up user service method");

        String firstName = userSignUpPayload.firstName();
        String lastName = userSignUpPayload.lastName();
        String middleName = userSignUpPayload.middleName();
        String email = userSignUpPayload.email();
        String password = userSignUpPayload.password();

        if (userRepository.existsUserByEmail(email)) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.email.already.exists", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String passwordEncoded = passwordEncoder.encode(password);
        UserRolesEnum role = UserRolesEnum.ROLE_MEMBER;
        UserPrivilegesEnum privilege = UserPrivilegesEnum.PRIVILEGE_STANDARD;

        if (email.equals(superAdminEmail) && password.equals(superAdminPassword)) {
            role = UserRolesEnum.ROLE_OWNER;
            privilege = UserPrivilegesEnum.PRIVILEGE_VIP;
        }

        log.info("Completed user service signup method");

        return userRepository.save(new User(
                -1,
                firstName,
                lastName,
                middleName,
                email,
                passwordEncoded,
                new Date(),
                false,
                role,
                privilege,
                "",
                false,
                null
        ));
    }

    @Override
    public AbstractMap.SimpleEntry<User, String> login(UserLogInPayload userLogInPayload) {

        log.info("Entered user service login method");

        String email = userLogInPayload.email();
        String password = userLogInPayload.password();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                            "security.auth.errors.email.not.found", null, Locale.getDefault()
                    ), HttpStatus.NOT_FOUND));

            log.info("Completed user service login method");

            return new AbstractMap.SimpleEntry<>(user, jwtTokenProvider.createToken(email));

        } catch (CustomException ex) {
            log.info("Catch authentication exception");
            throw new CustomException(ex.getMessage(), ex.getHttpStatus());
        }
    }
}
