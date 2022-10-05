import { Link } from "react-router-dom";
import styles from "./Sidebar.module.css";
import logo from "../../Img/Asset_3.png";
import { useSelector, useDispatch } from "react-redux";
import { authActions } from "../../store/auth";
import { useNavigate } from "react-router-dom";
import { persistor } from "../../index";
import { useEffect } from "react";
import { getProfile } from "../../api/services/user";
import { userAction } from "../../store/user";
import { useCookies } from "react-cookie";

const Sidebar = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [cookies, setCookie, removeCookie] = useCookies();

  useEffect(() => {
    if (localStorage.getItem("token") && isLogin === true) {
      getProfile()
        .then((res) => {
          console.log("로그인 유지 성공!");
          dispatch(userAction.setUser(res));
        })
        .catch((err) => {
          console.log("로그인 유지 실패");
        });
    }
  }, []);

  const logoutHanlder = () => {
    // deleteToken("token");
    localStorage.removeItem("token");
    dispatch(authActions.logout());
    removeCookie("refresh");
    console.log("로그아웃");
    navigate("/login");
  };
  const purge = async () => {
    await persistor.purge();
  };

  return (
    <div className={styles.sidebar_container}>
      <img src={logo} className={styles.logo} />
      <Link to="/profile">
        <img src="" className={styles.profile} />
      </Link>
      <Link to="/store-management">
        <button>StoreManagement</button>
      </Link>
      <div className={styles.btnContainer}>
        <Link to="/dashboard">
          <div className={styles.sidebarBtn}>
            <button className={styles.componentsBtn}>대시보드</button>
          </div>
        </Link>
        <Link to="/create-Code">
          <button className={styles.componentsBtn}>QR 코드 만들기</button>
        </Link>
        <Link to="/management">
          <button className={styles.componentsBtn}>QR 코드 관리</button>
        </Link>
        <Link to="/reservation-admin">
          <button className={styles.componentsBtn}>예약 관리</button>
        </Link>
        <Link to="/">
          <button className={styles.componentsBtn}>메뉴 관리</button>
        </Link>
        <Link to="/review-admin">
          <button className={styles.componentsBtn}>리뷰 관리</button>
        </Link>
        {isLogin && (
          <button
            className={styles.componentsBtn}
            onClick={async () => {
              await logoutHanlder();
              await setTimeout(() => purge(), 200);
            }}
          >
            로그아웃
          </button>
        )}
      </div>
    </div>
  );
};

export default Sidebar;
