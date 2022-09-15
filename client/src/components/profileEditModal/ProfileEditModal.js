import Sidebar from "../Sidebar/Sidebar";
import ProfileImgUpload from "../profileImgUpload/ProfileImgUpload";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';


const ProfileEdit = ({ setIsModal }) => {

  const [companyName, setCompanyName] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState('');
  // const [sectorName, setSectorName] = useState("");
  // const [phoneNum, setPhoneNum] = useState();
  // const [userName, setUserName] = useState("");
  // const [email, setEmail] = useState("");
  const profileImg = useSelector(state=> state.value)

  const navigate = useNavigate()

  const modalClose = () => {
    setIsModal(false);
  };

  const profileSubmitHandler = async () => {
    if (
      // !profileImg || 
      !companyName || !password
    ) {
      setErrorMessage('빈칸을 채워주세요');
      return;
    } else {
      const ProfileUpdateInfo = {
        // "profileImg": profileImg,
        "companyName": companyName,
        "password": password,
        // "sectorName": sectorName,
        // "phoneNum": phoneNum,
        // "userName": userName,
        // "email": email,
      }
      console.log(ProfileUpdateInfo)
      const profileRes = await axios.patch('/api/v1/questions', ProfileUpdateInfo,
        {
          "Content-Type": "application/json",
          "Accept": "application/json",
          headers: { Authorization: "Bearer " + JSON.parse(window.localStorage.getItem("access_token")).access_token }
        })
      console.log(profileRes)
    }
    //post 요청 성공하면 /Profile 페이지 이동
    navigate('/profile')
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
          <div>
            <label>기존 비밀번호</label>
            <input type={"password"} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <div>
            <label>기존 비밀번호 재확인</label>
            <input type={"password"} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <div>
            <label>새 비밀번호</label>
            <input type={"password"} onChange={(e) => setPassword(e.target.value)} />
          </div>
        </div>
        <div>
          <div>이용중인 서비스 목록</div>
          <div>- 예약/대기 서비스</div>
          <div>- 관리 서비스</div>
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
