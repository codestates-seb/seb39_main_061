import { userAxiosInstance } from "./instance";

export const getUserResList = (businessId, qrCodeId) => {
  return userAxiosInstance.get(
    `/business/${businessId}/reservation/qr-code/${qrCodeId}?page=1&size=10`
  );
};

export const registerUserRes = (businessId, qrCodeId, name, phone, count) => {
  return userAxiosInstance({
    method: "POST",
    url: `/business/${businessId}/reservation/qr-code/${qrCodeId}`,
    data: {
      businessId,
      qrCodeId,
      name,
      phone,
      count,
    },
  });
};

export const getUserStoreInfo = (businessId) => {
  return userAxiosInstance.get(`/business/${businessId}`);
};
