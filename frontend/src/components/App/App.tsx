import { Route, Routes } from "react-router-dom";
import Main from "../Main/Main";

import styles from "./App.module.css";

function App() {
  return (
    <div className={styles.page}>
      <div className={styles.wrapper}>
        <Routes>
          <Route path="/" element={<Main />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
