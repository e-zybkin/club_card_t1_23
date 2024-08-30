import { useContext, useEffect } from "react";
import styles from "./UsersList.module.css";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";

import { Accordion, AccordionTab } from "primereact/accordion";
import { Button } from "primereact/button";

import { User } from "../../utils/interfaces";

import { FormChangeRole } from "../Forms/FormChangeRole";
import { FormChangePrivelege } from "../Forms/FormChangePrivelege";
import { UserRoles, UserPrivileges } from "../../utils/enums";

interface props {
  users: User[];
  getAllUsers: () => void;
  onChangeRole: (id: number, newRole: UserRoles) => void;
  onChangePrivilege: (id: number, newPrivilege: UserPrivileges) => void;
  onDeleteUser: (user: User) => void;
}

export function UsersList({
  users,
  getAllUsers,
  onChangeRole,
  onChangePrivilege,
  onDeleteUser,
}: props) {
  const userContext = useContext(CurrentUserContext);
  const currentUser = userContext?.currentUser;

  const handleDeleteUserClick = (user: User) => onDeleteUser(user);

  useEffect(() => {
    getAllUsers();
  }, [getAllUsers]);

  return (
    <div>
      {users.length === 0 && (
        <h3 className={styles.title}>
          Пока что нет зарегистрированных пользователей.
        </h3>
      )}

      <Accordion multiple>
        {users.map((user) => {
          return (
            <AccordionTab
              key={user.id}
              header={
                <span className="flex align-items-center gap-2 w-full">
                  <span className="font-bold white-space-nowrap">
                    {`${user.firstName} ${user.lastName} ${user.middleName}`}
                  </span>
                </span>
              }
            >
              <div className={styles.user}>
                <p>{user.email}</p>
                <div className={styles.btnBlock}>
                  {currentUser?.role === UserRoles.owner && (
                    <div>
                      <FormChangeRole user={user} onChangeRole={onChangeRole} />
                      <FormChangePrivelege
                        user={user}
                        onChangePrivilege={onChangePrivilege}
                      />
                    </div>
                  )}

                  {currentUser?.role === UserRoles.manager && (
                    <div>
                      <FormChangePrivelege
                        user={user}
                        onChangePrivilege={onChangePrivilege}
                      />
                    </div>
                  )}

                  <Button
                    rounded
                    icon="pi pi-user-minus"
                    size="small"
                    severity="danger"
                    onClick={() => handleDeleteUserClick(user)}
                  />
                </div>
              </div>
            </AccordionTab>
          );
        })}
      </Accordion>
    </div>
  );
}
