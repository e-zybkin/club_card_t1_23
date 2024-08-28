package develop.backend.Club_card.entity.enums.card;

public enum CardTemplatesEnum {
    FULL,
    MIDDLE,
    LOW;

    public String getTemplateInString() {
        return name();
    }
}
