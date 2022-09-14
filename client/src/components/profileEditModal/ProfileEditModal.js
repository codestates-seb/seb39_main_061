import Sidebar from "../Sidebar/Sidebar";
import ProfileImgUpload from "../profileImgUpload/ProfileImgUpload";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import React, { useState } from 'react';

const ProfileEdit = ({ setIsModal }) => {
  // const [profileImg, setProfileImg] = useState("");
  const [imgFile, setImgFile] = useState({
    image_file: "",
    preview_URL: "img/default_image.png"
  })
  const [companyName, setCompanyName] = useState("");
  const [sectorName, setSectorName] = useState("");
  const [phoneNum, setPhoneNum] = useState();
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState('');

  /*
  const [profileInfo, setProfileInfo] = useState({
    companyName: "",
    sector: "",
    password: "",
  });
  */
  const navigate = useNavigate()

  const modalClose = () => {
    setIsModal(false);
  };

  const profileSubmitHandler = async () => {
    if (
      // !profileImg || 
      !companyName ||
      !sectorName || !phoneNum ||
      !userName || !email || !password
    ) {
      setErrorMessage('빈칸을 채워주세요');
      return;
    } else {
      const ProfileUpdateInfo = {
        // "profileImg": profileImg,
        "companyName": companyName,
        "sectorName": sectorName,
        "phoneNum": phoneNum,
        "userName": userName,
        "email": email,
        "password": password,
      }
      console.log(ProfileUpdateInfo)
      const profileRes = await axios.post('/api/v1/questions', ProfileUpdateInfo,
        {
          "Content-Type": "application/json",
          "Accept": "application/json",
          headers: { Authorization: "Bearer " + JSON.parse(window.localStorage.getItem("access_token")).access_token }
        })
      console.log(profileRes)
    }
    navigate('/dashboard')
  }

  return (
    <div>
      {/* <Sidebar /> */}
      <h1>프로필 수정</h1>
      <form onSubmit={(e) => e.preventDefault()}>
        {/* <img src="https://avatars.githubusercontent.com/u/82711000?v=4.jpg" /> */}
        <ProfileImgUpload />
        <div>
          <label>상호명</label>
          <input type="text" onChange={(e) => setCompanyName(e.target.value)} />
        </div>
        <div>
          <label>업종</label>
          <input type="text" onChange={(e) => setSectorName(e.target.value)} />
        </div>
        <div>
          <label>전화번호</label>
          <input type="text" onChange={(e) => setPhoneNum(e.target.value)} />
        </div>
        <div>
          <label>관리자 명</label>
          <input type="text" onChange={(e) => setUserName(e.target.value)} />
        </div>
        <div>
          <label>Email</label>
          <input type="text" onChange={(e) => setEmail(e.target.value)} />
        </div>
        <div>
          <label>비밀번호</label>
          <input type={"password"} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <div>
          <div>이용중인 서비스 목록</div>
          <div>예약/대기 서비스</div>
          <div>관리 서비스</div>
        </div>
        {errorMessage ? (
          <div>
            {errorMessage}
          </div>
        ) : (
          ''
        )}
        <button type='submit' onClick={profileSubmitHandler}>Edit</button>
        <button type='submit' onClick={modalClose}>Close</button>
      </form>
    </div>
  );
};
export default ProfileEdit;
