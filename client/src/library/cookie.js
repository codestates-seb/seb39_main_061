import { Cookies } from "react-cookie";

const cookies = new Cookies();

export const setLoginCookie = (token) => {
  let expireTime = new Date();
  expireTime.setMinutes(new Date().getMinutes() + 10);
  return cookies.set("token", token, { path: "/", expires: expireTime });
};

export const getLoginCookie = () => {
  return cookies.get("token");
};

export const deleteCookie = () => {
  return cookies.remove("token");
};
