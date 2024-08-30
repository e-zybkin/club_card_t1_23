package develop.backend.Club_card.controller.payload.catalogue;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MoneyAmountPayload(

        @NotNull(message = "{currency.interaction.errors.money.amount.is.null}")
        BigDecimal amount
) {
}
