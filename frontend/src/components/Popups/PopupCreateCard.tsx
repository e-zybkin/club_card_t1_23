import { Dialog } from "primereact/dialog";

import FormCreateCard from "../Forms/FormCreateCard";

import { CardColors, CardTemplates } from "../../utils/enums";

interface props {
  visible: boolean;
  onClose: () => void;
  onSubmit: (
    colorName: keyof typeof CardColors,
    templateName: keyof typeof CardTemplates
  ) => void;
}

function PopupCreateCard({ visible, onClose, onSubmit }: props) {
  return (
    <Dialog
      visible={visible}
      onHide={onClose}
      header="Создание карточки"
      draggable={false}
    >
      <FormCreateCard onSubmit={onSubmit} />
    </Dialog>
  );
}

export default PopupCreateCard;
