import { Dispatch, SetStateAction } from "react";

import Card from "../Card/Card";
import { Button } from "primereact/button";

import { CardData } from "../../utils/interfaces";

import main from "../../assets/images/main.png";

import styles from "./Main.module.css";

interface props {
  card: CardData | null;
  createCard: Dispatch<SetStateAction<boolean>>;
  openQr: Dispatch<SetStateAction<boolean>>;
}

function Main({ card, createCard, openQr }: props) {
  return (
    <main className={styles.main}>
      {card ? (
        <Card card={card} openQr={openQr}></Card>
      ) : (
        <div className={styles.create}>
          <img className={styles.picture} src={main} alt="Красивая картинка" />
          <h2 className={styles.title}>У вас ещё нет карточки</h2>
          <Button
            className={`${styles.submit} buttons`}
            label="Создать карту"
            onClick={() => createCard(true)}
          />
        </div>
      )}
    </main>
  );
}

export default Main;
