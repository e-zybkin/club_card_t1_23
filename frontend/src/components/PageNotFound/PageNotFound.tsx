import { useNavigate } from "react-router-dom";
import styles from "./PageNotFound.module.css";

function PageNotFound() {
  const navigate = useNavigate();

  return (
    <main className={styles.notFound}>
      <div>
        <h1 className={styles.title}>404</h1>
        <p className={styles.caption}>Страница не найдена</p>
      </div>
      <button
        className={`${styles.button} buttons`}
        onClick={() => navigate(-1)}
      >
        Назад
      </button>
    </main>
  );
}

export default PageNotFound;
