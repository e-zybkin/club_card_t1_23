import { Dialog } from "primereact/dialog";
import styles from "./Popups.module.css";
import { CardData } from "../../utils/interfaces";

interface props {
  visible: boolean;
  onClose: () => void;
  card: CardData | null;
}

export function PopupQR({ visible, onClose, card }: props) {
  return (
    <Dialog
      visible={visible}
      onHide={onClose}
      header="Ваш QR-код"
      breakpoints={{ "960px": "80vw" }}
      style={{ width: "30vw" }}
      draggable={false}
    >
      <img
        className={styles.code}
        src={`data:image/png;base64,${card?.qrCode}`}
        alt="Base64 Image"
      />
    </Dialog>
  );
}
