export enum UserRoles {
  unknown="ROLE_UNKNOWN", 
  owner = "ROLE_OWNER", 
  manager = "ROLE_MANAGER",
  member = "ROLE_MEMBER"
}

export enum UserPrivileges {
  unknown="PRIVILEGE_UNKNOWN",
  standart="PRIVILEGE_STANDARD",
  high="PRIVILEGE_HIGH",
  vip="PRIVILEGE_VIP"
}

export enum CardTemplates {
  FULL = "FULL",
  MIDDLE = "MIDDLE",
  LOW = "MINIMAL",
}

export enum CardColors {
  BLUE = "#00aae6",
  RED = "#ff2969",
  GREEN = "#88f657"
}
