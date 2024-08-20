package develop.backend.Club_card.services;

import develop.backend.Club_card.exceptions.CustomException;
import develop.backend.Club_card.models.Card;
import develop.backend.Club_card.models.User;
import develop.backend.Club_card.models.enums.UserPrivilegesEnum;
import develop.backend.Club_card.models.enums.UserRolesEnum;
import develop.backend.Club_card.repositories.UserRepository;
import develop.backend.Club_card.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
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
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username);
        } catch (AuthenticationException ex) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.invalid.username.or.password", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public AbstractMap.SimpleEntry<User, String> signup(String username, String password, Date dateOfBirth) {
        if (userRepository.existsUserByUsername(username)) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.username.already.exists", null, Locale.getDefault()
            ), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String passwordEncoded = passwordEncoder.encode(password);
        UserRolesEnum userRolesEnum = UserRolesEnum.ROLE_UNKNOWN;
        UserPrivilegesEnum userPrivilegesEnum = UserPrivilegesEnum.PRIVILEGE_UNKNOWN;

        if (username.equals(superAdminUsername) && password.equals(superAdminPassword)) {
            userRolesEnum = UserRolesEnum.ROLE_OWNER;
            userPrivilegesEnum = UserPrivilegesEnum.PRIVILEGE_VIP;
        }

        User user = userRepository.save(new User(
                -1,
                username,
                passwordEncoded,
                dateOfBirth,
                userRolesEnum,
                userPrivilegesEnum,
                new Card()
        ));
        String jwtToken = jwtTokenProvider.createToken(username);

        return new AbstractMap.SimpleEntry<>(user, jwtToken);
    }
}
