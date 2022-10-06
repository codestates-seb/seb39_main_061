import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { baseURL } from "../../api/axios";
import styles from "./EmailValidation.module.css";
import mainLogo from "../../assets/logo2.png";

const EmailValidation = () => {
  const [validation, setValidation] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();
  console.log(location.search);
  let email = String(location.search.split("&", 1));
  email = email.slice(7);

  useEffect(() => {
    const res = axios
      .get(`${baseURL}/auth/validation${location.search}`)
      .catch((err) => {
        setValidation(false);
        if (err.response) {
          const errMsg = err.response.data;
          console.log(errMsg);
        }
      });
    if (res) {
      setValidation(true);
      setTimeout(() => {
        navigate("/login");
      }, 3000);
    }
  }, []);

  return (
    <section className={styles.EmailValidation}>
      <div className={styles.imageBox}>
<<<<<<< HEAD
        <img alt="main-logo" src={mainLogo} />
=======
        <img src={mainLogo} />
>>>>>>> 64171d5243ed564510bc016419b5df708da78724
      </div>
      <div>
        {validation ? (
          <h1>
            <p>{email}님의 회원가입을 축하합니다</p>
          </h1>
        ) : (
          <h1>이메일 인증이 실패하였습니다</h1>
        )}
        {validation && <p>잠시후 로그인 페이지로 이동합니다</p>}
        {!validation && <p>다시 시도해주세요 회원가입 페이지로 이동합니다 </p>}
      </div>
    </section>
  );
};

export default EmailValidation;
