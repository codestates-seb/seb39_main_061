import axios from "axios";
import React, { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import { userAction } from "../../store/user";
import styles from "./Login.module.css";
import { getLoginCookie, setLoginCookie } from "../../library/cookie";
import { cookies } from "react-cookie";
import { getProfile } from "../../library/axios";

const Login = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const token = useSelector((state) => state.auth.token);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const emailRef = useRef();
  const PWRef = useRef();
  const loginSubmitHandler = async (e) => {
    e.preventDefault();
    await axios
      .post(
        "http://localhost:8080/auth/login",
        {
          email: emailRef.current.value,
          password: PWRef.current.value,
        },
        { withCredentials: true }
      )
      .then((res) => {
        console.log("로그인 성공!", res.data);

        setLoginCookie(res.data.data.accessToken);
        dispatch(authActions.login());

        navigate("/dashboard");
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response.data);
          const errorMessage = err.response.data.message;
          alert(errorMessage);
        }
      });

    // 유저정보 가져오기
    await axios
      .get("http://localhost:8080/api/v1/members/profile", {
        headers: {
          Authorization: `Bearer ${getLoginCookie("token")}`,
        },
      })
      .then((res) => {
        dispatch(userAction.setUser(res.data.data));
        navigate("/dashboard");
        console.log("유저정보", res.data);
      })
      .catch((err) => {
        console.log(err);
      });
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
