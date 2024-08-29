import { Link } from "react-router-dom";
import styles from "./AuthPage.module.css";
import React from "react";

interface props {
  title: string;
  name: string;
  reg: boolean;
  submitText: string;
  children: React.ReactNode;
}

function AuthPage(props: props) {
  return (
    <section className={styles["auth-page"]}>
      <h3 className={styles.title}>{props.title}</h3>
      {props.children}
      {props.reg ? (
        <div className={styles.teleport}>
          <p className={styles.hint}>Уже зарегистрированы?</p>
          <Link to="/signin" className={styles.link}>
            Войти
          </Link>
        </div>
      ) : (
        <div className={styles.teleport}>
          <p className={styles.hint}>Ещё нет аккаунта?</p>
          <Link to="/signup" className={styles.link}>
            Зарегистрироваться
          </Link>
        </div>
      )}
    </section>
  );
}

export default AuthPage;
