package develop.backend.Club_card.service.impl;

import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.repository.UserRepository;
import develop.backend.Club_card.service.CurrencyInteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CurrencyInteractionServiceImpl implements CurrencyInteractionService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "validation.errors.id.is.null", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND));
    }
}
