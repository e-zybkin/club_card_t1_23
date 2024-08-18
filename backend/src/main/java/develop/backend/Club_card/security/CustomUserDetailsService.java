package develop.backend.Club_card.security;

import develop.backend.Club_card.exceptions.CustomException;
import develop.backend.Club_card.models.User;
import develop.backend.Club_card.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MessageSource messageSource;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> mayBeUser = userRepository.findUserByUsername(username);

        if (mayBeUser.isEmpty()) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.username.not.found", null, Locale.getDefault()
            ), HttpStatus.NOT_FOUND);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(mayBeUser.get().getPassword())
                .authorities(mayBeUser.get().getRole().getRoleInString())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
