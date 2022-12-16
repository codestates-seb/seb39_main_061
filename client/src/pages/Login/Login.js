import React, { useRef } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import { userAction } from "../../store/user";
import styles from "./Login.module.css";
import googleLogo from "../../assets/google-logo.png";
import naverLogo from "../../assets/naver-logo.png";
import kakaoLogo from "../../assets/kakao-logo.png";
import { useState } from "react";
import mainLogo from "../../assets/logo2.png";
import Modal from "../../components/Modal/Modal";
import { useEffect } from "react";
import { getProfile } from "../../api/services/user";
import { login } from "../../api/services/auth";
import { baseURL, clientURL } from "../../api/axios";

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const emailRef = useRef();
  const PWRef = useRef();
  const [validationMSG, setValidationMSG] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [changeCSS, setChangeCSS] = useState(false);
  useEffect(() => {
    setChangeCSS(true);
  }, [changeCSS]);

  const checkValidation = () => {
    const email = emailRef.current.value;
    const password = PWRef.current.value;
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

  const loginSubmitHandler = async (e) => {
    e.preventDefault();
    setChangeCSS(false);
    const email = emailRef.current.value;
    const password = PWRef.current.value;
    checkValidation(email);
    if (checkValidation(email) === false) {
      return;
    }
    //login

    const token = await login(email, password).catch((err) => {
      console.log(err.response.data.message);
      if (err.response.data.message === "MEMBER NOT FOUND") {
        setValidationMSG("이메일이 존재하지 않습니다.");
      }
      if (err.response.data.message === "LOGIN INFO IS INCORRECT") {
        setValidationMSG("비밀번호가 일치하지 않습니다.");
      }
      if (err.response.data.message === "EMAIL VALIDATION IS NEED") {
        setValidationMSG("인증이 필요합니다 이메일을 확인해주세요.");
      }
    });
    if (token) {
      setValidationMSG("");
      console.log(token);
      localStorage.setItem("token", token);
      setIsModalOpen(true);

      //getProfile
      const userData = await getProfile();
      dispatch(userAction.setUser(userData));

      setTimeout(() => {
        dispatch(authActions.login());
        navigate("/dashboard");
      }, 1500);
    }
  };

  return (
    <div className={styles.login}>
      <form onSubmit={loginSubmitHandler} className={styles.login__form}>
        <div className={styles.login__form__title}>
          <img src={mainLogo} alt="React" />
          {/* <h1>Login</h1> */}
        </div>
        <div className={styles.register__form__validation}>
          {changeCSS === false ? (
            <p>{validationMSG}</p>
          ) : (
            <p className={styles.shake}>{validationMSG}</p>
          )}
        </div>
        <div className={styles.login__form__input}>
          <input ref={emailRef} placeholder="이메일" />
          <input
            maxLength={16}
            type="password"
            ref={PWRef}
            placeholder="비밀번호"
          />
        </div>

        <div className={styles.login__form__oauth}>
          <div>
            <a href="#!">
              <img src={naverLogo} alt="naverLogin" />
            </a>
          </div>

          <div>
            <a
              href={`${baseURL}/login/oauth2/authorize/kakao?redirect_uri=${clientURL}/oauth2/redirect`}
            >
              <img src={kakaoLogo} alt="kakaoLogin" />
            </a>
          </div>
          <div>
            <a
              href={`${baseURL}/login/oauth2/authorize/google?redirect_uri=${clientURL}/oauth2/redirect`}
            >
              <img src={googleLogo} alt="googleLogin" />
            </a>
          </div>
        </div>
        <div className={styles.login__form__loginBtn}>
          <button> Log in</button>
        </div>
        <div className={styles.login__form__findPassword}>
          <Link to="/signup">
            <p>회원가입</p>
          </Link>
          <Link to="/find-password">
            <p>비밀번호 찾기</p>
          </Link>
        </div>
      </form>
      {isModalOpen && <Modal num={0} />}
    </div>
  );
};
export default Login;
