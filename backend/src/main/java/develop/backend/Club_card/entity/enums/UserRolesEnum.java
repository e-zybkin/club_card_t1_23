package develop.backend.Club_card.entity.enums;

public enum UserRolesEnum {
    ROLE_UNKNOWN,
    ROLE_OWNER,
    ROLE_MANAGER,
    ROLE_MEMBER;

    public String getRoleInString() {
        return name();
    }
}
