package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.NotNull;

public record UserIdPayload(

        @NotNull(message = "{validation.errors.id.is.null}")
        Integer id
) {
}
