import axios from "axios";
import { baseURL } from "../axios";
import { getAuthorizationHeader } from "../axios";

export const postBusinessInfo = (form) => {
  return axios.patch(`${baseURL}/api/v1/business`, form, {
    headers: {
      Authorization: getAuthorizationHeader(),
    },
  });
};

export const getBusinessInfo = () => {
  return axios.get(`${baseURL}/api/v1/business`, {
    headers: {
      Authorization: getAuthorizationHeader(),
    },
  });
};
