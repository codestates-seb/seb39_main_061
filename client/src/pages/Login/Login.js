import axios from "axios";
import React, { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import { userAction } from "../../store/user";
import styles from "./Login.module.css";
import axiosInstance from "../../library/axios";

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const emailRef = useRef();
  const PWRef = useRef();

  const getProfile = async () => {
    try {
      let responose = await axiosInstance.get("/api/v1/members/profile");
      if (responose.status === 200) {
        dispatch(userAction.setUser(responose.data.data));
      }
    } catch (err) {
      console.log(err);
    }
  };
  const loginReq = async () => {
    try {
      let res = await axios.post("http://localhost:8080/auth/login", {
        email: emailRef.current.value,
        password: PWRef.current.value,
      });
      if (res.status === 200) {
        localStorage.setItem("token", res.data.data.accessToken);
        dispatch(authActions.login());
        console.log("로그인");
        navigate("/dashboard");
      }
    } catch (err) {
      if (err.response) {
        const errorMessage = err.response.data.message;
        alert(errorMessage);
      }
    }
  };

  const loginSubmitHandler = async (e) => {
    e.preventDefault();
    await loginReq();
    await getProfile();
    setTimeout(() => {
      localStorage.setItem(
        "token",
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqaGQ3MjkyQGdtYWlsLmNvbSIsInJvbGUiOiJST0xFX1JFU0VSVkFUSU9OIiwiaWF0IjoxNjYzMjQ2MTk4LCJleHAiOjE2NjMyNDk3OTh9.9IMub88llbKuUO70SSK6EQERqWEfU0QK7CPMORzasQGRSWEpJSSGa4KMUxZAKlE93VvStXrF6G08RX07_DJIVQ"
      );
    }, 3000);
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
