package develop.backend.Club_card.controllers.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignUpPayload(

        @NotBlank(message = "{security.auth.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.username.size.is.invalid}")
        String username,

        @NotBlank(message = "{security.auth.errors.password.is.blank}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.password.size.is.invalid}")
        String password,

        @NotBlank(message = "{security.auth.errors.email.is.blank}")
        @Pattern(
                regexp = "^[\\w!#$%&'*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "{security.auth.errors.email.format.is.invalid}"
        )
        String email
) {
}
