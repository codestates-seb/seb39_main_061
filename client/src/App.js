import { Route, Routes, BrowserRouter } from "react-router-dom";
import Login from "./pages/Login/Login.js";
import SignUp from "./pages/SignUp/SignUp";
import Dashboard from "./pages/Dashboard/Dashboard";
import Profile from "./pages/Profile/Profile";
import Management from "./pages/Management/Management";
import ManagementDetail from "./pages/ManagementDetail/ManagementDetail";
import UserPage from "./pages/UserPage/UserPage";
import MainPage from "./pages/MainPage/MainPage";
import CreateCode from "./pages/CreateCode/CreateCode.js";
import FindPassword from "./pages/FindPassword/FindPassword.js";
import { useSelector, useDispatch } from "react-redux";
import { authActions } from "./store/auth.js";
import EmailValidation from "./pages/EmailValidation/EmailValidation.js";
import Register from "./pages/Register/Register.js";
import { getProfile } from "./library/axios.js";
import { useEffect } from "react";
import { userAction } from "./store/user.js";
import "./app.css";

function App() {
  const url = process.env.REACT_APP_BASE_URL;
  console.log("베이스유알엘", url);
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const dispatch = useDispatch();

  // 유저 정보로 새로고침해도 로그인 유지
  useEffect(() => {
    if (localStorage.getItem("token") && isLogin === true) {
      getProfile()
        .then((res) => {
          console.log("로그인 유지 성공!");
          dispatch(userAction.setUser(res));
        })
        .catch((err) => {
          console.log("로그인 유지 실패");
        });
    }
  }, []);

  return (
    <div className="App">
      <Routes>
        {!isLogin && <Route path="/" element={<MainPage />}></Route>}
        {!isLogin && <Route path="/signup" element={<SignUp />}></Route>}
        {!isLogin && <Route path="/login" element={<Login />}></Route>}
        {!isLogin && (
          <Route path="/find-password" element={<FindPassword />}></Route>
        )}
        {isLogin && <Route path="/dashboard" element={<Dashboard />}></Route>}
        {isLogin && <Route path="/profile" element={<Profile />}></Route>}
        {isLogin && (
          <Route path="/create-code" element={<CreateCode />}></Route>
        )}
        {isLogin && <Route path="/management" element={<Management />}></Route>}
        {isLogin && (
          <Route
            path="/management-Detail"
            element={<ManagementDetail />}
          ></Route>
        )}
        {isLogin && <Route path="/userPage" element={<UserPage />}></Route>}
        <Route path="*" element={<MainPage />}></Route>
        <Route path="/email-validation" element={<EmailValidation />}></Route>
        <Route path="/oauth2/redirect" element={<Register />}></Route>
      </Routes>
    </div>
  );
}

export default App;
