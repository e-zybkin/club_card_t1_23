package develop.backend.Club_card.models.enums;

public enum UserRolesEnum {
    ROLE_UNKNOWN,
    ROLE_ADMIN,
    ROLE_MEMBER;

    public String getRoleInString() {
        return name();
    }
}
