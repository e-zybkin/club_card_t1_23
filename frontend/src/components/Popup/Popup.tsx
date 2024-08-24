import React, { useEffect } from "react";
import styles from "./Popup.module.css";

interface props {
  isOpen: boolean;
  onClose: () => void;
  children: React.ReactNode;
}

const Popup = ({ isOpen, onClose, children }: props) => {
  useEffect(() => {
    if (!isOpen) return;

    const closeByEscape = (e: KeyboardEvent) => {
      if (e.key === "Escape") {
        onClose();
      }
    };

    document.addEventListener("keydown", closeByEscape);
    return () => document.removeEventListener("keydown", closeByEscape);
  }, [isOpen, onClose]);

  const handleOverlay = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div
      className={`${styles.popup} ${isOpen ? styles["popup-opened"] : ""}`}
      onClick={handleOverlay}
    >
      <div className={styles.content}>
        <button className={styles.close} type="button" onClick={onClose} />
        {children}
      </div>
    </div>
  );
};

export default Popup;
