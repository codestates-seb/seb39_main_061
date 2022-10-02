import styles from "./Header.module.css";
import { getProfile } from "../../../src/api/services/user";
import { useEffect, useState } from "react";
import noneProfile from "../../Img/Asset_5.png";
import { Link } from "react-router-dom";

const Header = (title) => {
  const [profileImg, setProfileImg] = useState(null);
  // const navigate = useNavigate()

  useEffect(() => {
    getProfile()
      .then(profileInfo =>
        setProfileImg(profileInfo.profileImg))
  }, [])

  return (
    <div className={styles.header_container}>
      <h2 className={styles.h2}>{title.title}</h2>
      <Link to="/profile">
        {profileImg === null || profileImg === undefined ?
          <img alt="나는 없는 이미지" src={noneProfile} className={styles.imgPreview} />
          :
          <img src={"http://localhost:8080" + profileImg} className={styles.imgPreview} alt="프로필 이미지" />
        }
      </Link>
    </div>
  );
};

export default Header;