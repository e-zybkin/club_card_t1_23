package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserNamePayload(

        @NotBlank(message = "{validation.errors.username.is.blank}")
        @Size(min = 5, max = 25, message = "{validation.errors.username.size.is.invalid}")
        String username
) {
}
