import styles from "./Header.module.css";
import { getProfile } from "../../../src/api/services/user";
import React from "react";
import { useEffect, useState } from "react";
import noneProfile from "../../Img/Asset_5.png";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { authActions } from "../../store/auth";

const Header = (title) => {
  const [profileImg, setProfileImg] = useState(null);
  const isLogin = useSelector((state) => state.auth.isLogin);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    getProfile().then((profileInfo) => setProfileImg(profileInfo.profileImg));
  }, []);
  const logoutHanlder = () => {
    // deleteToken("token");
    localStorage.removeItem("token");
    dispatch(authActions.logout());
    console.log("로그아웃");
    navigate("/login");
  };

  return (
    <div className={styles.header_container}>
      <h2 className={styles.h2}>{title.title}</h2>
      <Link to="/profile">
        {profileImg === null || profileImg === undefined ? (
          <img
            alt="나는 없는 이미지"
            src={noneProfile}
            className={styles.imgPreview}
          />
        ) : (
          <img
            src={"http://localhost:8080" + profileImg}
            className={styles.imgPreview}
            alt="프로필 이미지"
          />
        )}
        {
          <button
            className={styles.componentsBtn}
            onClick={async () => {
              await logoutHanlder();
            }}
          >
            LOG OUT
          </button>
        }
      </Link>
    </div>
  );
};

export default Header;
