import Sidebar from "../../components/Sidebar/Sidebar";
import ProfileEditModal from "../../components/profileEditModal/ProfileEditModal";
import React, { useEffect, useState } from 'react';
import styles from "./Profile.module.css";
import noneProfile from "../../Img/Asset_5.png";
import { useDispatch, useSelector } from "react-redux";
import { getProfile } from "../../../src/api/services/user";
// import { profileImgActions } from "../../store/profileImg";
// import user from "../../store/user";


const Profile = () => {
  const profile = useSelector(state => state.user.userProfile)
  const [userInfo, setUserInfo] = useState({})
  const [service, setService] = useState("");
  const [email, setEmail] = useState("");
  const [isModal, setIsModal] = useState(true);

  const dispatch = useDispatch();

  console.log(profile)
  // const showModal = () => {
  //   console.log(isModal)
  // setIsModal(true);
  // }

  useEffect(() => {
    getProfile()
      .then(userData => {
        setUserInfo(userData);
        setEmail(userData.email);
        setService(userData.service[0])
      })
  }, [])
  // dispatch(profileImgActions.ImgSubmit(userInfo.profileImg))

  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.main_container}>
        {/* {isModal ? */}
        <div className={styles.profile_container}>
          <h1 className={styles.title}>프로필</h1>
          <div className={styles.contents_container}>
            <div className={styles.imgWrapper}>
              {userInfo.profileImg === null || userInfo.profileImg === undefined ?
                <img alt="나는 없는 이미지" src={noneProfile} /> :
                <img src={"http://localhost:8080" + userInfo.profileImg} className={styles.imgPreview} alt="나는 프로필" />}
            </div>
            <div className={styles.contents_info}>
              <div>
                <div className={styles.contents_text}>관리자 명: {userInfo.name}</div>
                <div className={styles.contents_text}>이메일: {userInfo.email}</div>
                <div className={styles.contents_text}>전화번호: {userInfo.phone}</div>
              </div>
              <div>
                <button className={styles.btn}>수 정</button>
              </div>
            </div>
          </div>
        </div>
        {/* } */}
        <ProfileEditModal />
      </div>
    </div>
  );
};
export default Profile;