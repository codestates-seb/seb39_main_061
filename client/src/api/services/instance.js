import axios from "axios";

const BASE_URL = "http://localhost:8080";
const getToken = () => localStorage.getItem("token");
const getAuthorizationHeader = () => `Bearer ${getToken()}`;

export const userAxiosInstance = axios.create({
  baseURL: BASE_URL,
});

export const adminAxiosInstance = axios.create({
  baseURL: `${BASE_URL}/api/v1`,
  headers: { Authorization: getAuthorizationHeader() },
});
