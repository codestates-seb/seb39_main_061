import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { baseURL } from "../../library/axios";
import styles from "./EmailValidation.module.css";

const EmailValidation = () => {
  const [validation, setValidation] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();
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
      alert("인증 성공!");
      setTimeout(() => {
        navigate("/login");
      }, 3000);
    }
  }, []);

  return (
    <section className={styles.EmailValidation}>
      {validation ? (
        <h1>이메일 인증이 성공하였습니다!</h1>
      ) : (
        <h1>이메일 인증이 실패하였습니다!</h1>
      )}
      {validation && <p>잠시후 로그인 페이지로 이동합니다</p>}
      {!validation && <p>잠시후 회원가입 페이지로 이동합니다</p>}
      {!validation && <button>이메일 다시 보내기</button>}
    </section>
  );
};

export default EmailValidation;
