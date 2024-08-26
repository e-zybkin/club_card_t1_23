package develop.backend.Club_card.controller.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record CardInfoPayload (

    Long number,

    Date openingDate,

    Date dateOfExpiration,

    Integer cvcCode,

    Integer score,

    Boolean isBlocked
){
}
