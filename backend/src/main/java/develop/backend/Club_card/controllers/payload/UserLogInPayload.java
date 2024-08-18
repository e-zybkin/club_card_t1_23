package develop.backend.Club_card.controllers.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserLogInPayload(

        @NotNull(message = "{security.auth.errors.username.is.null}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.username.size.is.invalid}")
        String username,

        @NotNull(message = "{security.auth.errors.password.is.null}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.password.size.is.invalid}")
        String password
) {
}
