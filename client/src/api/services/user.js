import axios from "axios";
import { getAuthorizationHeader } from "../axios";
import { baseURL } from "../axios";

export const getProfile = () => {
  return axios
    .get(`${baseURL}/api/v1/members/profile`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      // console.log("겟프로필");
      return res.data.data;
    })
};
