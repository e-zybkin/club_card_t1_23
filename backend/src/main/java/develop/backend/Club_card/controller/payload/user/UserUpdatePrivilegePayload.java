package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdatePrivilegePayload(

        @NotBlank(message = "{validation.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{validation.errors.username.size.is.invalid}")
        String username,

        @Pattern(
                regexp = "PRIVILEGE_STANDARD|PRIVILEGE_HIGH|PRIVILEGE_VIP",
                message = "{validation.errors.privilege.does.not.exist}"
        )
        String privilege
) {
}
