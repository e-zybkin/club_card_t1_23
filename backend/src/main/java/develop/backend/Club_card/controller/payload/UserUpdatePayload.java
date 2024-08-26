package develop.backend.Club_card.controller.payload;

import jakarta.validation.constraints.*;

import java.util.Date;

public record UserUpdatePayload(

        @NotBlank(message = "{validation.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{validation.errors.username.size.is.invalid}")
        String username,

        @NotBlank(message = "{validation.errors.email.is.blank}")
        @Pattern(
                regexp = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "{validation.errors.email.format.is.invalid}"
        )
        String email,

        @NotBlank(message = "{validation.errors.first.name.is.blank}")
        String firstName,

        @NotBlank(message = "{validation.errors.last.name.is.blank}")
        String lastName,

        @NotBlank(message = "{validation.errors.middle.name.is.blank}")
        String middleName,

        @NotNull(message = "{validation.errors.date.of.birth.is.null}")
        @PastOrPresent(message = "{validation.errors.date.of.birth.is.in.future}")
        Date dateOfBirth
) {
}
