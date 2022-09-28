import Sidebar from "../../components/Sidebar/Sidebar";
import ProfileEditModal from "../../components/profileEditModal/ProfileEditModal";
import axiosInstance from "../../api/axios";
import React, { useEffect, useState } from 'react';
import styles from "./Profile.module.css";
import noneProfile from "../../Img/Asset_5.png";
import { useDispatch } from "react-redux";
import { profileImgActions } from "../../store/profileImg";

const Profile = () => {
  const [userInfo, setUserInfo] = useState({})
  const [service, setService] = useState("");
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
        setService(userData.data.data.service[0])
      })
  }, [])
  dispatch(profileImgActions.ImgSubmit(userInfo.profileImg))

  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.main_container}>
        {isModal ? <ProfileEditModal setIsModal={setIsModal} /> :
          <div>
            <h1 className={styles.title}>프로필</h1>
            <div>
              {/* <img src={noneProfile} /> */}
              {/* 데이터에 프로필 이미지 있는지 없는지 */}
              {userInfo.profileImg === null ?
                <img src={noneProfile} /> :
                <img src={"http://localhost:8080" + userInfo.profileImg} />}
              <button onClick={showModal}>Edit</button>
              <div>
                {/* <div>상호명: {userInfo.businessName}</div> */}
                <div>관리자 명: {userInfo.name}</div>
                <div>전화번호: {userInfo.phone}</div>
              </div>
              <div>
                <div>이용중인 서비스 목록</div>
                <div>- {service}</div>

              </div>
            </div>
          </div>
        }
      </div>
    </div>
  );
};
export default Profile;