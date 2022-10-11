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
export const deleteUserRes = (
  businessId,
  qrCodeId,
  reservationId,
  phone,
  name,
  count
) => {
  return axios.patch(
    `${baseURL}/business/${businessId}/reservation/qr-code/${qrCodeId}/cancel/${reservationId}`,
    {
      businessId,
      qrCodeId,
      reservationId,
      phone,
      count,
      name,
    }
  );
};

export const getUserStoreInfo = (businessId) => {
  return userAxiosInstance.get(`/business/${businessId}`);
};

export const getUserFoodList = (businessId, pageNum) => {
  return axios.get(
    `${baseURL}/business/${businessId}/menu?page=${pageNum}&size=6`
  );
};
