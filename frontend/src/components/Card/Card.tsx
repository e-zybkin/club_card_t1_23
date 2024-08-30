import logo from "../../assets/cards/card-logo.png";
import name from "../../assets/cards/card-name.png";
import art from "../../assets/cards/card-art.png";

import { useContext, Dispatch, SetStateAction } from "react";

import { CurrentUserContext } from "../../contexts/CurrentUserContext";

import styles from "./Card.module.css";
import { CardData } from "../../utils/interfaces";
import { CardTemplates, CardColors } from "../../utils/enums";

interface props {
  card: CardData | null;
  openQr: Dispatch<SetStateAction<boolean>>;
}

function Card({ card, openQr }: props) {
  const userContext = useContext(CurrentUserContext);

  const currentUser = userContext?.currentUser;

  return (
    <div className={styles.cardBox}>
      <div
        className={styles.card}
        style={{
          backgroundColor: card
            ? CardColors[card.colour.toUpperCase() as keyof typeof CardColors]
            : undefined,
        }}
      >
        <img
          src={logo}
          alt="логотип Т1"
          className={`${
            card?.template === CardTemplates.FULL
              ? styles["logo-first-case"]
              : card?.template === CardTemplates.MIDDLE
              ? styles["logo-second-case"]
              : styles["logo-third-case"]
          } ${styles.logo}`}
        />
        <img src={name} alt="логотип Дебют" className={styles[`name`]} />
        <p
          className={styles.initials}
        >{`${currentUser?.firstName} ${currentUser?.lastName} `}</p>
        {card?.template === CardTemplates.FULL && (
          <p className={styles.number}>{card?.number}</p>
        )}
        {card?.template === CardTemplates.FULL ||
          (card?.template === CardTemplates.MIDDLE && (
            <p className={styles.date}>{card?.dateOfExpiration}</p>
          ))}
      </div>

      <div
        className={styles.card}
        style={{
          backgroundColor: card
            ? CardColors[card.colour.toUpperCase() as keyof typeof CardColors]
            : undefined,
        }}
      >
        <div className={styles.stripe}></div>
        <img
          onClick={() => openQr(true)}
          className={styles.code}
          src={`data:image/png;base64,${card?.qrCode}`}
          alt="Base64 Image"
        />
        <img src={art} className={styles.art} />
      </div>
    </div>
  );
}

export default Card;
