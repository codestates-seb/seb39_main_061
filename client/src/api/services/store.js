import axios from "axios";
import { baseURL } from "../axios";
import { getAuthorizationHeader } from "../axios";

export const PostBusinessInfo = (
  introduction,
  openTime,
  holiday,
  address,
  name,
  lon,
  lat,
  phone
) => {
  return axios.post(`${baseURL}/api/v1/business`, {
    headers: {
      Authorization: getAuthorizationHeader(),
    },
  });
};
