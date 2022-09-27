import { baseURL } from "../axios";
import axios from "axios";
import { getAuthorizationHeader } from "../axios";

export const login = (email, password) => {
  return axios
    .post(`${baseURL}/auth/login`, {
      email,
      password,
    })
    .then((res) => {
      return res.data.data.accessToken;
    });
};

export const signUpReq = (
  email,
  password,
  name,
  businessName,
  phone,
  sectorId
) => {
  return axios
    .post(`${baseURL}/auth/signup`, {
      email,
      password,
      name,
      businessName,
      phone,
      role: "reservation",
      sectorId,
    })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      return err.response.data;
    });
};

export const oauthReq = (businessName, phone, name) => {
  return axios
    .patch(
      `${baseURL}/auth/members`,
      {
        service: "reservation",
        businessName,
        phone,
        name,
      },
      {
        headers: {
          Authorization: getAuthorizationHeader(),
        },
      }
    )
    .then((res) => {
      console.log("추가 전송 성공!");
      return res;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
};

export const emailCheck = (email) => {
  return axios
    .post(`${baseURL}/auth/email-validation`, {
      email,
    })
    .then((res) => {
      console.log(res.data.data.exist);
      return res.data.data.exist;
    })
    .catch((err) => {
      return console.log(err);
    });
};
