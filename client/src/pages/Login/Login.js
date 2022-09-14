import axios from "axios";
import React, { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import styles from "./Login.module.css";
import { getLoginCookie, setLoginCookie } from "../../library/cookie";

const Login = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const token = useSelector((state) => state.auth.token);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const emailRef = useRef();
  const PWRef = useRef();
  const loginSubmitHandler = (e) => {
    e.preventDefault();
    axios
      .post(
        "http://211.104.147.150:8080/auth/login",
        {
          email: emailRef.current.value,
          password: PWRef.current.value,
        },
        { withCredentials: true }
      )
      .then((res) => {
        //...
        console.log(res.data);
        console.log(res);
        setLoginCookie(res.data.data.accessToken);
        dispatch(authActions.login());
        console.log("로그인 성공");
        navigate("/dashboard");
      })
      .catch((err) => {
        if (err.response) {
          console.log(err.response.data);
          const errorMessage = err.response.data.message;
          alert(errorMessage);
        }
      });
  };
  return (
    <section className={styles.login}>
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
