import React from "react";
import { CurrentUserContextType } from "../utils/interfaces";

export const CurrentUserContext = React.createContext<CurrentUserContextType | null>(null);