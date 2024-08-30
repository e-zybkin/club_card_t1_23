package develop.backend.Club_card.controller.payload.currency;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositRequestPayload(

        @NotNull
        BigDecimal amount,

        @NotNull
        String comment,

        @NotNull
        String serviceName
) {
}
