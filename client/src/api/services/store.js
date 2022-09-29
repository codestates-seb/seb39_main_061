import axios from "axios";
import { baseURL } from "../axios";
import { getAuthorizationHeader } from "../axios";

export const postBusinessInfo = (
  businessId,
  name,
  introduction,
  openTime,
  holiday,
  address,
  phone,
  lon,
  lat
) => {
  return axios.patch(
    `${baseURL}/api/v1/business/${businessId}`,
    { name, introduction, openTime, holiday, address, phone, lon, lat },
    {
      headers: {
        Authorization: getAuthorizationHeader(),
      },
    }
  );
};

export const getBusinessInfo = () => {
  return axios.get(`${baseURL}/api/v1/business`, {
    headers: {
      Authorization: getAuthorizationHeader(),
    },
  });
};
