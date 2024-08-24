package develop.backend.Club_card.controller.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CardInfoPayload {

    private Long number;

    private Date openingDate;

    private Date dateOfExpiration;

    private Integer cvcCode;

    private Integer score;

    private Boolean isBlocked;
}
