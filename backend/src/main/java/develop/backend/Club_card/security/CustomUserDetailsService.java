package develop.backend.Club_card.security;

import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String email) {
        Optional<User> mayBeUser = userRepository.findUserByEmail(email);

        if (mayBeUser.isEmpty()) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.email.not.found", null, Locale.getDefault()
            ), HttpStatus.NOT_FOUND);
        }

        if (mayBeUser.get().getIsPendingDeletion()) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.user.is.pending.deletion", null, Locale.getDefault()
            ), HttpStatus.FORBIDDEN);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(mayBeUser.get().getPassword())
                .authorities(mayBeUser.get().getRole().getRoleInString())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
