import axios from "axios";
import { adminAxiosInstance } from "./instance";
import { baseURL, getAuthorizationHeader } from "../axios";

export const getAdminResList = (businessId, qrId) => {
  return axios.get(
    `${baseURL}/business/${businessId}/reservation/qr-code/${qrId}?page=1&size=10`,
    {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    }
  );
};

export const deleteAdminRes = (businessId, qrId, reservationId) => {
  return adminAxiosInstance({
    method: "PATCH",
    url: `/business/${businessId}/reservation/qr-code/${qrId}/info/${reservationId}/cancel`,
  });
};

export const getBusInfo = () => {
  return adminAxiosInstance.get(`/business`);
};

export const getResNotification = (businessId, qrId, reservationId) => {
  return adminAxiosInstance({
    method: "PATCH",
    url: `/business/${businessId}/reservation/qr-code/${qrId}/info/${reservationId}/enter`,
  });
};
