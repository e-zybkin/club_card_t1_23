import { Dispatch, SetStateAction } from "react";
import { UserRoles, UserPrivileges } from "./enums"
import { CardColors, CardTemplates } from "./enums";

export interface UserLogin {
  email: string,
  password: string
}

export interface UserRegister {
  firstName: string,
  lastName: string,
  middleName: string,
  email: string,
  password: string,
}

export interface User {
  id: number,
  password: string,
  email: string,
  firstName: string,
  lastName: string,
  middleName: string,
  dateOfBirth: Date,
  role: UserRoles,
  privilege: UserPrivileges,
}

export interface CardData {
  id: number,
  number: number,
  openingDate: string,
  dateOfExpiration: string,
  cvcCode: number,
  bonuses: number,
  isBlocked: boolean
  colour: CardColors,
  pattern: CardTemplates,
  qrCode: string;
}

export interface CurrentUserContextType {
  currentUser: User | Record<PropertyKey, never>;
  setCurrentUser: Dispatch<SetStateAction<User | Record<PropertyKey, never>>>;
  loggedIn: boolean;
  setLoggedIn: Dispatch<SetStateAction<boolean>>;
}

export interface UserUpdate {
  firstName: string;
  lastName: string;
  middleName: string;
  email: string;
  dateOfBirth: Date;
}
