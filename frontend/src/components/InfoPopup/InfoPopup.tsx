import tick from "../../assets/images/tick.svg";
import cross from "../../assets/images/cross.svg";
import Popup from "../Popup/Popup";
import styles from "./InfoPopup.module.css";

interface props {
  isOpen: boolean;
  onClose: () => void;
  isSuccess: boolean;
}

function InfoPopup({ isOpen, onClose, isSuccess }: props) {
  return (
    <Popup isOpen={isOpen} onClose={onClose}>
      <img
        className={styles.picture}
        src={isSuccess ? tick : cross}
        alt="Галочка"
      />
      <h3 className={styles.title}>
        {isSuccess ? "Успех!" : "Что-то пошло не так! Попробуйте ещё раз."}
      </h3>
    </Popup>
  );
}

export default InfoPopup;
