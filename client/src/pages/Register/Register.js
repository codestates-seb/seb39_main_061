import { useEffect } from "react";
import { useRef } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import styles from "./Register.module.css";
import axios from "axios";
import { getProfile } from "../../library/axios";
import { userAction } from "../../store/user";

const Register = () => {
  const url = process.env.REACT_APP_BASE_URL;
  const BusinessCategoryRef = useRef();
  const businessNameRef = useRef();
  const phoneNumRef = useRef();
  const nameRef = useRef();

  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // 인증 안됐으면 true
  const oauthValidation = location.search.includes("true");

  const accessToken = String(location.search.split("&", 1));
  const token = accessToken.substring(13);
  const pageTitle = "페이지제목";
  window.history.pushState("", pageTitle, `/oauth2`);
  const checkValidation = () => {
    if (oauthValidation === true) {
      // 이메일 인증이 true면
      // 1. 로그인 상태 true로
      // 2. 토큰을 로컬스토리지에 덮어쓰기
      // 2. 유저정보 받아서 리덕스 상태 바꾸기
      console.log("로그인 성공!");
      localStorage.setItem("token", token);
      getProfile().then((data) => {
        console.log("겟프로필 받아옴", data);
        dispatch(userAction.setUser(data));
        dispatch(authActions.login());
      });
    }
  };

  useEffect(() => {
    localStorage.setItem("token", token);
    checkValidation();
    navigate("/dashboard");
  }, []);

  const oauthReq = async () => {
    try {
      let response = await axios.patch(
        `${url}/auth/members`,
        {
          service: "reservation",
          sectorId: BusinessCategoryRef.current.value,
          businessName: businessNameRef.current.value,
          phone: phoneNumRef.current.value,
          name: nameRef.current.value,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      if (response.status === 200) {
        console.log("새로응답받은 토큰?", response.data.data.accessToken);
        const newToken = response.data.accessToken;
        localStorage.setItem("token", newToken);

        dispatch(authActions.login());
        navigate("/dashboard");
      }
    } catch (err) {
      console.log(err);
      alert("요청에 실패하였습니다 다시 요청해주세요");
      // navigate("/");
    }
  };

  const handlerSubmit = async () => {
    console.log("인증안된 소셜 토큰:", token);
    alert("전송 요청!");
    await oauthReq();
  };

  return (
    <div className={styles.register}>
      <h1>추가정보 등록 페이지</h1>
      {/* <p>{location.search}</p> */}

      <div className={styles.register__form}>
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
        <input ref={businessNameRef} placeholder="상호명" />
        <input ref={phoneNumRef} placeholder="전화번호" />
        <input ref={nameRef} placeholder="이름" />
        <button onClick={handlerSubmit}>전송</button>
      </div>
    </div>
  );
};

export default Register;
