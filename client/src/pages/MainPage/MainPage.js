import { useSelector } from "react-redux";
import { Link, Router } from "react-router-dom";
import React from "react";

const MainPage = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  return (
    <div>
      <h1>Main Page</h1>
      {!isLogin && (
        <Link to="/login">
          <button>Login</button>
        </Link>
      )}
      {isLogin && (
        <Link to="/dashboard">
          <button>대시보드</button>
        </Link>
      )}
    </div>
  );
};
export default MainPage;
