import { Dispatch, SetStateAction } from "react";

import Card from "../Card/Card";
import { Button } from "primereact/button";

import styles from "./Main.module.css";
import { CardData } from "../../utils/interfaces";
// import { CardColors, CardTemplates } from "../../utils/enums";

interface props {
  card: CardData | null;
  createCard: Dispatch<SetStateAction<boolean>>;
}

function Main({ card, createCard }: props) {
  return (
    <main className={styles.main}>
      {/* <Card
        card={{
          id: 1,
          number: "0000 1111 2222 3333",
          openingDate: "22/24",
          dateOfExpiration: "23/24",
          cvcCode: 565,
          bonuses: 4,
          isBlocked: false,
          color: CardColors.BLUE,
          template: CardTemplates.MIDDLE,
        }}
      ></Card> */}

      {card ? (
        <Card card={card}></Card>
      ) : (
        <div>
          <h2>У вас ещё нет карточки</h2>
          <Button label="Создать карту" onClick={() => createCard(true)} />
        </div>
      )}
    </main>
  );
}

export default Main;
