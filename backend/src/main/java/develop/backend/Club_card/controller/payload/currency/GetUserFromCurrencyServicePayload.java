package develop.backend.Club_card.controller.payload.currency;

public record GetUserFromCurrencyServicePayload(

        String email,

        String firstName,

        String lastName,

        String middleName
) {
}
