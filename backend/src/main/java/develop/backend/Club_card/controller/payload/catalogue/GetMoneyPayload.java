package develop.backend.Club_card.controller.payload.catalogue;

import jakarta.validation.constraints.Min;

public record GetMoneyPayload(

        @Min(0)
        Integer count
) {
}
