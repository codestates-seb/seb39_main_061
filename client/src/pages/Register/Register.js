import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { getLoginCookie } from "../../library/cookie";
import { authActions } from "../../store/auth";
import { userAction } from "../../store/user";

const Register = () => {
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
    <div>
      <h1>추가정보 등록 페이지</h1>
      <p>{location.search}</p>
    </div>
  );
};

export default Register;
