import Sidebar from "../../components/Sidebar/Sidebar";
import ProfileEditModal from "../../components/profileEditModal/ProfileEditModal";
import axios from "axios";
import React, { useEffect, useState } from 'react';

const Profile = () => {
  const [userInfo, setUserInfo] = useState({})
  const [sector, setSector] = useState("");
  const [service, setService] = useState("");
  const [isModal, setIsModal] = useState(false);

  const showModal = () => {
    console.log(isModal)
    setIsModal(true);
  }

  const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5aXRza3lAbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfR1VFU1QiLCJpYXQiOjE2NjMyOTUxMjQsImV4cCI6MTY2MzI5ODcyNH0.K7r0skN_Ir0iMrgiy5_tuGNkyJKBibPbeSH4FHz2O10Amp82wNJb7tokIN1s-sM98Rnx8wdF_iUAf6ONDQD8uQ"

  useEffect(() => {
    axios.get("http://localhost:8080/api/v1/members/profile",
      {
        headers: { Authorization: "Bearer " + token }
      }
    )
      .then(userData => {
        console.log(userData.data.data)
        setUserInfo(userData.data.data);
        setSector(userData.data.data.sector.name)
        setService(userData.data.data.service[0])
      })
  }, [])

  return (
    <div>
      <Sidebar />
      {isModal && <ProfileEditModal setIsModal={setIsModal} />}
      <h1>프로필</h1>
      <div>
        <img src={"http://localhost:8080" + userInfo.profileImg} />
        <button onClick={showModal}>Edit</button>
        <div>
          <div>상호명: {userInfo.businessName}</div>
          <div>업종: {sector}</div>
          <div>관리자 명: {userInfo.name}</div>
          <div>전화번호: {userInfo.phone}</div>
        </div>
        <div>
          <div>이용중인 서비스 목록</div>
          <div>- {service}</div>

        </div>
      </div>
    </div>
  );
};
export default Profile;
