import axios from "axios";
import { getAuthorizationHeader } from "../axios";
import { baseURL } from "../axios";

export const getDashboard = (businessId, qrcodeId, today) => {
  return axios
    .get(`${baseURL}/api/v1/business/${businessId}/reservation/qr-code/${qrcodeId}/statistics?date=${today}`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      return res.data.data;
    })
};
