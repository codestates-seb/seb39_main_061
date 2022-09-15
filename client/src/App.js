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
import { useEffect } from "react";
import { authActions } from "./store/auth.js";
import { getLoginCookie } from "./library/cookie.js";
import EmailValidation from "./pages/EmailValidation/EmailValidation.js";
import axios from "axios";

function App() {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const dispatch = useDispatch();
  // 로그인 유지 - app에서 새로고침 할때마다
  useEffect(() => {
    const accessToken = getLoginCookie();
    console.log(getLoginCookie());

    axios
      .get("http://localhost:8080/api/v1/members/profile", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((res) => {
        dispatch(authActions.login());
        console.log("유저정보 확인");
        console.log("유저정보", res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  return (
    <BrowserRouter>
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
          {isLogin && (
            <Route path="/management" element={<Management />}></Route>
          )}
          {isLogin && (
            <Route
              path="/management-Detail"
              element={<ManagementDetail />}
            ></Route>
          )}
          {isLogin && <Route path="/userPage" element={<UserPage />}></Route>}
          {/* <Route path="*" element={<MainPage />}></Route> */}
          <Route path="/email-validation" element={<EmailValidation />}></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
