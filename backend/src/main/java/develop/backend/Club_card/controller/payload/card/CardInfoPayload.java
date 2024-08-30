package develop.backend.Club_card.controller.payload.card;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record CardInfoPayload (

    @NotNull(message = "{validation.errors.card.number.is.null}")
    Long number,

    @NotNull(message = "{validation.errors.date.of.birth.is.null}")
    @PastOrPresent(message = "{validation.errors.date.of.birth.is.in.future}")
    Date openingDate,

    @NotNull(message = "{validation.errors.date.of.birth.is.null}")
    Date dateOfExpiration,

    @NotNull(message = "{validation.errors.cvc.code.is.null}")
    Integer cvcCode,

    @NotNull(message = "{validation.errors.score.is.null}")
    Integer score,

    @NotNull(message = "{validation.errors.is.blocked.field.is.null}")
    Boolean isBlocked,

    @NotNull(message = "{validation.errors.colour.is.null}")
    @Pattern(
            regexp = "RED|GREEN|BLUE",
            message = "{validation.card.errors.invalid.colour}"
    )
    String colour,

    @NotNull(message = "{validation.errors.pattern.is.null}")
    @Pattern(
            regexp = "FULL|MIDDLE|LOW",
            message = "{validation.card.errors.invalid.pattern}"
    )
    String pattern,

    @NotNull(message = "{validation.errors.qr.code.is.null}")
    String qrCode
){
}
