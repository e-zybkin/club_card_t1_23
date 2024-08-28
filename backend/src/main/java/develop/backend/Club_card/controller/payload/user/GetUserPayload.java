package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GetUserPayload(

        @NotBlank(message = "{validation.errors.username.is.blank}")
        @Size(min = 5, max = 255, message = "{validation.errors.username.size.is.invalid}")
        String username,

        @NotBlank(message = "{validation.errors.password.is.blank}")
        @Size(min = 5, max = 255, message = "{validation.errors.password.size.is.invalid}")
        String password,

        @NotBlank(message = "{validation.errors.email.is.blank}")
        @Pattern(
                regexp = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "{validation.errors.email.format.is.invalid}"
        )
        String email,

        @NotNull(message = "{validation.errors.first.name.is.null}")
        String firstName,

        @NotNull(message = "{validation.errors.last.name.is.null}")
        String lastName,

        @NotNull(message = "{validation.errors.middle.name.is.null}")
        String middleName,

        @Pattern(
                regexp = "ROLE_MANAGER|ROLE_MEMBER",
                message = "{validation.errors.role.does.not.exist}"
        )
        String role,

        @Pattern(
                regexp = "PRIVILEGE_STANDARD|PRIVILEGE_HIGH|PRIVILEGE_VIP",
                message = "{validation.errors.privilege.does.not.exist}"
        )
        String privilege
) {
}
