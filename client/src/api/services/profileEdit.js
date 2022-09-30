import axios from "axios";
import { getAuthorizationHeader } from "../axios";
import { baseURL } from "../axios";

export const postProfileEdit = (formData) => {
  return axios
    .post(`${baseURL}/api/v1/members/profile`, 
    formData,
    {
      headers: {
        Authorization: getAuthorizationHeader(),
        "Content-Type": "multipart/form-data",
        "Accept": "application/json, multipart/form-data"
      },
    })
    .then((res) => {
      return res.data.data;
    })
    .catch((err) => {
      console.log(err.response);
    });
};
