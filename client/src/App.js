import { Route, Routes } from "react-router-dom";
import { useSelector } from "react-redux";
import "./app.css";
import React from "react";

import Login from "./pages/Login/Login.js";
import SignUp from "./pages/SignUp/SignUp.jsx";
import Dashboard from "./pages/Dashboard/Dashboard";
import Profile from "./pages/Profile/Profile";
import CreateCode from "./pages/CreateCode/CreateCode";
import UserPage from "./pages/UserPage/UserPage";
import MainPage from "./pages/MainPage/MainPage";
import FindPassword from "./pages/FindPassword/FindPassword";
import EmailValidation from "./pages/EmailValidation/EmailValidation";
import StoreManagement from "./pages/StoreManagement/StoreManagement";
import Register from "./pages/Register/Register.js";
import ReservationAdmin from "./pages/Reservation/ReservationAdmin/ReservationAdmin";
import ReservationUser from "./pages/Reservation/ReservationUser/ReservationUser";
import ReviewUser from "./pages/Review/ReviewUser/ReviewUser";
import ReviewAdmin from "./pages/Review/ReviewAdmin/ReviewAdmin";

function App() {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);

  return (
    <div className="App">
      <Routes>
        {!isLogin && (
          <>
            <Route path="/" element={<MainPage />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<Login />} />
            <Route path="/find-password" element={<FindPassword />} />
          </>
        )}
        {isLogin && (
          <>
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/qrcode-management" element={<CreateCode />} />
            <Route path="/reservation-admin" element={<ReservationAdmin />} />
            <Route path="/review-admin" element={<ReviewAdmin />} />
            <Route path="/userPage" element={<UserPage />} />
            <Route path="store-management" element={<StoreManagement />} />
          </>
        )}
        <Route path="/business/:id/qr-code/:id" element={<ReservationUser />} />
        <Route
          path="/review/business/:id/qr-code/:id"
          element={<ReviewUser />}
        />
        <Route path="*" element={<MainPage />} />
        <Route path="/email-validation" element={<EmailValidation />} />
        <Route path="/oauth2/redirect" element={<Register />} />
      </Routes>
    </div>
  );
}

export default App;
