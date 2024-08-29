package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.controller.payload.currency.GetUserToCurrencyServicePayload;
import develop.backend.Club_card.controller.payload.user.UserLogInPayload;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.CurrencyInteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CurrencyInteractionServiceImpl implements CurrencyInteractionService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;

    @Override
    public GetUserToCurrencyServicePayload logInUserFromCurrencyService(UserLogInPayload userLogInPayload) {
        String email = userLogInPayload.email();
        String password = userLogInPayload.password();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                            "security.auth.errors.email.not.found", null, Locale.getDefault()
                    ), HttpStatus.NOT_FOUND));
            return new GetUserToCurrencyServicePayload(
                    email,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getMiddleName()
            );
        } catch (CustomException ex) {
            throw new CustomException(ex.getMessage(), ex.getHttpStatus());
        }
    }
}
