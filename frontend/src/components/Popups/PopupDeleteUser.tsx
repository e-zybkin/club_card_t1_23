import { Dialog } from "primereact/dialog";
import { Button } from "primereact/button";
import { User } from "../../utils/interfaces";

interface Props {
  user: User | null;
  visible: boolean;
  onClose: () => void;
  onDeleteUser: (userId: number) => void;
}

export function PopupDeleteUser({
  user,
  visible,
  onClose,
  onDeleteUser,
}: Props) {
  return (
    <Dialog
      header="Удаление пользователя"
      visible={visible}
      onHide={onClose}
      blockScroll
    >
      <p>
        Удалить пользователя {user?.firstName} {user?.lastName}?
      </p>
      <Button
        type="button"
        label="Удалить"
        onClick={() => {
          if (user && user.id !== undefined) {
            onDeleteUser(user.id);
          }
        }}
      />
    </Dialog>
  );
}
