import { Dialog } from "primereact/dialog";
import { Button } from "primereact/button";

interface props {
  visible: boolean;
  onClose: () => void;
  isDialogError: boolean;
  dialogMessage: string;
}

export function PopupDialog({
  visible,
  onClose,
  isDialogError,
  dialogMessage,
}: props) {
  const dialogFooter = (
    <div className="flex justify-content-center">
      <Button
        label="OK"
        className="p-button-text"
        autoFocus
        onClick={onClose}
      />
    </div>
  );

  return (
    <Dialog
      visible={visible}
      onHide={onClose}
      footer={isDialogError ? dialogFooter : ""}
      showHeader={false}
      breakpoints={{ "960px": "80vw" }}
      style={{ width: "30vw" }}
    >
      <div className="flex justify-content-center flex-column pt-6 px-3">
        <i
          className={`pi pi-${isDialogError ? "times" : "check"}-circle`}
          style={{
            fontSize: "5rem",
            color: `var(--${isDialogError ? "red" : "green"}-500)`,
          }}
        />
        <h4>{isDialogError ? "Произошла ошибка!" : "Успех!"}</h4>
        <p style={{ lineHeight: 1.5 }}>{dialogMessage}</p>
      </div>
    </Dialog>
  );
}
