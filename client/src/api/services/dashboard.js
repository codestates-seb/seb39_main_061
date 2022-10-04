import axios from "axios";
import { getAuthorizationHeader } from "../axios";
import { baseURL } from "../axios";

export const getDashboard = (today) => {
  return axios
    .get(`${baseURL}/api/v1/business/1/reservation/qr-code/1/statistics?date=${today}`, {
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
