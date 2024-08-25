package develop.backend.Club_card.controller.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRolePayload(

        @NotBlank(message = "{validation.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{validation.errors.username.size.is.invalid}")
        String username,

        @Pattern(
                regexp = "ROLE_MANAGER|ROLE_MEMBER",
                message = "{validation.errors.role.does.not.exist}"
        )
        String role
) {
}
