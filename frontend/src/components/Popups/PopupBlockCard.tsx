import { Dialog } from "primereact/dialog";
import { Button } from "primereact/button";

interface Props {
  visible: boolean;
  onClose: () => void;
  onBlockCard: () => void;
}

export function PopupBlockCard({ visible, onClose, onBlockCard }: Props) {
  return (
    <Dialog
      header="Блокировка карты"
      visible={visible}
      onHide={onClose}
      blockScroll
    >
      <p>Вы уверены, что хотите заблокировать карту?</p>
      <Button type="button" label="Заблокировать" onClick={onBlockCard} />
    </Dialog>
  );
}
