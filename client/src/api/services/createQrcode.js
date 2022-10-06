import axios from "axios";
import { getAuthorizationHeader } from "../axios";
import { baseURL } from "../axios";


export const postCreateQRCode = (body) => {
  return axios
    .post(`${baseURL}/api/v1/business/1/type/reservation/qr-code`,
      body,
      {
        headers: {
          Authorization: getAuthorizationHeader(),
        },
      })
    .then((res) => {
      return res.data;
    })
};

export const updateCreateQRCode = (formData, business, qrCodeId) => {
  return axios
    .post(`${baseURL}/api/v1/business/${business}/type/reservation/qr-code/${qrCodeId}/update`,
      formData,
      {
        headers: {
          Authorization: getAuthorizationHeader(),
          "Content-Type": "multipart/form-data",
        },
      })
    .then((res) => {
      return res.data.data;
    })
};

export const getBusinessId = () => {
  return axios
    .get(`${baseURL}/api/v1/business`,
      {
        headers: {
          Authorization: getAuthorizationHeader(),
        },
      })
    .then((res) => {
      return res.data.data;
    })
};
