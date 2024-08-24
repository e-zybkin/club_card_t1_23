import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";

interface ProtectedRouteProps {
  children: React.ReactElement;
}

function ProtectedRoute({ children }: ProtectedRouteProps) {
  const userContext = useContext(CurrentUserContext);

  if (!userContext) {
    return <Navigate to="/signin" />;
  }

  const { loggedIn /* currentUser */ } = userContext;

  if (!localStorage.getItem("jwt") || !loggedIn) {
    return <Navigate to="/signin" />;
  }

  return children;
}

export default ProtectedRoute;
