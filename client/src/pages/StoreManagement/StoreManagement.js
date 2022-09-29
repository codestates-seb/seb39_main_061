import React from "react";
import SideBar from "../../components/Sidebar/Sidebar.js";
import StoreInfo from "../../components/StoreInfo/StoreInfo.js";
import Styles from "./StoreManagement.module.css";

const StoreManagement = () => {
  // 매장정보를 가져와서 뿌려주는

  return (
    <main>
      <SideBar />
      <h1>매장관리</h1>
      <StoreInfo />
    </main>
  );
};

export default StoreManagement;
