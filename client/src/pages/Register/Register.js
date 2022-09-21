import { useEffect, useState } from "react";
import { useRef } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate, Link } from "react-router-dom";
import { authActions } from "../../store/auth";
import styles from "./Register.module.css";
import { getProfile } from "../../library/axios";
import { userAction } from "../../store/user";
import { oauthReq } from "../../library/axios";
import mainLogo from "../../assets/logo1.png";

const Register = () => {
  const BusinessCategoryRef = useRef();
  const businessNameRef = useRef();
  const phoneNumRef = useRef();
  const nameRef = useRef();

  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [validationMSG, setValidationMSG] = useState("");

  const oauthValidation = location.search.includes("true");
  const accessToken = String(location.search.split("&", 1));
  const token = accessToken.substring(13);
  const pageTitle = "페이지제목";
  window.history.pushState("", pageTitle, `/oauth2`);

  // 인증 됐으면 바로 로그인 -> 유저데이터 받아오기 -> 대시보드
  const checkValidation = async () => {
    if (oauthValidation === true) {
      localStorage.setItem("token", token);
      const userData = await getProfile();
      dispatch(userAction.setUser(userData));
      dispatch(authActions.login());
      navigate("/dashboard");
    }
  };
  useEffect(() => {
    localStorage.setItem("token", token);
    checkValidation();
  }, []);

  //인증 false -> 추가 기입 전송 -> 로그인,유저데이터 받아오기
  const handlerSubmit = async () => {
    const businessName = businessNameRef.current.value;
    const phone = phoneNumRef.current.value;
    const name = nameRef.current.value;

    if (businessName.length === 0) {
      setValidationMSG("상호명을 입력해주세요");
      return;
    }
    let phoneCheck = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;
    let kor_check = /([^가-힣ㄱ-ㅎㅏ-ㅣ\x20])/i;
    if (kor_check.test(businessName)) {
      setValidationMSG("상호명은 한글로 입력해주세요");
      return;
    }
    if (kor_check.test(businessName)) {
      setValidationMSG("상호명은 한글로 입력해주세요");
      return;
    }
    if (phone.length === 0) {
      setValidationMSG("전화번호를 입력해주세요");
      return;
    }
    if (!phoneCheck.test(phone)) {
      setValidationMSG("전화번호를 정확히 입력해주세요");
      return;
    }
    if (name.length === 0) {
      setValidationMSG("이름을 입력해주세요");
      return;
    }

    const newToken = await oauthReq(businessName, phone, name);
    if (newToken.status === 200) {
      localStorage.setItem("token", newToken);
      dispatch(authActions.login());
      const userData = await getProfile();
      dispatch(userAction.setUser(userData));
      navigate("/dashboard");
    }
  };

  return (
    <div className={styles.register}>
      {/* <p>{location.search}</p> */}
      <div className={styles.register__form}>
        <div className={styles.register__form__title}>
          <h1>회원가입</h1>
          {/* <img src={mainLogo} alt="react" /> */}
        </div>
        <div className={styles.register__form__validation}>
          <p>{validationMSG}</p>
        </div>
        <div className={styles.register__form__input}>
          <div className={styles.register__form__input__businessName}>
            <span>상호명</span>
            <input
              maxLength={15}
              ref={businessNameRef}
              placeholder="예: 덕이네곱창(한글)"
            />
          </div>
          <div className={styles.register__form__input__phone}>
            <span>휴대폰 번호</span>
            <input ref={phoneNumRef} placeholder="예: 010-xxxx-xxxx" />
          </div>
          <div className={styles.register__form__input__name}>
            <span>이름</span>
            <input
              maxLength={8}
              ref={nameRef}
              placeholder="2글자 ~ 8글자 입력"
            />
          </div>
        </div>
        <div className={styles.register__form__btn}>
          <button onClick={handlerSubmit}>회원가입</button>
          <Link to="/login">
            <button>취소</button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Register;
