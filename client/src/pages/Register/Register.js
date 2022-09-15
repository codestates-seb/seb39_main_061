import axios from "axios";
import { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { getLoginCookie } from "../../library/cookie";
import { authActions } from "../../store/auth";
import { userAction } from "../../store/user";
import styles from "./Register.module.css";

const Register = () => {
  const BusinessCategoryRef = useRef();
  const businessNameRef = useRef();
  const phoneNumRef = useRef();
  const nameRef = useRef();

  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLogin = useSelector((state) => state.auth.isAuthenticated);

  // 인증 안됐으면 true
  const validationIsFalse = location.search.includes("false");

  const accessToken = String(location.search.split("&", 1));
  const token = accessToken.substring(13);

  console.log(token);
  console.log("이메일 인증 안됌 ? = ", validationIsFalse);

  const handlerSubmit = (e) => {
    e.preventDefalut();
    axios
      .patch(
        "http://localhost:8080/auth/members",
        {
          service: "reservation",
          sectorId: BusinessCategoryRef.current.value,
          businessName: businessNameRef.current.value,
          phone: phoneNumRef.current.value,
          name: nameRef.current.value,
        },
        { Authorization: `Bearer ${token}` }
      )
      .then((res) => {
        console.log(res.data);
        navigate("/dashboard");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 인증이 됐으면 로그인하기
  if (validationIsFalse === false) {
    //이메일 인증이 true면
    // 1. 로그인 상태 true로
    // 2. 토큰으로 로그인
    // 2. 유저정보 받아서 리덕스 상태 바꾸기
    axios
      .get("http://localhost:8080/api/v1/members/profile", {
        headers: {
          Authorization: `Bearer ${getLoginCookie("token")}`,
        },
      })
      .then((res) => {
        dispatch(authActions.login());
        console.log("로그인상태는? ", isLogin);
        navigate("/dashboard");
      })
      .catch((err) => {});
  }

  return (
    <div className={styles.register}>
      <h1>추가정보 등록 페이지</h1>
      <p>{location.search}</p>

      <form className={styles.register__form} onSubmit={handlerSubmit}>
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
        <button>전송</button>
      </form>
    </div>
  );
};

export default Register;
