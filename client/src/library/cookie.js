import { Cookies } from "react-cookie";

const cookies = new Cookies();

// 토큰 만료시간 되면 사라짐

export const setLoginCookie = (token) => {
  let expireTime = new Date();
  expireTime.setMinutes(new Date().getMinutes() + 10);
  return cookies.set("token", token, { path: "/" });
};

// // 토큰 계속 남아있음

// export const setLoginCookie = (token) => {
//   return cookies.set("token");
// };

export const getLoginCookie = (name: string) => {
  return cookies.get(name);
};

export const deleteCookie = (name: string) => {
  return cookies.remove(name);
};
