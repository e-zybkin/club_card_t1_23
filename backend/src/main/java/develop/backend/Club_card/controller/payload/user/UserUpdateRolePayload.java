package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRolePayload(

        @Pattern(
                regexp = "ROLE_MANAGER|ROLE_MEMBER",
                message = "{validation.errors.role.does.not.exist}"
        )
        String role
) {
}
