import { baseURL } from "../axios";
import axios from "axios";
import { getAuthorizationHeader } from "../axios";

export const getMenuList = (businessId, page) => {
  return axios
    .get(`${baseURL}/api/v1/business/${businessId}/menu?page=${page}&size=10`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      console.log(err);
    });
};
export const getMenu = (businessId, menuId) => {
  return axios
    .get(`${baseURL}/api/v1/business/${businessId}/menu/${menuId}`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      console.log(err);
    });
};
export const postMenu = (businessId, formData) => {
  return axios({
    method: "POST",
    url: `${baseURL}/api/v1/business/${businessId}/menu`,
    data: formData,
    headers: {
      Authorization: getAuthorizationHeader(),
      "Content-Type": "multipart/form-data", // Content-Type을 반드시 이렇게 하여야 한다.
    },
    // data 전송시에 반드시 생성되어 있는 formData 객체만 전송 하여야 한다.
  });
};

export const editMenu = (businessId, menuId, formData) => {
  return axios({
    method: "POST",
    url: `${baseURL}/api/v1/business/${businessId}/menu/${menuId}`,
    data: formData,
    headers: {
      Authorization: getAuthorizationHeader(),
      "Content-Type": "multipart/form-data", // Content-Type을 반드시 이렇게 하여야 한다.
    },
    // data 전송시에 반드시 생성되어 있는 formData 객체만 전송 하여야 한다.
  });
};

export const deleteMenu = (businessId, menuId) => {
  return axios
    .delete(`${baseURL}/api/v1/business/${businessId}/menu/${menuId}`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err);
    });
};
