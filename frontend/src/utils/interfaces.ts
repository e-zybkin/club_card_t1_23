import { Dispatch, SetStateAction } from "react";
import { UserRoles, UserPriveleges } from "./enums"

export interface UserLogin {
  username: string,
  password: string
}

export interface UserRegister {
  username: string,
  email: string,
  password: string,
}

export interface User {
  id: number,
  username: string,
  password: string,
  email: string,
  firstName: string,
  lastName: string,
  middleName: string,
  dateOfBirth: Date,
  role: UserRoles,
  privilege: UserPriveleges,
  card: Card
}

export interface Card {
  id: number,
  number: string,
  openingDate: string,
  dateOfExpiration: string,
  cvcCode: number,
  bonuses: number,
  isBlocked: boolean
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
  username: string;
  email: string;
  birthday: Date;
}
