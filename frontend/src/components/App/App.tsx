import { useState, useEffect, useMemo } from "react";
import { Route, Routes, useNavigate, Navigate } from "react-router-dom";

import Header from "../Header/Header";
import Main from "../Main/Main";
import Register from "../Register/Register";
import Login from "../Login/Login";
import PageNotFound from "../PageNotFound/PageNotFound";
import ProtectedRoute from "../ProtectedRoute/ProtectedRoute";
import PopupEditUser from "../Popups/PopupEditUser";
import PopupCreateCard from "../Popups/PopupCreateCard";
import Loader from "../Loader/Loader";
// import { UsersList } from "../UsersList/UsersList";
import { PopupDialog } from "../Popups/PopupDialog";

import {
  UserLogin,
  UserRegister,
  User,
  UserUpdate,
  CardData,
} from "../../utils/interfaces";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";

import * as userApi from "../../utils/api/auth";
import * as cardApi from "../../utils/api/card";

import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import "primeflex/primeflex.css";
import styles from "./App.module.css";
import { CardColors, CardTemplates } from "../../utils/enums";

function App() {
  const [currentUser, setCurrentUser] = useState<
    User | Record<PropertyKey, never>
  >({});
  const [cardData, setCardData] = useState<CardData | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [loggedIn, setLoggedIn] = useState<boolean>(false);

  const [isPopupEditUserOpen, setIsPopupEditUserOpen] =
    useState<boolean>(false);
  const [isPopupCreateCardOpen, setIsPopupCreateCardOpen] =
    useState<boolean>(false);

  const [showMessage, setShowMessage] = useState(false);
  const [isDialogError, setIsDialogError] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");

  const navigate = useNavigate();

  const user = useMemo(
    () => ({
      currentUser,
      setCurrentUser,
      loggedIn,
      setLoggedIn,
    }),
    [currentUser, loggedIn]
  );

  useEffect(() => {
    if (localStorage.getItem("jwt")) {
      userApi
        .getMyInfo()
        .then((res) => {
          if (res) {
            setLoggedIn(true);
            navigate("/");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);

  useEffect(() => {
    if (loggedIn) {
      userApi
        .getMyInfo()
        .then((result) => {
          setCurrentUser(result);
        })
        .catch((err) => {
          console.log(err);
        });

      cardApi
        .getCardInfo()
        .then((result) => {
          setCardData(result);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [loggedIn]);

  const showDialog = (message: string, status: boolean) => {
    setDialogMessage(message);
    if (status === false) {
      setTimeout(() => {
        setShowMessage(false);
      }, 1200);
    }
    setIsDialogError(status);
    setShowMessage(true);
  };

  const handleLogin = async (data: UserLogin): Promise<void> => {
    setIsLoading(true);
    try {
      const res = await userApi.authorize(data);
      localStorage.setItem("jwt", res.token);
      setLoggedIn(true);
      navigate("/");
      showDialog("Вы успешно вошли!", false);
    } catch (err) {
      showDialog(err?.message, true);
    } finally {
      setIsLoading(false);
    }
  };

  const handleRegister = async (data: UserRegister): Promise<void> => {
    setIsLoading(true);
    try {
      await userApi.register(data);
      handleLogin({ email: data.email, password: data.password });
      navigate("/sign-in");
    } catch (err) {
      showDialog(err?.message, true);
    } finally {
      setIsLoading(false);
    }
  };

  const handleUpdateUser = (userData: UserUpdate) => {
    setIsLoading(true);
    userApi
      .updUserData(userData)
      .then((result) => {
        setCurrentUser(result);

        setIsPopupEditUserOpen(false);
      })
      .catch((err) => {
        setIsPopupEditUserOpen(false);
        showDialog(err?.message, true);
      })
      .finally(() => {
        setIsLoading(false);
        closeAllPopups();
      });
  };

  const handleCreateCard = (
    colorName: keyof typeof CardColors,
    templateName: keyof typeof CardTemplates
  ) => {
    setIsLoading(true);
    cardApi
      .createCard(colorName, templateName)
      .then((res) => {
        setCardData(res);
      })
      .catch((err) => {
        showDialog(err?.message, true);
      })
      .finally(() => {
        setIsLoading(false);
        closeAllPopups();
      });
  };

  const signOut = (): void => {
    localStorage.removeItem("jwt");
    setLoggedIn(false);
    setCurrentUser({});
    setCardData(null);
    navigate("/signin");
  };

  const closeAllPopups = (): void => {
    setIsPopupEditUserOpen(false);
    setIsPopupCreateCardOpen(false);
  };

  return (
    <CurrentUserContext.Provider value={user}>
      <div className={styles.page}>
        <div className={styles.wrapper}>
          <Header signOut={signOut} editUser={setIsPopupEditUserOpen} />

          <Routes>
            <Route
              path="/"
              element={
                <ProtectedRoute
                  children={
                    <Main
                      card={cardData}
                      createCard={setIsPopupCreateCardOpen}
                    />
                  }
                />
              }
            />

            {/* <Route
              path="/users"
              element={
                <ProtectedRoute
                  children={
                    <UsersList
                      getAllUsers={handleGetAllUsers}
                      users={allUsers}
                      onChangeRole={handleChangeRole}
                      onDeleteUser={openDeleteUserPopup}
                    />
                  }
                />
              }
            /> */}

            <Route
              path="/signup"
              element={<Register handleRegister={handleRegister} />}
            />

            <Route
              path="/signin"
              element={<Login handleLogin={handleLogin} />}
            />

            <Route
              path="/"
              element={
                loggedIn ? <Navigate to="/" /> : <Navigate to="/sign-in" />
              }
            />

            <Route path="*" element={<PageNotFound />} />
          </Routes>

          <PopupDialog
            showMessage={showMessage}
            setShowMessage={setShowMessage}
            isDialogError={isDialogError}
            dialogMessage={dialogMessage}
          />

          <PopupEditUser
            visible={isPopupEditUserOpen}
            onClose={closeAllPopups}
            onSubmit={handleUpdateUser}
          />

          <PopupCreateCard
            visible={isPopupCreateCardOpen}
            onClose={closeAllPopups}
            onSubmit={handleCreateCard}
          />
        </div>
        <Loader isLoading={isLoading} />
      </div>
    </CurrentUserContext.Provider>
  );
}

export default App;
