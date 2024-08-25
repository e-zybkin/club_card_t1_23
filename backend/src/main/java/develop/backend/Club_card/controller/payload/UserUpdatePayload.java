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

        @NotNull(message = "{validation.errors.first.name.is.null}")
        String firstName,

        @NotNull(message = "{validation.errors.last.name.is.null}")
        String lastName,

        @NotNull(message = "{validation.errors.middle.name.is.null}")
        String middleName,

        @NotNull(message = "{validation.errors.date.of.birth.is.null}")
        @PastOrPresent(message = "{validation.errors.date.of.birth.is.in.future}")
        Date dateOfBirth
) {
}
