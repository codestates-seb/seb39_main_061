import { useEffect, useState } from "react";
import { useRef } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate, Link } from "react-router-dom";
import { authActions } from "../../store/auth";
import styles from "./Register.module.css";
import Modal from "../../components/Modal/Modal";
import Login from "../Login/Login";
import { oauthReq } from "../../api/services/auth";

const Register = () => {
  const businessNameRef = useRef();
  const phoneNumRef = useRef();
  const nameRef = useRef();

  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [validationMSG, setValidationMSG] = useState("");
  const [modalOpen, setIsModalOpen] = useState(false);
  const [page, setPage] = useState("register");
  const [changeCSS, setChangeCSS] = useState(false);
  useEffect(() => {
    setChangeCSS(true);
  }, [changeCSS]);

  const oauthValidation = location.search.includes("true");
  const accessToken = String(location.search.split("&", 1));
  const token = accessToken.substring(13);
  const pageTitle = "페이지제목";

  window.history.pushState("", pageTitle, `/oauth2`);

  // 인증 됐으면 바로 로그인 -> 유저데이터 받아오기 -> 대시보드
  const checkValidation = async () => {
    if (oauthValidation === true) {
      setPage("login");
      localStorage.setItem("token", token);
      // const userData = await getProfile();
      // dispatch(userAction.setUser(userData));
      setIsModalOpen(true);
      setTimeout(() => {
        navigate("/dashboard");
        dispatch(authActions.login());
      }, 3000);
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
    setPage("register");
    setChangeCSS(false);

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

    if (phone.length === 0) {
      setValidationMSG("휴대폰 번호를 입력해주세요");
      return;
    }
    if (!phoneCheck.test(phone)) {
      setValidationMSG("휴대폰 번호를 정확히 입력해주세요");
      return;
    }
    if (name.length === 0) {
      setValidationMSG("이름을 입력해주세요");
      return;
    }

    const res = await oauthReq(businessName, phone, name);
    if (res.status === 200) {
      setValidationMSG("");
      console.log("추가전송");
      localStorage.setItem("token", res.data.data.accessToken);
      setIsModalOpen(true);
      setTimeout(() => {
        navigate("/login");
      }, 1500);
    }
  };

  return (
    <div className={styles.register}>
      {page === "login" && <Login />}
      {/* <p>{location.search}</p> */}
      {page === "register" && (
        <div className={styles.register__form}>
          <div className={styles.register__form__title}>
            <h1>회원가입</h1>
            {/* <img src={mainLogo} alt="react" /> */}
          </div>
          <div className={styles.register__form__validation}>
            {changeCSS === false ? (
              <p>{validationMSG}</p>
            ) : (
              <p className={styles.shake}>{validationMSG}</p>
            )}
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
      )}
      {modalOpen && (
        <Modal
          num={oauthValidation === true ? 0 : 2}
          setIsModalOpen={setIsModalOpen}
        />
      )}
    </div>
  );
};

export default Register;
