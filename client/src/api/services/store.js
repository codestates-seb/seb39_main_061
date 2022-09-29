import axios from "axios";
import { baseURL } from "../axios";
import { getAuthorizationHeader } from "../axios";

export const postBusinessInfo = (
  name,
  introduction,
  openTime,
  holiday,
  address,
  phone,
  lon,
  lat
) => {
  return axios.patch(
    `${baseURL}/api/v1/business/1`,
    { name, introduction, openTime, holiday, address, phone, lon, lat },
    {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    }
  );
};

export const getBusinessInfo = () => {
  return axios
    .get(`${baseURL}/api/v1/business`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      console.log("매장정보 조회 성공", res);
    })
    .catch((err) => {
      console.log("매장정보 조회 실패", err);
    });
};
