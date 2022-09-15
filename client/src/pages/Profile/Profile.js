import Sidebar from "../../components/Sidebar/Sidebar";
import ProfileEditModal from "../../components/profileEditModal/ProfileEditModal";
import axios from "axios";
import { Link } from "react-router-dom";
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

  useEffect(() => {
    // const getPosts = async () => {
    //   const posts = await axios.get(
    //     "http://localhost:8080/api/v1/members/profile", {
    //     headers: { Authorization: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5aXRza3lAbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfUkVTRVJWQVRJT04iLCJpYXQiOjE2NjMyMjUzNDMsImV4cCI6MTY2MzIyODk0M30.49EGzfQvuRj3SaTET1Md5g_s0vdpuWgMv0OJvU8yFfX-l0pNJxO_67G_ggjrtdZkQkfGCDrjq5eBBippPC-Ufg" }
    //   }
    //   );
    //   setUserInfo(posts.data);
    // };
    // getPosts();
    // console.log(userInfo)
    axios.get("http://localhost:8080/api/v1/members/profile",
      {
        headers: { Authorization: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5aXRza3lAbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfUkVTRVJWQVRJT04iLCJpYXQiOjE2NjMyNDcyNTEsImV4cCI6MTY2MzI1MDg1MX0.j_er63uI_lgy_MQW--p5UYQ8r0wjEyLP8m7Jl453agAWpsss62jm1HuIRak2y1O67977mXLmFciaKus2qYY-rA" }
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
