import { userAxiosInstance } from "./instance";

export const getRevUserList = (businessId) => {
  return userAxiosInstance.get(
    `/business/${businessId}/review?page=1&size=110`
  );
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
