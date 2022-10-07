import React from 'react';
import Sidebar from "../../components/Sidebar/Sidebar";
import CreateQr from "../../components/CreateQr/CreateQr";
import styles from "./CreateCode.module.css";
import Header from "../../components/Header/Header";

const CreateCode = () => {
  const title = "QR 코드 관리";

  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.qr__container}>
        <Header title={title} />
        <CreateQr />
      </div>
    </div>
  );
};
export default CreateCode;
