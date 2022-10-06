import styles from "./Header.module.css";
import { getProfile } from "../../../src/api/services/user";
import React from "react";
import { useEffect, useState } from "react";
import noneProfile from "../../Img/Asset_5.png";
import { Link, useNavigate } from "react-router-dom";
import { persistor } from "../../index";
import { baseURL } from "../../api/axios";
import { useSelector, useDispatch } from "react-redux";
import { authActions } from "../../store/auth";

const Header = (title) => {
  const [profileImg, setProfileImg] = useState(null);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    getProfile().then((profileInfo) => setProfileImg(profileInfo.profileImg));
  }, []);
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
            src={baseURL + profileImg}
            className={styles.imgPreview}
            alt="프로필 이미지"
          />
        )}
        {
          <button
            className={styles.componentsBtn}
            onClick={async () => {
              await logoutHanlder();
              dispatch(authActions.logout());
              await setTimeout(() => purge(), 200);
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
