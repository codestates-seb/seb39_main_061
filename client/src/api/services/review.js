import { userAxiosInstance } from "./instance";
import { adminAxiosInstance } from "./instance";
import { baseURL } from "../axios";
import axios from "axios";

export const getRevUserList = (businessId) => {
  return axios.get(`${baseURL}/business/${businessId}/review?page=1&size=110`);
};

export const registerUserRev = (businessId, contents, score) => {
  return userAxiosInstance({
    method: "POST",
    url: `/business/${businessId}/review`,
    data: {
      businessId,
      contents,
      score: 5,
    },
  });
};

export const getRevAdminList = (businessId) => {
  return adminAxiosInstance.get(
    `business/${businessId}/review?page=1&size=110`
  );
};
