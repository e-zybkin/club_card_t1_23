import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";
import { useLocation } from "react-router-dom";
import { UserRoles } from "../../utils/enums";

interface ProtectedRouteProps {
  children: React.ReactElement;
}

function ProtectedRoute({ children }: ProtectedRouteProps) {
  const userContext = useContext(CurrentUserContext);
  const location = useLocation();

  if (!userContext) {
    return <Navigate to="/signin" />;
  }

  const { loggedIn, currentUser } = userContext;

  if (!localStorage.getItem("jwt") || !loggedIn) {
    return <Navigate to="/signin" />;
  }

  if (currentUser.role === UserRoles.member && location.pathname === "/users") {
    return <Navigate to="/" />;
  }

  return children;
}

export default ProtectedRoute;
