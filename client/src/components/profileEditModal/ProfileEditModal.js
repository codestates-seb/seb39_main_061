import axios from "axios";
import { useNavigate } from "react-router-dom";
import React, { useState, useRef } from 'react';

const ProfileEdit = ({ setIsModal }) => {
  const [password, setPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [errMessage, setErrMessage] = useState('');
  const [image, setImage] = useState({
    image_file: "",
    preview_URL: "img/default_image.png",
  });

  const navigate = useNavigate()
  const passwordRef = useRef()
  const confirmPasswordRef = useRef()

  let inputRef;

  //프로필 수정 모달
  const modalClose = () => {
    setIsModal(false);
  };

  // 이미지 preview
  const saveImage = (e) => {
    e.preventDefault();
    const fileReader = new FileReader();
    if (e.target.files[0]) {
      fileReader.readAsDataURL(e.target.files[0])
    }
    fileReader.onload = () => {
      setImage(
        {
          image_file: e.target.files[0],
          preview_URL: fileReader.result
        }
      )
    }
  }

  const token = ""

  // 이미지 data,file(formData) 전송
  const profileEditSubmiy = async () => {
    const passwordEdit = passwordRef.current.value;
    const confirmPasswordEdit = confirmPasswordRef.current.value;
    if (passwordEdit !== confirmPasswordEdit) {
      setErrMessage("비밀번호가 일치하지 않습니다.");
      return;
    }
    setIsLoading(true);
    if (!password) {
      setErrMessage('빈칸을 채워주세요');
      return;
    } else if (!image.image_file) {
      alert("사진을 등록하세요!")
    }
    else {
      const formData = new FormData()
      formData.enctype = "multipart/form-data"
      const profileFormData = {
        "password": passwordEdit,
        "sectorId": 1,
        "service": ["RESERVATION"]
      }
      formData.append('file', image.image_file)
      formData.append("data",
        new Blob([JSON.stringify(profileFormData)], { type: "application/json" }));
      console.log(formData.get('file'));
      const profileData = await axios.post(
        'http://localhost:8080/api/v1/members/profile',
        formData,
        {
          headers: {
            Authorization: "Bearer " + token,
            "Content-Type": "multipart/form-data"
          }
        });
      console.log(profileData);
      alert("프로필 수정 완료!");
      setImage({
        image_file: "",
        preview_URL: "img/default_image.png",
      });
      navigate('/profile')
      window.location.reload();
    }
  }

  return (
    <div>
      <h1>프로필 수정</h1>
      <form onSubmit={(e) => e.preventDefault()}>
        <input
          type="file"
          accept="image/*"
          onChange={saveImage}
          // 클릭할 때 마다 file input의 value를 초기화 하지 않으면 버그가 발생
          // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있음!
          onClick={(e) => e.target.value = null}
          ref={refParam => inputRef = refParam}
          style={{ display: "none" }}
        />
        <div>
          <img src={image.preview_URL} />
        </div>
        <div>
          <button onClick={() => inputRef.click()}>
            +
          </button>
        </div>
        <div>
          <div>
            <span>새 비밀번호</span>
            <input
              type={"password"}
              onChange={(e) => setNewPassword(e.target.value)}
              ref={passwordRef}
            />
          </div>
          <div>
            <span>새 비밀번호 확인</span>
            <input
              type={"password"}
              onChange={(e) => setPassword(e.target.value)}
              ref={confirmPasswordRef}
            />
          </div>
        </div>
        <div>
          <div>이용중인 서비스 목록</div>
          <div>- 예약/대기 서비스</div>
          <div>- 관리 서비스</div>
        </div>
        {errMessage ? (
          <div>
            {errMessage}
          </div>
        ) : (
          ''
        )}
        <button type='submit' onClick={profileEditSubmiy}>Edit</button>
        <button type='submit' onClick={modalClose}>Close</button>
      </form>
    </div>
  );
};
export default ProfileEdit;
