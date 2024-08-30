package develop.backend.Club_card.controller.payload.user;

import jakarta.validation.constraints.*;

import java.util.Date;

public record GetUserPayload(

        @NotNull(message = "{validation.errors.id.is.null}")
        Integer id,

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
        String privilege,

        @NotNull(message = "{validation.errors.date.of.birth.is.null}")
        @PastOrPresent(message = "{validation.errors.date.of.birth.is.in.future}")
        Date dateOfBirth
) {
}
