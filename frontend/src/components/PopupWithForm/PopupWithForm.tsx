import React from "react";
import Popup from "../Popup/Popup";
import styles from "./PopupWithForm.module.css";

interface props {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  children: React.ReactNode;
  submitText: string;
}

function PopupWithForm({
  isOpen,
  onClose,
  title,
  onSubmit,
  children,
  submitText,
}: props) {
  return (
    <Popup isOpen={isOpen} onClose={onClose}>
      <h3 className={styles.title}>{title}</h3>
      <form className={styles.form} onSubmit={onSubmit}>
        {children}
        <button type="submit" className={`${styles.button} buttons`}>
          {submitText}
        </button>
      </form>
    </Popup>
  );
}

export default PopupWithForm;
