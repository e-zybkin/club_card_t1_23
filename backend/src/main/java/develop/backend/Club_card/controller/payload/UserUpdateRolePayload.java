package develop.backend.Club_card.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRolePayload(

        @NotNull(message = "{validation.errors.id.is.null}")
        Integer id,

        @Pattern(
                regexp = "ROLE_MANAGER|ROLE_MEMBER",
                message = "{validation.errors.role.does.not.exist}"
        )
        String role
) {
}
