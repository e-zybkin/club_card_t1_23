import styles from "./Loader.module.css";

interface props {
  isLoading: boolean;
}

function Loader({ isLoading }: props) {
  if (isLoading) {
    return (
      <div className={styles.loader}>
        <div className={styles.container}>
          <span className={styles.round} />
        </div>
      </div>
    );
  }
}

export default Loader;
