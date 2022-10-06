import { Link } from "react-router-dom";
import styles from "./Sidebar.module.css";
import logo from "../../Img/Asset_3.png";
import { useSelector, useDispatch } from "react-redux";
import React, { useEffect } from "react";
import { getProfile } from "../../api/services/user";
import { userAction } from "../../store/user";
import { persistor } from "../../index";
import { useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";

const Sidebar = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const logoutHanlder = () => {
    // deleteToken("token");
    localStorage.removeItem("token");
    console.log("로그아웃");
    navigate("/login");

    setTimeout(() => {}, 2000);
  };
  const purge = async () => {
    await persistor.purge();
  };

  useEffect(() => {
    if (localStorage.getItem("token") && isLogin === true) {
      getProfile()
        .then((res) => {
          // console.log("로그인 유지 성공!");
          dispatch(userAction.setUser(res));
        })
        .catch((err) => {
          console.log("로그인 유지 실패");
          logoutHanlder();
          dispatch(authActions.logout());
          setTimeout(() => purge(), 200);
        });
    }
  }, []);

  return (
    <div className={styles.sidebar_container}>
      <Link to="/dashboard">
        <img src={logo} className={styles.logo} />
      </Link>
      <Link to="/profile">
        <img src="" className={styles.profile} />
      </Link>
      <div className={styles.btnContainer}>
        <Link to="/dashboard">
          <div className={styles.sidebarBtn}>
            <button className={styles.componentsBtn}>대시보드</button>
          </div>
        </Link>
        <Link to="/create-Code">
          <button className={styles.componentsBtn}>QR 코드 관리</button>
        </Link>
        <Link to="/reservation-admin">
          <button className={styles.componentsBtn}>예약 관리</button>
        </Link>
        <Link to="/store-management">
          <button className={styles.componentsBtn}>매장 관리</button>
        </Link>
        <Link to="/">
          <button className={styles.componentsBtn}>리뷰 관리</button>
        </Link>
        {/* {isLogin && (
          <button
            className={styles.componentsBtn}
            onClick={async () => {
              await logoutHanlder();
              await setTimeout(() => purge(), 200);
            }}
          >
            LOGOUT
          </button>
        )} */}
      </div>
    </div>
  );
};

export default Sidebar;
