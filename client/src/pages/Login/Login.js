import React, { useRef } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import { userAction } from "../../store/user";
import styles from "./Login.module.css";
import { getProfile, login } from "../../library/axios";
import googleLogo from "../../assets/google-logo.png";
import naverLogo from "../../assets/naver-logo.png";
import kakaoLogo from "../../assets/kakao-logo.png";
import { useState } from "react";
import mainLogo from "../../assets/logo1.png";

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const emailRef = useRef();
  const PWRef = useRef();
  const [validationMSG, setValidationMSG] = useState("");

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
    const email = emailRef.current.value;
    const password = PWRef.current.value;
    checkValidation(email);
    if (checkValidation(email) === false) {
      return;
    }
    // if (validationMSG.length === 0) {
    //   return;
    // }

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
      console.log(token);
      localStorage.setItem("token", token);
      dispatch(authActions.login());
      //getProfile
      const userData = await getProfile();
      dispatch(userAction.setUser(userData));
      navigate("/dashboard");
    }
  };

  return (
    <section className={styles.login}>
      <form onSubmit={loginSubmitHandler} className={styles.login__form}>
        <div className={styles.login__form__title}>
          <img src={mainLogo} alt="React" />
          {/* <h1>Login</h1> */}
          <p>{validationMSG}</p>
        </div>
        <div className={styles.login__form__input}>
          <input ref={emailRef} placeholder="이메일" />
          <input type="password" ref={PWRef} placeholder="비밀번호" />
        </div>

        <div className={styles.login__form__oauth}>
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
        <div className={styles.login__form__loginBtn}>
          <button> Log in</button>
        </div>
        <div className={styles.login__form__findPassword}>
          <Link to="/signup">
            <p>회원가입</p>
          </Link>
          <Link to="/find-password">
            <p>아이디/비밀번호 찾기</p>
          </Link>
        </div>
      </form>
    </section>
  );
};
export default Login;
