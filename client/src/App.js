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
import axiosInstance from "./library/axios.js";
import { useEffect } from "react";

function App() {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const userData = useSelector((state) => state.user.userProfile);
  const dispatch = useDispatch();

  // 유저 정보로 새로고침해도 로그인 유지
  const getProfile = async () => {
    try {
      let responose = await axiosInstance.get("/api/v1/members/profile");
      if (responose.status === 200) {
        console.log("로그인유지!");
        dispatch(authActions.login());
      }
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    if (localStorage.getItem("token")) {
      getProfile();
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
