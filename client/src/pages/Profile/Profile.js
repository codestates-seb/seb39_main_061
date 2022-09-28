import Sidebar from "../../components/Sidebar/Sidebar";
import ProfileEditModal from "../../components/profileEditModal/ProfileEditModal";
import axiosInstance from "../../api/axios";
import React, { useEffect, useState } from 'react';
import styles from "./Profile.module.css";
import noneProfile from "../../Img/Asset_5.png";
import { useDispatch } from "react-redux";
import { profileImgActions } from "../../store/profileImg";
import user from "../../store/user";

const Profile = () => {
  const [userInfo, setUserInfo] = useState({})
  const [service, setService] = useState("");
  const [email, setEmail] = useState("");
  const [isModal, setIsModal] = useState(false);

  const dispatch = useDispatch();

  const showModal = () => {
    console.log(isModal)
    setIsModal(true);
  }

  useEffect(() => {
    axiosInstance({
      url: "/api/v1/members/profile",
      method: "GET"
    })
      .then(userData => {
        console.log(userData.data.data)
        console.log(userData.data.data.profileImg)
        setUserInfo(userData.data.data);
        setEmail(userData.data.data.email);
        setService(userData.data.data.service[0])
      })
  }, [])
  dispatch(profileImgActions.ImgSubmit(userInfo.profileImg))

  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.main_container}>
        {isModal ?
          <ProfileEditModal setIsModal={setIsModal} /> :
          <div className={styles.profile_container}>
            <h1 className={styles.title}>프로필</h1>
            <div className={styles.contents_container}>
              <div>
                {userInfo.profileImg === null || userInfo.profileImg === undefined ?
                  <img src={noneProfile} /> :
                  <img src={"http://localhost:8080" + userInfo.profileImg} />}
              </div>
              <div className={styles.contents_info}>
                <div>
                  <div className={styles.contents_text}>관리자 명: {userInfo.name}</div>
                  <div className={styles.contents_text}>email: {email}</div>
                  <div className={styles.contents_text}>전화번호: {userInfo.phone}</div>
                  <div className={styles.contents_text}>이용중인 서비스 목록</div>
                  <div className={styles.contents_text}>- {service}</div>
                </div>
                <div>
                  <button onClick={showModal} className={styles.btn}>수 정</button>
                </div>
              </div>
            </div>
          </div>
        }
      </div>
    </div>
  );
};
export default Profile;