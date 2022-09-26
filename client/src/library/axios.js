import axios from "axios";

export const baseURL = process.env.REACT_APP_BASE_URL;

export const getToken = () => localStorage.getItem("token");

export const getAuthorizationHeader = () => `Bearer ${getToken()}`;

export const axiosInstance = axios.create({
  baseURL,
  headers: { Authorization: getAuthorizationHeader() },
});

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

export const getProfile = () => {
  return axios
    .get(`${baseURL}/api/v1/members/profile`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      return res.data.data;
    })
    .catch((err) => {
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
      return res.data.data.accessToken;
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

export const getReservationChart = () => {
  return axios
    .get(`${baseURL}/api/v1/business/1/reservation/qr-code/1/statistics?date=20220926`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      console.log(res.data.data)
      return res.data.data;
    })
    .catch((err) => {
      console.log(err.response);
    });
};

export default axiosInstance;
