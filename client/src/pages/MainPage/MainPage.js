import { useSelector } from "react-redux";
import { Link, Router } from "react-router-dom";
import React from "react";
import styles from "./MainPage.module.css";
import logoGif from "../../Img/anigiflogo.gif";
import logo from "../../Img/Asset_7.png";

const MainPage = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  return (
    <div className={styles.main__container}>
      <img className={styles.main__img} alt="gifImg" src={logoGif}></img>
      <div className={styles.main__txtsWrapper}>
        <div className={styles.main__txtWrapper}>
          <div className={styles.main__txt}>우리 매장의 예약과 대기를</div>
          <div className={styles.main__txt}><span>QR 코드</span>로 관리하고</div>
          <div className={styles.main__txt}>기간 별 예약 현황을 한눈에!</div>
        </div>
        <div className={styles.main__subTxt}>QR코드 찍고 예약대기와 메뉴 선택까지 한번에!</div>
        {/* {!isLogin && ( */}
          <Link to="/login">
            <button className={styles.main__btn}>Quick Book 사용하기</button>
          </Link>
        {/* )} */}
      </div>
      <img className={styles.main__imgLogo} alt="logo" src={logo}></img>
    </div>
  );
};
export default MainPage;
