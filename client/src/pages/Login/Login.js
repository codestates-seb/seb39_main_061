import React, { useRef } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import { userAction } from "../../store/user";
import styles from "./Login.module.css";
import { getProfile, login } from "../../library/axios";

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const emailRef = useRef();
  const PWRef = useRef();

  const loginSubmitHandler = async (e) => {
    const email = emailRef.current.value;
    const password = PWRef.current.value;
    e.preventDefault();
    //login
    const token = await login(email, password);
    if (token) {
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
      <p>
        <a href="http://localhost:8080/login/oauth2/authorize/naver?redirect_uri=http://localhost:3000/oauth2/redirect">
          네이버
        </a>
      </p>
      <p>
        <a href="http://localhost:8080/login/oauth2/authorize/kakao?redirect_uri=http://localhost:3000/oauth2/redirect">
          카카오
        </a>
      </p>
      <p>
        <a href="http://localhost:8080/login/oauth2/authorize/google?redirect_uri=http://localhost:3000/oauth2/redirect">
          구글
        </a>
      </p>
      <div>
        <h1>Login</h1>
      </div>
      <form onSubmit={loginSubmitHandler} className={styles.login__form}>
        <input ref={emailRef} placeholder="Email" />
        <input type="password" ref={PWRef} placeholder="Password" />
        <div>
          <button>로그인</button>
          <Link to="/signup">
            <button>회원가입</button>
          </Link>
        </div>
        <Link to="/find-password">
          <p>비밀번호 찾기</p>
        </Link>
      </form>
    </section>
  );
};
export default Login;
