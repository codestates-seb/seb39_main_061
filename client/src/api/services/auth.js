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

export const signUpReq = async (userValue) => {
  const { email, password, name, businessName, phone } = userValue;
  const res = await axios.post(`${baseURL}/auth/signup`, {
    email,
    password,
    name,
    businessName,
    phone,
    role: "reservation",
  });
  return res.data;
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
export const findPassword = (email) => {
  return axios.post(`${baseURL}/auth/password`, {
    email,
  });
};
