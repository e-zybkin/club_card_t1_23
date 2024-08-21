package develop.backend.Club_card.controllers.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record UserUpdatePayload(

        @NotBlank(message = "{security.auth.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.username.size.is.invalid}")
        String username,

        @NotBlank(message = "{security.auth.errors.email.is.blank}")
        @Pattern(
                regexp = "^[\\w!#$%&'*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "{security.auth.errors.email.format.is.invalid}"
        )
        String email,

        @NotNull(message = "{security.update.errors.first.name.is.null}")
        String firstName,

        @NotNull(message = "{security.update.errors.last.name.is.null}")
        String lastName,

        @NotNull(message = "{security.update.errors.middle.name.is.null}")
        String middleName,

        @NotNull(message = "{security.update.errors.date.of.birth.is.null}")
        Date dateOfBirth
) {
}
