package develop.backend.Club_card.controller.payload.catalogue;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record GetMoneyPayload(

        @Min(0)
        BigDecimal count,

        @NotBlank(message = "{validation.errors.email.is.blank}")
        @Pattern(
            regexp = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "{validation.errors.email.format.is.invalid}"
        )
        String email
) {
}
