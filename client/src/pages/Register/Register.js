import { useEffect } from "react";
import { useRef } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { authActions } from "../../store/auth";
import styles from "./Register.module.css";
import { getProfile } from "../../library/axios";
import { userAction } from "../../store/user";
import { oauthReq } from "../../library/axios";

const Register = () => {
  const BusinessCategoryRef = useRef();
  const businessNameRef = useRef();
  const phoneNumRef = useRef();
  const nameRef = useRef();

  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const oauthValidation = location.search.includes("true");
  const accessToken = String(location.search.split("&", 1));
  const token = accessToken.substring(13);
  const pageTitle = "페이지제목";
  window.history.pushState("", pageTitle, `/oauth2`);

  // 인증 됐으면 바로 로그인 -> 유저데이터 받아오기 -> 대시보드
  const checkValidation = async () => {
    if (oauthValidation === true) {
      localStorage.setItem("token", token);
      const userData = await getProfile();
      dispatch(userAction.setUser(userData));
      dispatch(authActions.login());
      navigate("/dashboard");
    }
  };
  useEffect(() => {
    localStorage.setItem("token", token);
    checkValidation();
  }, []);

  //인증 false -> 추가 기입 전송 -> 로그인,유저데이터 받아오기
  const handlerSubmit = async () => {
    const sectorId = BusinessCategoryRef.current.value;
    const businessName = businessNameRef.current.value;
    const phone = phoneNumRef.current.value;
    const name = nameRef.current.value;
    alert("전송 요청!");
    const newToken = await oauthReq(sectorId, businessName, phone, name);
    localStorage.setItem("token", newToken);
    dispatch(authActions.login());
    const userData = await getProfile();
    dispatch(userAction.setUser(userData));
    navigate("/dashboard");
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
