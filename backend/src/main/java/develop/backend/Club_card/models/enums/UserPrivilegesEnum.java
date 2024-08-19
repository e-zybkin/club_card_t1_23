package develop.backend.Club_card.models.enums;

public enum UserPrivilegesEnum {
    PRIVILEGE_UNKNOWN,
    PRIVILEGE_STANDARD,
    PRIVILEGE_HIGH,
    PRIVILEGE_VIP;

    public String getPrivilegeInString() {
        return name();
    }
}
