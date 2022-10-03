import React, { useState, useRef, useEffect } from 'react';
import styles from "./profileEditModal.module.css";
import noneProfile from "../../Img/Asset_5.png";
import imgPlusBtn from "../../Img/imgPlusBtn.png";
import { postProfileEdit } from "../../api/services/profileEdit";
import { getProfile } from "../../../src/api/services/user";
import { profileAction } from "../../store/profile"
import { useSelector, useDispatch } from "react-redux";
import { userAction } from "../../store/user";

const ProfileEdit = ({ setIsModal, isModal }) => {
  const [password, setPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newName, setNewName] = useState("");
  const [phone, setPhone] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [errMessage, setErrMessage] = useState('');
  const [image, setImage] = useState({});
  const [profile, setProfile] = useState({});

  const passwordRef = useRef()
  const confirmPasswordRef = useRef()
  // const nameRef = useRef()
  const profileName = useSelector(state => state.profile)
  console.log(profileName)
  const dispatch = useDispatch();
  let inputRef;

  //프로필 수정 모달-------------------------------------------------------
  const modalClose = () => {
    setIsModal(!isModal);
  };

  // 이미지 preview------------------------------------------------------
  const saveImage = (e) => {
    console.log(image.preview_URL)
    e.preventDefault();
    const fileReader = new FileReader();
    // console.log(fileReader)
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
    } else if (!password) {
      setErrMessage('빈칸을 채워주세요');
      return;
    } else if (!image.image_file) {
      alert("사진을 등록하세요!")
    } else {
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
      window.location.reload();
    }
  }

  // 프로필 수정 이미지 불러오기 분기 처리------------------------------------
  const profileImgRender = () => {
    if (image.preview_URL === undefined &&
      profile.profileImg === null) {
      return <img
        src={noneProfile}
        alt="None profileImg"
      />
    }
    if (image.preview_URL) {
      return <img
        src={image.preview_URL}
        className={styles.imgPreview}
        alt="profileImg"
      />
    } else if (profile.profileImg) {
      return <img
        src={"http://localhost:8080" + profile.profileImg}
        alt="ProfileImg"
        className={styles.imgPreview}
      />
    }
  }

  // getProfile()로 proifle 불러와서 input에 띄워주기-----------------------
  useEffect(() => {
    getProfile()
      .then(profileData => {
        setProfile(profileData)
        dispatch(profileAction.changeName(profile.name))
        console.log(profileData)
      })
  }, [])

  return (
    <div className={styles.profile_container}>
      <form
        className={styles.contents_container}
        onSubmit={(e) => e.preventDefault()}>
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
            {profileImgRender()}
          </div>
          <div>
            <button
              className={styles.imgPlusBtn}
              onClick={() => inputRef.click()}>
              <img
                src={imgPlusBtn}
                className={styles.imgPlus}
                alt="button"
              />
            </button>
          </div>
        </div>
        <div className={styles.contents_info}>
          <div>
            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"text"}
                onChange={(e) => {
                  setNewName(e.target.value)
                  // if (e.target.value === "") {
                  //   return profileName.name
                  // }
                  // else {
                  //   console.log(profile.name)
                  //   return setNewName(e.target.value)
                  // }
                  //   e.target.value ? setNewName(e.target.value) : profile.name
                  // }
                  // ref={nameRef}
                }}
                placeholder={profile.name}
              />
            </div>

            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"tel"}
                // onChange={(e) => setPhone(e.target.value)}
                onChange={(e) => {
                  return setPhone(e.target.value === "" ? profile.phone :
                    e.target.value)
                }}
                placeholder={profile.phone}
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
          <div className={styles.bottomBtn}>
            <button type='submit' onClick={profileEditSubmit} className={styles.btn}>완 료</button>
            <button type='submit' onClick={modalClose} className={styles.btn}>닫 기</button>
          </div>
        </div>
      </form>
    </div>
  );
};
export default ProfileEdit;