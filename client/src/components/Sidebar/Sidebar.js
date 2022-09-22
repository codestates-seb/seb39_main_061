import { Link } from "react-router-dom";
import styles from "./Sidebar.module.css";
import logo from "../../Img/Asset_3.png";

const Sidebar = () => {

  return (
    <div className={styles.sidebar_container}>
      <Link to="/dashboard">
        <img src={logo} alt="React" className={styles.logo}></img>
      </Link>
      <Link to="/profile">
        <img src="" className={styles.profile}></img>
      </Link>
      <div className={styles.btnContainer}>
        <Link to="/">
          <button className={styles.componentsBtn}>대시보드</button>
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
    </div>
  );
};

export default Sidebar;
