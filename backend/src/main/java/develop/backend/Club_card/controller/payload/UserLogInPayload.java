package develop.backend.Club_card.controller.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLogInPayload(

        @NotBlank(message = "{security.auth.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.username.size.is.invalid}")
        String username,

        @NotBlank(message = "{security.auth.errors.password.is.blank}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.password.size.is.invalid}")
        String password
) {
}