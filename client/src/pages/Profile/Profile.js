import Sidebar from "../../components/Sidebar/Sidebar";
import ProfileEditModal from "../../components/profileEditModal/ProfileEditModal";
import axios from "axios";
import { Link } from "react-router-dom";
import React, { useEffect, useState } from 'react';

const Profile = () => {
  const [userInfo, setUserInfo] = useState({
    profileImg: "",
    companyName: "",
    sectorName: "",
    phoneNum: 0,
    userName: "",
    email: "",
    password: null,
  })
  const [isModal, setIsModal] = useState(false);

  // const [profileImg, setProfileImg] = useState("");
  // const [companyName, setCompanyName] = useState("");
  // const [sectorName, setSectorName] = useState("");
  // const [phoneNum, setPhoneNum] = useState();
  // const [userName, setUserName] = useState("");
  // const [email, setEmail] = useState("");

  const showModal =() => {
    console.log(isModal)
    setIsModal(true);
  }

/*
  useEffect(() => {
    axios.get("/api/v1/members/profile",
      {
        headers: { Authorization: "Bearer " + JSON.parse(window.localStorage.getItem("access_token")).access_token }
      }
    )
      .then(userData => {
        setUserInfo(userData.data);
      })
  }, [])
  */


  return (
    <div>
      <Sidebar />
      {isModal && <ProfileEditModal setIsModal={setIsModal} />}
      <h1>프로필</h1>
      <div>
        <img src="https://avatars.githubusercontent.com/u/82711000?v=4.jpg" />
          <button onClick={showModal}>Edit</button>
        <div>
          <div>상호명: 돈까스집{userInfo.companyName}</div>
          <div>업종: 비어있음{userInfo.sectorName}</div>
          <div>전화번호: 비어있음{userInfo.phoneNum}</div>
          <div>관리자 명: 유인태{userInfo.userName}</div>
          <div>Email: asdf@naver.com{userInfo.email}</div>
        </div>
        <div>
          <div>이용중인 서비스 목록</div>
          <div>예약/대기 서비스</div>
          <div>관리 서비스</div>
        </div>
      </div>
    </div>
  );
};
export default Profile;
