import { Dispatch, SetStateAction } from "react";
import { UserRoles, UserPriveleges } from "./enums"
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
  privilege: UserPriveleges,
}

export interface CardData {
  id: number,
  number: string,
  openingDate: string,
  dateOfExpiration: string,
  cvcCode: number,
  bonuses: number,
  isBlocked: boolean
  colour: CardColors,
  template: CardTemplates,
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
  birthday: Date;
}
