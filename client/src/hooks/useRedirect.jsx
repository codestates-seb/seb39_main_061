import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

export const useRedirect = () => {
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    const isToken = localStorage.getItem("token");
    console.log(isToken);
    if (isToken) setIsLogin(true);
    else setIsLogin(false);
  }, []);
  return [isLogin];
};
