import { useRef, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import styles from "./SignUp.module.css";
import axios from "axios";
import { signUpReq } from "../../library/axios";
import { emailCheck } from "../../library/axios";
import mainLogo from "../../assets/logo1.png";
import naverLogo from "../../assets/naver-logo.png";
import kakaoLogo from "../../assets/kakao-logo.png";
import googleLogo from "../../assets/google-logo.png";

const SignUp = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const emailRef = useRef();
  const PWRef = useRef();
  const confirmPWRef = useRef();
  const OwenerNameRef = useRef();
  const phoneNumRef = useRef();
  const businessNameRef = useRef();
  const BusinessCategoryRef = useRef();
  const [validationMSG, setValidationMSG] = useState("");

  const SignUpHandler = (event) => {
    event.preventDefault();
    const email = emailRef.current.value;
    const password = PWRef.current.value;
    const confirmPassword = confirmPWRef.current.value;
    const name = OwenerNameRef.current.value;
    const businessName = businessNameRef.current.value;
    const phone = phoneNumRef.current.value;

    const checkValidation = () => {
      let regEmail =
        /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
      if (email.length === 0) {
        setValidationMSG("이메일을 입력해주세요");
        return false;
      }
      if (regEmail.test(email) === false) {
        setValidationMSG("올바른 이메일 주소를 입력해주세요");
        return false;
      }
      if (password.length === 0) {
        setValidationMSG("비밀번호를 입력해주세요");
        return false;
      }
      return true;
    };

    //email check
    checkValidation(email); // 이메일 유효성검사
    if (checkValidation(email) === false) {
      return;
    }
    const exist = emailCheck(email); // 이메일 중복검사
    exist
      ? setValidationMSG("사용 가능한 이메일 입니다")
      : setValidationMSG("이미 가입되어 있는 이메일 입니다");

    if (password !== confirmPassword) {
      // 비밀번호 일치 검사
      setValidationMSG("비밀번호가 일치하지 않습니다.");
      return console.log("비밀번호가 일치하지 않습니다.");
    }
    setIsLoading(true);

    try {
      signUpReq(email, password, name, businessName, phone);
      setIsLoading(false);
      navigate("/login");
    } catch (err) {
      let errorMessage = err.error.message;
      alert(errorMessage);
    }
  };

  const emailCheckReq = () => {
    console.log("중복검사");
    const email = emailRef.current.value;
  };

  return (
    <div className={styles.signUp}>
      <form onSubmit={SignUpHandler} className={styles.signUp__form}>
        <div className={styles.signUp__form__title}>
          <img src={mainLogo} alt="react" />
        </div>
        <div className={styles.signUp__form__validation}>
          <p>{validationMSG}</p>
        </div>
        <div className={styles.signUp__form__input}>
          <div className={styles.signUp__form__input__email}>
            <span>이메일</span>
            <input ref={emailRef} placeholder="예: quickbook@quickbook.com" />
          </div>
          <div className={styles.signUp__form__input__password}>
            <span>비밀번호</span>
            <input
              type="password"
              ref={PWRef}
              placeholder="숫자,영문,특수문자 8~16자 입력"
            />
          </div>
          <div className={styles.signUp__form__input__passwordCheck}>
            <span>비밀번호 확인</span>
            <input
              type="password"
              ref={confirmPWRef}
              placeholder="비밀번호 확인"
            />
          </div>
          <div className={styles.signUp__form__input__name}>
            <span>대표 성명</span>
            <input ref={OwenerNameRef} placeholder="대표자 성명" />
          </div>
          <div className={styles.signUp__form__input__bussnissName}>
            <span>상호명</span>
            <input ref={businessNameRef} placeholder="예: 덕이네곱창(한글)" />
          </div>
          <div className={styles.signUp__form__input__phone}>
            <span>전화번호</span>
            <input ref={phoneNumRef} placeholder="010-xxxx-xxxx (휴대폰번호)" />
          </div>
        </div>

        <div className={styles.signUp__form__oauth}>
          <div>
            <a href="http://localhost:8080/login/oauth2/authorize/naver?redirect_uri=http://localhost:3000/oauth2/redirect">
              <img src={naverLogo} alt="React" />
            </a>
          </div>

          <div>
            <a href="http://localhost:8080/login/oauth2/authorize/kakao?redirect_uri=http://localhost:3000/oauth2/redirect">
              <img src={kakaoLogo} alt="React" />
            </a>
          </div>

          <div>
            <a href="http://localhost:8080/login/oauth2/authorize/google?redirect_uri=http://localhost:3000/oauth2/redirect">
              <img src={googleLogo} alt="React" />
            </a>
          </div>
        </div>
        <div className={styles.signUp__form__btn}>
          {!isLoading && <button>회원가입</button>}
          {isLoading && <p>요청중...</p>}
          <Link to="/login">
            <button>취소</button>
          </Link>
        </div>
      </form>
    </div>
  );
};
export default SignUp;
