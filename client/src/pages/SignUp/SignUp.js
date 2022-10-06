import React from "react";
import { useRef, useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import styles from "./SignUp.module.css";
import naverLogo from "../../assets/naver-logo.png";
import kakaoLogo from "../../assets/kakao-logo.png";
import googleLogo from "../../assets/google-logo.png";
import Modal from "../../components/Modal/Modal";
import { emailCheck, signUpReq } from "../../api/services/auth";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { modalActions } from "../../store/modal";
import { baseURL } from "../../api/axios";

const SignUp = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const emailRef = useRef();
  const PWRef = useRef();
  const confirmPWRef = useRef();
  const OwenerNameRef = useRef();
  const phoneNumRef = useRef();
  const businessNameRef = useRef();
  const [validationMSG, setValidationMSG] = useState("");
  const isModalOpen = useSelector((state) => state.modal.isModalOpen);
  const [changeCSS, setChangeCSS] = useState(false);
  const dispatch = useDispatch();
  useEffect(() => {
    setChangeCSS(true);
  }, [changeCSS]);

  const SignUpHandler = async (event) => {
    event.preventDefault();

    const email = emailRef.current.value;
    const password = PWRef.current.value;
    const confirmPassword = confirmPWRef.current.value;
    const name = OwenerNameRef.current.value;
    const businessName = businessNameRef.current.value;
    const phone = phoneNumRef.current.value;
    setIsLoading(true);
    setChangeCSS(false);

    const validationInput = async () => {
      let regEmail =
        /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
      const phoneCheck = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;

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

      if (email.length === 0) {
        setValidationMSG("이메일을 입력해주세요");
        return false;
      }
      if (regEmail.test(email) === false) {
        setValidationMSG("올바른 이메일 주소를 입력해주세요");
        return false;
      }

      const exist = await emailCheck(email); // 이메일 중복검사

      if (exist) {
        setValidationMSG("이미 가입되어 있는 이메일 입니다");
        return false;
      }

      if (password.length === 0) {
        setValidationMSG("비밀번호를 입력해주세요");
        return false;
      }
      if (strCheck(password, "pwd") === false) {
        setValidationMSG("비밀번호는 8~16자 영문+숫자+특수문자로 입력해주세요");
        return false;
      }
      if (password !== confirmPassword) {
        // 비밀번호 일치 검사
        setValidationMSG("비밀번호가 일치하지 않습니다.");
        return false;
      }
      if (name.length === 0) {
        setValidationMSG("대표 성명을 입력해주세요");
        return false;
      }
      if (businessName.length === 0) {
        setValidationMSG("상호명을 입력해주세요");
        return false;
      }

      if (phone.length === 0) {
        setValidationMSG("휴대폰 번호를 입력해주세요");
        return false;
      }
      if (!phoneCheck.test(phone)) {
        setValidationMSG("휴대폰 번호를 정확히 입력해주세요");
        return false;
      }

      return true;
    };
    const check = await validationInput();

    if (check === true) {
      setValidationMSG("");
      console.log("유효성 통과");
      try {
        signUpReq(email, password, name, businessName, phone);
        setIsLoading(false);
        dispatch(modalActions.setIsModalOpen(true));
        setTimeout(() => {
          navigate("/login");
          dispatch(modalActions.setIsModalOpen(false));
        }, 1500);
      } catch (err) {
        let errorMessage = err.error.message;
        alert(errorMessage);
      }
    } else {
      setIsLoading(false);
      return;
    }

    //email check

    // validationInput(); // 유효성검사
  };

  return (
    <div className={styles.signUp}>
      <form onSubmit={SignUpHandler} className={styles.signUp__form}>
        <div className={styles.signUp__form__title}>
          {/* <img src={mainLogo} alt="react" /> */}
          <h1>회원가입</h1>
        </div>
        <div className={styles.signUp__form__validation}>
          {changeCSS === false ? (
            <p>{validationMSG}</p>
          ) : (
            <p className={styles.shake}>{validationMSG}</p>
          )}
        </div>
        <div className={styles.signUp__form__input}>
          <div className={styles.signUp__form__input__email}>
            <span>이메일</span>
            <input ref={emailRef} placeholder="예: quickbook@quickbook.com" />
          </div>
          <div className={styles.signUp__form__input__password}>
            <span>비밀번호</span>
            <input
              maxLength={16}
              type="password"
              ref={PWRef}
              placeholder="숫자,영문,특수문자 8~16자 입력"
            />
          </div>
          <div className={styles.signUp__form__input__passwordCheck}>
            <span>비밀번호 확인</span>

            <input
              maxLength={16}
              type="password"
              ref={confirmPWRef}
              placeholder="비밀번호 확인"
            />
          </div>
          <div className={styles.signUp__form__input__name}>
            <span>대표 성명</span>
            <input
              maxLength={8}
              ref={OwenerNameRef}
              placeholder="대표자 성명"
            />
          </div>
          <div className={styles.signUp__form__input__bussnissName}>
            <span>상호명</span>
            <input
              maxLength={10}
              ref={businessNameRef}
              placeholder="예:덕이네곱창"
            />
          </div>
          <div className={styles.signUp__form__input__phone}>
            <span>전화번호</span>
            <input ref={phoneNumRef} placeholder="010-xxxx-xxxx (휴대폰번호)" />
          </div>
        </div>

        <div className={styles.signUp__form__oauth}>
          <div>
            <a
              href={`${baseURL}/login/oauth2/authorize/naver?redirect_uri=https://quick-book.ml/oauth2/redirect`}
            >
              <img src={naverLogo} alt="React" />
            </a>
          </div>
          <div>
            <a
              href={`${baseURL}/login/oauth2/authorize/kakao?redirect_uri=https://quick-book.ml/oauth2/redirect`}
            >
              <img src={kakaoLogo} alt="React" />
            </a>
          </div>

          <div>
            <a
              href={`${baseURL}/login/oauth2/authorize/google?redirect_uri=https://quick-book.ml/oauth2/redirect`}
            >
              <img src={googleLogo} alt="React" />
            </a>
          </div>
        </div>
        <div className={styles.signUp__form__btn}>
          {!isLoading && <button>회원가입</button>}
          {isLoading && <button>요청중</button>}
          <Link to="/login">
            <button>취소</button>
          </Link>
        </div>
        {isModalOpen && <Modal num={1} />}
      </form>
    </div>
  );
};
export default SignUp;
