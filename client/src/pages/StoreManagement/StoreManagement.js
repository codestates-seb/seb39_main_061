import MenuInfo from "../../components/MenuInfo/MenuInfo.js";
import React from "react";
import SideBar from "../../components/Sidebar/Sidebar.js";
import StoreInfo from "../../components/StoreInfo/StoreInfo.js";
import styles from "./StoreManagement.module.css";
import Header from "../../components/Header/Header";

const StoreManagement = () => {
  // 매장정보를 가져와서 뿌려주는

  return (
    <main className={styles.container}>
      <SideBar />

      <div className={styles.StoreManagement}>
        <Header title={"매장 관리"} />
        <StoreInfo />
        <MenuInfo />

        {/* {businessId && (
         
        )} */}
      </div>
    </main>
  );
};

export default StoreManagement;
