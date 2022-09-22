import Sidebar from "../../components/Sidebar/Sidebar";
import ProfileEditModal from "../../components/profileEditModal/ProfileEditModal";
import axios from "axios";
import React, { useEffect, useState } from 'react';

const Profile = () => {
  const [userInfo, setUserInfo] = useState({})
  const [service, setService] = useState("");
  const [isModal, setIsModal] = useState(false);

  const showModal = () => {
    console.log(isModal)
    setIsModal(true);
  }

  const token = ""

  useEffect(() => {
    axios.get("http://localhost:8080/api/v1/members/profile",
      {
        headers: { Authorization: "Bearer " + token }
      }
    )
      .then(userData => {
        console.log(userData.data.data)
        setUserInfo(userData.data.data);
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
