import axios from "axios";

export const baseURL = process.env.REACT_APP_BASE_URL;
export const clientURL = process.env.REACT_APP_CLIENT_URL;
export const imgURL = process.env.REACT_APP_IMAGE_URL;

export const getToken = () => localStorage.getItem("token");

export const getAuthorizationHeader = () => `Bearer ${getToken()}`;

export const axiosInstance = axios.create({
  baseURL,
  headers: { Authorization: getAuthorizationHeader() },
});

export default axiosInstance;
