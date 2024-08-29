import { useContext, useEffect } from "react";
import styles from "./UsersList.module.css";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";

import { Accordion, AccordionTab } from "primereact/accordion";
import { Button } from "primereact/button";

import { User } from "../../utils/interfaces";

import { FormChangeRole } from "../Forms/FormChangeRole";

interface props {
  users: User[];
  getAllUsers: () => void;
  onChangeRole: () => void;
  onChangePrivelege: () => void;
  onDeleteUser: () => void;
}

export function UsersList({
  users,
  getAllUsers,
  onChangeRole,
  onChangePrivelege,
  onDeleteUser,
}: props) {
  const userContext = useContext(CurrentUserContext);
  const currentUser = userContext?.currentUser;

  const handleDeleteUserClick = (user) => onDeleteUser(user);

  useEffect(() => {
    getAllUsers();
  }, [getAllUsers]);

  return (
    <div>
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
                  {currentUser.role === "super" && (
                    <FormChangeRole user={user} onChangeRole={onChangeRole} />
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
