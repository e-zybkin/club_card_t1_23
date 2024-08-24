import { useState, useEffect, useMemo } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";

import Header from "../Header/Header";
import Main from "../Main/Main";
import Register from "../Register/Register";
import Login from "../Login/Login";
import PageNotFound from "../PageNotFound/PageNotFound";
import ProtectedRoute from "../ProtectedRoute/ProtectedRoute";
import InfoPopup from "../InfoPopup/InfoPopup";
import PopupEditUser from "../PopupEditUser/PopupEditUser";
import Loader from "../Loader/Loader";

import {
  UserLogin,
  UserRegister,
  User,
  UserUpdate,
} from "../../utils/interfaces";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";

import * as userApi from "../../utils/api/auth";

import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import "primeflex/primeflex.css";
import styles from "./App.module.css";

function App() {
  const [currentUser, setCurrentUser] = useState<
    User | Record<PropertyKey, never>
  >({});
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [loggedIn, setLoggedIn] = useState<boolean>(false);

  const [isInfoPopupOpen, setIsInfoPopupOpen] = useState<boolean>(false);
  const [isPopupEditUserOpen, setIsPopupEditUserOpen] =
    useState<boolean>(false);

  const [isPopupSuccess, setIsPopupSuccess] = useState<boolean>(false);

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
          navigate("/");
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [loggedIn]);

  const handleLogin = async (data: UserLogin): Promise<void> => {
    setIsLoading(true);
    try {
      const res = await userApi.authorize(data);
      localStorage.setItem("jwt", res.token);
      setLoggedIn(true);
      navigate("/");
    } catch (err) {
      console.log(err);
      setIsPopupSuccess(false);
      setIsInfoPopupOpen(true);
    } finally {
      setIsLoading(false);
    }
  };

  const handleRegister = async (data: UserRegister): Promise<void> => {
    setIsLoading(true);
    try {
      await userApi.register(data);
      handleLogin({ username: data.username, password: data.password });
      navigate("/sign-in");
    } catch (err) {
      console.log(err);
      setIsPopupSuccess(false);
      setIsInfoPopupOpen(true);
    } finally {
      setIsLoading(false);
    }
  };

  const handleUpdateUser = (userData: UserUpdate) => {
    setIsLoading(true);
    userApi
      .updUserData(userData)
      .then((result) => {
        console.log(result);
        // setCurrentUser({
        //   _id: result.data._id,
        //   name: result.data.name,
        //   email: result.data.email,
        //   role: result.data.role,
        // });

        setIsPopupEditUserOpen(false);
      })
      .catch(() => {
        setIsPopupEditUserOpen(false);
        setIsPopupSuccess(false);
        setIsInfoPopupOpen(true);
      })
      .finally(() => {
        setIsLoading(false);
        setIsPopupEditUserOpen(false);
      });
  };

  const signOut = (): void => {
    localStorage.removeItem("jwt");
    setLoggedIn(false);
    setCurrentUser({});
    navigate("/signin");
  };

  const closeAllPopups = (): void => {
    setIsInfoPopupOpen(false);
    setIsPopupEditUserOpen(false);
  };

  return (
    <CurrentUserContext.Provider value={user}>
      <div className={styles.page}>
        <div className={styles.wrapper}>
          <Header signOut={signOut} editUser={setIsPopupEditUserOpen} />

          <Routes>
            <Route path="/" element={<ProtectedRoute children={<Main />} />} />
            <Route
              path="/signup"
              element={<Register handleRegister={handleRegister} />}
            />
            <Route
              path="/signin"
              element={<Login handleLogin={handleLogin} />}
            />
            <Route path="*" element={<PageNotFound />} />
          </Routes>

          <InfoPopup
            isOpen={isInfoPopupOpen}
            onClose={closeAllPopups}
            isSuccess={isPopupSuccess}
          />

          <PopupEditUser
            isOpen={isPopupEditUserOpen}
            onClose={closeAllPopups}
            onSubmit={handleUpdateUser}
          />
        </div>
        <Loader isLoading={isLoading} />
      </div>
    </CurrentUserContext.Provider>
  );
}

export default App;
