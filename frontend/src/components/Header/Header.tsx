import { Dispatch, SetStateAction } from "react";
import { useContext, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";

import { CurrentUserContext } from "../../contexts/CurrentUserContext";
import { Menu } from "primereact/menu";
import { Menu as MenuType } from "primereact/menu";
import { Button } from "primereact/button";

import { UserRoles } from "../../utils/enums";

import logo from "../../assets/images/logo.svg";

import "primeicons/primeicons.css";
import styles from "./Header.module.css";

interface props {
  signOut: () => void;
  editUser: Dispatch<SetStateAction<boolean>>;
}

function Header({ signOut, editUser }: props) {
  const userContext = useContext(CurrentUserContext);
  const userMenu = useRef<MenuType>(null);

  const loggedIn = userContext?.loggedIn;
  const currentUser = userContext?.currentUser;

  const location = useLocation();
  const navigate = useNavigate();

  const adminPaths = {
    "/": () => [
      {
        label: "Пользователи",
        icon: "pi pi-users",
        command: () => navigate("/users"),
      },
    ],
    "/users": () => [
      {
        label: "Главная",
        icon: "pi pi-folder",
        command: () => navigate("/"),
      },
    ],
  };

  const commonItems = [
    {
      label: "Редактировать",
      icon: "pi pi-user-edit",
      command: () => editUser(true),
    },
    { label: "Выйти", icon: "pi pi-sign-out", command: () => signOut() },
  ];

  const createMenuItems = (
    loc: string,
    paths?: Record<
      string,
      () => { label: string; icon: string; command: () => void }[]
    >
  ) => {
    const items = [...(paths?.[loc] ? paths[loc]() : []), ...commonItems];
    return [
      {
        label: paths && "/users" in paths ? "Администратор" : "Пользователь",
        items,
      },
    ];
  };

  const adminItems = createMenuItems(location.pathname, adminPaths);
  const userItems = createMenuItems(location.pathname);

  return (
    <header className={styles.header}>
      <img src={logo} alt="логотип" className={styles.logo} />

      {loggedIn && (
        <div>
          <Menu
            model={
              currentUser?.role === UserRoles.member ||
              currentUser?.role === UserRoles.unknown
                ? userItems
                : adminItems
            }
            popup
            ref={userMenu}
            id="user_menu"
          />
          <Button
            type="button"
            label={`${currentUser?.firstName} ${currentUser?.lastName}`}
            icon="pi pi-user"
            onClick={(event) => userMenu.current?.toggle(event)}
            aria-controls="user_menu"
            aria-haspopup
            text
            className={styles.button}
          />
        </div>
      )}
    </header>
  );
}

export default Header;
