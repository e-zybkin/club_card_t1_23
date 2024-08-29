import { Dialog } from "primereact/dialog";
import { FormEditUser } from "../Forms/FormEditUser";
import { UserUpdate } from "../../utils/interfaces";

interface props {
  visible: boolean;
  onClose: () => void;
  onSubmit: (userData: UserUpdate) => void;
}

export function PopupEditUser({ visible, onClose, onSubmit }: props) {
  return (
    <Dialog
      header="Редактировать данные"
      visible={visible}
      onHide={() => onClose()}
      draggable={false}
    >
      <FormEditUser onUpdateUser={onSubmit} />
    </Dialog>
  );
}
export default PopupEditUser;
