package develop.backend.Club_card.controller.payload.currency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record GetUserToCurrencyServicePayload(

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
        String middleName
) {
}
