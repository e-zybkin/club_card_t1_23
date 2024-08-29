package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdatePrivilegePayload(

        @NotNull(message = "{validation.errors.id.is.null}")
        Integer id,

        @Pattern(
                regexp = "PRIVILEGE_STANDARD|PRIVILEGE_HIGH|PRIVILEGE_VIP",
                message = "{validation.errors.privilege.does.not.exist}"
        )
        String privilege
) {
}
