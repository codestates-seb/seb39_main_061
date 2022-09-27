import axios from "axios";
import { useDispatch } from "react-redux";
import { barChartsAction } from "../store/barCharts";
// import { useState } from "react";
// import { barCharts, extraReducers } from "../store/barCharts";
// import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

const baseURL = "http://localhost:8080";

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
    })
    .catch((err) => {
      console.log(err.response.data.message);
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
      console.log(err.response);
    });
};

export const oauthReq = (sectorId, businessName, phone, name) => {
  return axios
    .patch(
      `${baseURL}/auth/members`,
      {
        service: "reservation",
        sectorId,
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

export const getStatisticsChart = async () => {
  return await axios
    .get(`${baseURL}/api/v1/business/1/reservation/qr-code/1/statistics?date=20220927`, {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    })
    .then((res) => {
      console.log(res.data.data)
      return res.data.data
    })
    .catch((err) => {
      console.log(err.response);
    });
};

// export const asyncChartAxios = createAsyncThunk(
//   'barChartSlice/asyncChartAxios',
//   async () => {
//     await axios
//       .get(`${baseURL}/api/v1/business/1/reservation/qr-code/1/statistics?date=20220927`, {
//         headers: {
//           Authorization: getAuthorizationHeader(),
//         },
//       })
//       .then((res) => {
//         console.log(res.data.data)
//         useDispatch(barCharts.extraReducers(res.data.data))
//       })
//       .catch((err) => {
//         console.log(err.response);
//       });

//   }
// )

export default axiosInstance;
