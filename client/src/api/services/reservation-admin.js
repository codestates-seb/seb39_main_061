import { adminAxiosInstance } from "./instance";

export const getAdminResList = (businessId, qrId) => {
  return adminAxiosInstance.get(
    `/business/${businessId}/reservation/qr-code/${qrId}?page=1&size=10`
  );
};

export const deleteAdminRes = (businessId, qrId, reservationId) => {
  return adminAxiosInstance({
    method: "PATCH",
    url: `/business/${businessId}/reservation/qr-code/${qrId}/info/${reservationId}/cancel`,
  });
};
