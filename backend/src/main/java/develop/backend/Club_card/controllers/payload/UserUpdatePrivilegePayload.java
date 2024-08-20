package develop.backend.Club_card.controllers.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdatePrivilegePayload(

        @NotNull(message = "{security.auth.errors.username.is.null}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.username.size.is.invalid}")
        String username,

        @Pattern(regexp = "PRIVILEGE_STANDARD|PRIVILEGE_HIGH|PRIVILEGE_VIP")
        String privilege
) {
}
