package develop.backend.Club_card.entity.enums.user;

public enum UserRolesEnum {
    ROLE_OWNER,
    ROLE_MANAGER,
    ROLE_MEMBER;

    public String getRoleInString() {
        return name();
    }
}
