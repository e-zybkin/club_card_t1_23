package develop.backend.Club_card.controller.payload.card;


import jakarta.validation.constraints.Pattern;

public record CreationCardPayload(

    @Pattern(
        regexp = "RED|GREEN|BLUE",
        message = "{validation.card.errors.invalid.colour}"
    )
    String colour,

    @Pattern(
        regexp = "FULL|MIDDLE|LOW",
        message = "{validation.card.errors.invalid.pattern}"
    )
    String pattern
)
{
}
