import axios from "axios";
import { getAuthorizationHeader } from "../axios";
import { baseURL } from "../axios";

export const profileEdit = () => {
  const formData = new FormData()
      formData.enctype = "multipart/form-data"
      const profileFormData = {
        "email": "",
        "password": newPassword,
        "service": ["reservation", "keep"],
        "profileImg": "profile-img-url",
        "phone": phone,
        "name": newName
      }
      formData.append('file', image.image_file)
      formData.append("data",
        new Blob([JSON.stringify(profileFormData)], { type: "application/json" }));
      console.log(formData.get('file'));
  return axios
    .post(`${baseURL}/api/v1/members/profile`, {
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



      const profileData = await axiosInstance({
        url: "/api/v1/members/profile",
        method: "POST",
        data: formData,
        headers: {
          Authorization: "Bearer ",
          "Content-Type": "multipart/form-data"
        }
      })