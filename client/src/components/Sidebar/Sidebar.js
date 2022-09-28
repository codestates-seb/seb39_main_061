import { Link } from "react-router-dom";
import styles from "./Sidebar.module.css";
import logo from "../../Img/Asset_3.png";

const Sidebar = () => {
  // const isLogin = useSelector((state) => state.auth.isAuthenticated);
  // const dispatch = useDispatch();
  // const navigate = useNavigate();
  // const logoutHanlder = () => {
  //   // deleteToken("token");
  //   localStorage.removeItem("token");
  //   dispatch(authActions.logout());
  //   console.log("로그아웃");
  //   navigate("/");
  // };
  // const purge = async () => {
  //   await persistor.purge();
  // };

  //   return (
  //     <div className={styles.sidebar_container}>
  //       <Link to="/dashboard">
  //         <img src={logo} alt="React" className={styles.logo}></img>
  // import { useSelector, useDispatch } from "react-redux";
  // import { authActions } from "../../store/auth";
  // import { useNavigate } from "react-router-dom";
  // import { persistor } from "../../index";

  return (
    <div className={styles.sidebar_container}>
      <img src={logo} className={styles.logo} />
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
          <button className={styles.componentsBtn}>QR 코드 만들기</button>
        </Link>
        <Link to="/management">
          <button className={styles.componentsBtn}>QR 코드 관리</button>
        </Link>
        <Link to="/">
          <button className={styles.componentsBtn}>예약 관리</button>
        </Link>
        <Link to="/">
          <button className={styles.componentsBtn}>메뉴 관리</button>
        </Link>
        <Link to="/">
          <button className={styles.componentsBtn}>리뷰 관리</button>
        </Link>
      </div>
      {/* {isLogin && (
        <button
          onClick={async () => {
            await logoutHanlder();
            await setTimeout(() => purge(), 200);
          }}
        >
          로그아웃
        </button>
      )} */}
    </div>
  );
};

export default Sidebar;
