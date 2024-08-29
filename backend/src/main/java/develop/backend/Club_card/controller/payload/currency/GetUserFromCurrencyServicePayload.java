package develop.backend.Club_card.controller.payload.currency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record GetUserFromCurrencyServicePayload(

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
        String surname,

        @NotNull(message = "currency.interaction.errors.coins.are.null")
        BigDecimal coins
) {
}
