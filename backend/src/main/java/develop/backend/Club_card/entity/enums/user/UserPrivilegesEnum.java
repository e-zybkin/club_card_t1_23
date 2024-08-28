package develop.backend.Club_card.entity.enums.user;

public enum UserPrivilegesEnum {
    PRIVILEGE_STANDARD,
    PRIVILEGE_HIGH,
    PRIVILEGE_VIP;

    public String getPrivilegeInString() {
        return name();
    }
}
