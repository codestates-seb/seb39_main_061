import styles from "./Header.module.css";
import { getProfile } from "../../../src/api/services/user";
import { useEffect, useState } from "react";

const Header = () => {
  const [profileImg, setProfileImg] = useState(null);

  useEffect(() => {
    getProfile()
      .then(profileInfo =>
        setProfileImg(profileInfo.profileImg))
  }, [])
  
  return (
    <div className={styles.header_container}>
      <h2>페이지 명</h2>
      <img alt="프로필 이미지" src={profileImg} />
    </div>
  );
};

export default Header;