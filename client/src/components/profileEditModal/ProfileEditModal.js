import axios from "axios";
import { useNavigate } from "react-router-dom";
import React, { useState, useRef } from 'react';
import styles from "./profileEditModal.module.css";
import noneProfile from "../../Img/Asset_5.png";
import imgPlusBtn from "../../Img/imgPlusBtn.png";
import { postProfileEdit } from "../../api/services/profileEdit";
import { useSelector, useDispatch } from "react-redux";
import { userAction } from "../../store/user";

const ProfileEdit = ({ setIsModal }) => {
  const [password, setPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newName, setNewName] = useState("");
  const [phone, setPhone] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [errMessage, setErrMessage] = useState('');
  const [image, setImage] = useState({});

  const navigate = useNavigate()
  const passwordRef = useRef()
  const nameRef = useRef()
  const confirmPasswordRef = useRef()
  // const profileImg = useSelector(state => state.profileImg.value)
  let inputRef;
  const dispatch = useDispatch();

  //프로필 수정 모달-------------------------------------------------------
  const modalClose = () => {
    setIsModal(false);
  };

  // 이미지 preview------------------------------------------------------
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

  // 이미지 data,file(formData) 전송--------------------------------------
  const profileEditSubmit = async () => {
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
        "password": newPassword,
        "service": ["reservation"],
        "name": newName,
        "phone": phone,
      }
      formData.append('file', image.image_file)
      formData.append("data",
        new Blob([JSON.stringify(profileFormData)], { type: "application/json" }));
      console.log(formData.getAll('data'));

      const profileData = await postProfileEdit(formData)
      console.log(profileData)
      // dispatch(userAction(profileData))
      alert("프로필 수정 완료!");
      // setIsModal(false);      
      // setImage({
      //   image_file: "",
      //   preview_URL: "",
      // });
    }
  }

  return (
    <div>
      <h1 className={styles.title}>프로필 수정</h1>
      <form className={styles.contents_container} onSubmit={(e) => e.preventDefault()}>
        <div>
          <input
            type="file"
            accept="image/*"
            onChange={saveImage}
            onClick={(e) => e.target.value = null}
            ref={refParam => inputRef = refParam}
            style={{ display: "none" }}
          />
          <div className={styles.imgWrapper}>
            {image.preview_URL === undefined ?
              <img src={noneProfile} /> :
              <img src={image.preview_URL} className={styles.imgPreview} />}
          </div>
          <div>
            <button className={styles.imgPlusBtn} onClick={() => inputRef.click()}>
              <img src={imgPlusBtn} className={styles.imgPlus} />
            </button>
          </div>
        </div>
        <div className={styles.contents_info}>
          <div>
            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"text"}
                onChange={(e) => setNewName(e.target.value)}
                ref={nameRef}
                placeholder="관리자 명"
              />
            </div>

            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"tel"}
                onChange={(e) => setPhone(e.target.value)}
                ref={nameRef}
                placeholder="연락처"
              />
            </div>
            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"password"}
                onChange={(e) => setNewPassword(e.target.value)}
                ref={passwordRef}
                placeholder="새 비밀번호"
              />
            </div>
            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"password"}
                onChange={(e) => setPassword(e.target.value)}
                ref={confirmPasswordRef}
                placeholder="새 비밀번호 확인"
              />
            </div>
          </div>
          {errMessage ? (
            <div className={styles.errMsg}>
              {errMessage}
            </div>
          ) : (
            ''
          )}
          <button type='submit' onClick={profileEditSubmit} className={styles.btn}>완 료</button>
          <button type='submit' onClick={modalClose} className={styles.btn}>닫 기</button>
        </div>
      </form>
    </div>
  );
};
export default ProfileEdit;