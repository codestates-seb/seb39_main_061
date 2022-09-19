import axios from "axios";
import jwt_decode from "jwt-decode";
import dayjs from "dayjs";
import { authActions } from "../store/auth";

const baseURL = "http://localhost:8080";

export const getToken = () => localStorage.getItem("token");

export const getAuthorizationHeader = () => `Bearer ${getToken()}`;

export const axiosInstance = axios.create({
  baseURL,
  headers: { Authorization: getAuthorizationHeader() },
});

export const login = (email, password) => {
  return axios
    .post(`${baseURL}/auth/login`, {
      email,
      password,
    })
    .then((res) => {
      console.log("로그인 데이터는?", res.data.data.accessToken);
      return res.data.data.accessToken;
    })
    .catch((err) => {
      alert(err.response.data.message);
    });
};

export const getProfile = () => {
  return axios
    .get("http://localhost:8080/api/v1/members/profile", {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      return res.data.data;
    })
    .catch((err) => {
      console.log(err.response);
    });
};

export default axiosInstance;
