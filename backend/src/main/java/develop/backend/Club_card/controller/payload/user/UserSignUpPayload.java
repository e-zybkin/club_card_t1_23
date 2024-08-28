package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignUpPayload(

        @NotBlank(message = "{validation.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{validation.errors.username.size.is.invalid}")
        String username,

        @NotBlank(message = "{validation.errors.password.is.blank}")
        @Size(min = 5, max = 255, message = "{validation.errors.password.size.is.invalid}")
        String password,

        @NotBlank(message = "{validation.errors.email.is.blank}")
        @Pattern(
                regexp = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "{validation.errors.email.format.is.invalid}"
        )
        String email
) {
}
