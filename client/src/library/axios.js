import axios from "axios";
import jwt_decode from "jwt-decode";
import dayjs from "dayjs";

const baseURL = "http://localhost:8080";

let authToken = localStorage.getItem("token");

const axiosInstance = axios.create({
  baseURL,
  headers: { Authorization: `Bearer ${authToken}` },
});

axiosInstance.interceptors.request.use(async (req) => {
  if (!authToken) {
    authToken = localStorage.getItem("token");
    req.headers.Authorization = `Bearer ${authToken}`;
  }

  const user = jwt_decode(authToken);
  const isExpired = dayjs.unix(user.exp).diff(dayjs()) < 1;
  console.log("isExpired: ", isExpired);
  if (!isExpired) return req;

  const response = await axios.post(`${baseURL}/auth/reissue`, {
    accessToken: authToken,
  });

  localStorage.setItem("token", JSON.stringify(response.data.accessToken));
  req.headers.Authorization = `Bearer ${authToken}`;
  return req;
});

export const getProfile = async () => {
  const responose = await axiosInstance.get("/api/v1/members/profile");
};

export default axiosInstance;
