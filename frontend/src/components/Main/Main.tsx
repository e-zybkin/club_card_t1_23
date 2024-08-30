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
  blockCard: Dispatch<SetStateAction<boolean>>;
}

function Main({ card, createCard, openQr, blockCard }: props) {
  return (
    <main className={styles.main}>
      {card ? (
        <div className={styles.cardBox}>
          {card.isBlocked ? (
            <div className={styles.blockedMessage}>
              <h2 className={styles.title}>Ваша карта заблокирована</h2>
            </div>
          ) : (
            <>
              <Card card={card} openQr={openQr} />
              <Button
                className={`${styles.btnBlock} buttons`}
                label="Заблокировать карту"
                onClick={() => blockCard(true)}
              />
            </>
          )}
        </div>
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
