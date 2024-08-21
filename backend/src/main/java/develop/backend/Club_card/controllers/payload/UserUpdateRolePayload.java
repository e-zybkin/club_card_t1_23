package develop.backend.Club_card.controllers.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRolePayload(

        @NotNull(message = "{security.auth.errors.username.is.null}")
        @Size(min = 5, max = 255, message = "{security.auth.errors.username.size.is.invalid}")
        String username,

        @Pattern(regexp = "ROLE_MANAGER|ROLE_MEMBER", message = "{security.auth.errors.role.does.not.exist}")
        String role
) {
}
