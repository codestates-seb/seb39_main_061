import React, { useState, useRef, useEffect } from "react";
import styles from "./profileEditModal.module.css";
import noneProfile from "../../Img/Asset_5.png";
import imgPlusBtn from "../../Img/imgPlusBtn.png";
import { postProfileEdit } from "../../api/services/profileEdit";
import { getProfile } from "../../../src/api/services/user";

const ProfileEdit = ({ setIsModal, isModal, setIsOkModalOpen }) => {
  const [password, setPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newName, setNewName] = useState("");
  const [phone, setPhone] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [errMessage, setErrMessage] = useState("");
  const [image, setImage] = useState({});
  const [profile, setProfile] = useState({});

  const passwordRef = useRef();
  const confirmPasswordRef = useRef();
  let inputRef;

  //프로필 수정 모달-------------------------------------------------------
  const modalClose = () => {
    setIsModal(!isModal);
  };

  //번호 정규표현식-------------------------------------------------------
  const handlePhone = (e) => {
    setPhone(e.target.value.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3"));
  };

  // 이미지 preview------------------------------------------------------
  const saveImage = (e) => {
    console.log(image.preview_URL);
    e.preventDefault();
    const fileReader = new FileReader();
    // console.log(fileReader)
    if (e.target.files[0]) {
      fileReader.readAsDataURL(e.target.files[0]);
    }
    console.log(fileReader);
    fileReader.onload = () => {
      setImage({
        image_file: e.target.files[0],
        preview_URL: fileReader.result,
      });
    };
  };

  // 이미지 data,file(formData) 전송--------------------------------------
  const profileEditSubmit = async () => {
    const passwordEdit = passwordRef.current.value;
    const confirmPasswordEdit = confirmPasswordRef.current.value;
    function strCheck(str, type) {
      const REGEX = {
        EMAIL: /\S+@\S+\.\S+/,
        PWD_RULE: /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,16}$/,
        NAME_RULE: /^[가-힣a-zA-Z]+$/,
      };
      if (type === "email") {
        return REGEX.EMAIL.test(str);
      } else if (type === "pwd") {
        return REGEX.PWD_RULE.test(str);
      } else if (type === "name") {
        return REGEX.NAME_RULE.test(str);
      } else {
        return false;
      }
    }

    if (passwordEdit !== confirmPasswordEdit) {
      setErrMessage("비밀번호가 일치하지 않습니다.");
      return;
    } else if (
      strCheck(confirmPasswordEdit, "pwd") === false &&
      confirmPasswordEdit.length !== 0 &&
      passwordEdit.length !== 0
    ) {
      setErrMessage("비밀번호는 8~16자 영문+숫자+특수문자로 입력해주세요");
      return false;
    } else {
      const formData = new FormData();
      formData.enctype = "multipart/form-data";
      let profileFormData;
      if (!password) {
        profileFormData = {
          service: ["reservation"],
          name: newName,
          phone: phone,
        };
      } else {
        profileFormData = {
          password: newPassword,
          service: ["reservation"],
          name: newName,
          phone: phone,
        };
      }

      if (!image.image_file) {
        const newFile = new File([""], "");
        formData.append("file", newFile);
      } else {
        formData.append("file", image.image_file);
      }

      formData.append(
        "data",
        new Blob([JSON.stringify(profileFormData)], {
          type: "application/json",
        })
      );
      console.log(formData.getAll("data"));

      const profileData = await postProfileEdit(formData);
      console.log(profileData);
      setIsOkModalOpen(true);
      setTimeout(() => {
        window.location.reload();
      }, 1500);
    }
  };

  // 프로필 수정 이미지 불러오기 분기 처리------------------------------------
  const profileImgRender = () => {
    if (image.preview_URL === undefined && profile.profileImg === null) {
      return <img src={noneProfile} alt="None profileImg" />;
    }
    if (image.preview_URL) {
      return (
        <img
          src={image.preview_URL}
          className={styles.imgPreview}
          alt="profileImg"
        />
      );
    } else if (profile.profileImg) {
      return (
        <img
          src={"http://localhost:8080" + profile.profileImg}
          alt="ProfileImg"
          className={styles.imgPreview}
        />
      );
    }
  };

  // getProfile()로 proifle 불러와서 input에 띄워주기-----------------------
  useEffect(() => {
    getProfile().then((profileData) => {
      setProfile(profileData);
      setNewName(profileData.name);
      setPhone(profileData.phone);
    });
  }, []);

  // input value 핸들러 함수 ---------------------------------------------
  const newNameHandler = (e) => {
    setNewName(e.target.value);
  };
  const PhoneHandler = (e) => {
    setPhone(e.target.value);
  };

  return (
    <div className={styles.profile_container}>
      <form
        className={styles.contents_container}
        onSubmit={(e) => e.preventDefault()}
      >
        <div>
          <input
            type="file"
            accept="image/*"
            onChange={saveImage}
            onClick={(e) => (e.target.value = null)}
            ref={(refParam) => (inputRef = refParam)}
            style={{ display: "none" }}
          />
          <div className={styles.imgWrapper}>{profileImgRender()}</div>
          <div>
            <button
              className={styles.imgPlusBtn}
              onClick={() => inputRef.click()}
            >
              <img src={imgPlusBtn} className={styles.imgPlus} alt="button" />
            </button>
          </div>
        </div>
        <div className={styles.contents_info}>
          <div>
            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"text"}
                value={newName === null ? "" : newName}
                onChange={newNameHandler}
                placeholder={profile.name}
              />
            </div>

            <div className={styles.contents_text}>
              <input
                className={styles.input}
                type={"tel"}
                value={phone === null ? "" : phone}
                onChange={PhoneHandler}
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
          {errMessage ? <div className={styles.errMsg}>{errMessage}</div> : ""}
          <div className={styles.bottomBtn}>
            <button
              type="submit"
              onClick={profileEditSubmit}
              className={styles.btn}
            >
              완 료
            </button>
            <button type="submit" onClick={modalClose} className={styles.btn}>
              닫 기
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};
export default ProfileEdit;
