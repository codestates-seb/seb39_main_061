import { userAxiosInstance } from "./instance";
import axios from "axios";
import { baseURL } from "../axios";

export const getUserResList = (businessId, qrCodeId) => {
  return axios.get(
    `${baseURL}/business/${businessId}/reservation/qr-code/${qrCodeId}?page=1&size=10`
  );
};

export const registerUserRes = (businessId, qrCodeId, name, phone, count) => {
  return userAxiosInstance({
    method: "POST",
    url: `${baseURL}/business/${businessId}/reservation/qr-code/${qrCodeId}`,
    data: {
      businessId,
      qrCodeId,
      name,
      phone,
      count,
    },
  });
};

export const getUserStoreInfo = (businessId) => {
  return userAxiosInstance.get(`/business/${businessId}`);
};

export const getUserFoodList = (businessId) => {
  return axios.get(`${baseURL}/business/${businessId}/menu?page=1&size=10`);
};
