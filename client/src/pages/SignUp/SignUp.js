import { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styles from "./SignUp.module.css";
import axios from "axios";
import baseURL from "../../library/axios";

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
  const [errMessage, setErrMessage] = useState("");
  const [emailCheckMsg, setEmailCheckMsg] = useState("");

  const SignUpHandler = (event) => {
    event.preventDefault();
    const email = emailRef.current.value;
    const password = PWRef.current.value;
    const confirmPassword = confirmPWRef.current.value;
    const name = OwenerNameRef.current.value;
    const businessName = businessNameRef.current.value;
    const phone = phoneNumRef.current.value;
    const sectorId = BusinessCategoryRef.current.value;
    console.log(sectorId);
    if (password !== confirmPassword) {
      setErrMessage("비밀번호가 일치하지 않습니다.");
      alert(errMessage);
      return;
    }
    setIsLoading(true);
    axios
      .post("http://211.104.147.150:8080/auth/signup", {
        email: email,
        password: password,
        name: name,
        businessName: businessName,
        phone: phone,
        role: "reservation",
        sectorId: sectorId,
      })
      .then((res) => {
        setIsLoading(false);
        console.log(res);
        navigate("/login");
      })
      .catch((err) => {
        setIsLoading(false);
        if (err.response) {
          const data = err.response.data;
          let errorMessage = "Authentication failed!";
          if (data && data.error && data.error.message) {
            errorMessage = data.error.message;
          }
          // 서버에서 보내는 유효성 검사 에러
          alert(errorMessage);
        }
      });
  };

  const emailCheckHandler = (e) => {
    e.preventDefault();
    console.log("중복검사");
    axios
      .post("http://211.104.147.150:8080/auth/validation", {
        email: emailRef.current.value,
      })
      .then((res) => {
        console.log("이메일 체크");
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
        if (err.response) {
          //
        }
        // 서버에서 보내는 유효성 검사 에러
        // alert();
      });
  };

  return (
    <div className={styles.SignUp}>
      <h1>SignUp Page</h1>
      <form onSubmit={SignUpHandler} className={styles.SignUp__form}>
        <div>
          <input ref={emailRef} placeholder="이메일" />
          <button onClick={emailCheckHandler}>중복확인</button>
          <p>{emailCheckMsg}</p>
        </div>
        <div>
          <input type="password" ref={PWRef} placeholder="비밀번호" />
          <input
            type="password"
            ref={confirmPWRef}
            placeholder="비밀번호 확인"
          />
          <p>{errMessage}</p>
        </div>

        <input ref={OwenerNameRef} placeholder="대표 성명" />
        <input ref={phoneNumRef} placeholder="연락처" />
        <input ref={businessNameRef} placeholder="상호명" />
        <select ref={BusinessCategoryRef}>
          <option value={0}>업종을 선택하세요</option>
          <option value={1}>농업, 임업 및 어업</option>
          <option value={2}>전기, 가스 및 수도사업</option>
          <option value={3}>하수·폐기물 처리, 원료재생 및 환경복원업</option>
          <option value={4}>건설업</option>
          <option value={5}>숙박 및 음식점업</option>
          <option value={6}>출판, 영상, 방송통신 및 정보서비스업</option>
          <option value={7}>금융 및 보험업</option>
          <option value={8}>부동산 및 임대업</option>
          <option value={9}>전문, 과학 및 기술 서비스업</option>
          <option value={10}>공공행정, 국방 및 사회보장 행정</option>
          <option value={11}>교육 서비스업</option>
          <option value={12}>보건 및 사회복지사업</option>
          <option value={13}>예술, 스포츠 및 여가관련 서비스업</option>
          <option value={14}>협회 및 단체, 수리 및 기타 개인서비스업</option>
          <option value={15}>기타</option>
        </select>
        {!isLoading && <button>회원가입</button>}
        {isLoading && <p>요청중...</p>}
      </form>
    </div>
  );
};
export default SignUp;
