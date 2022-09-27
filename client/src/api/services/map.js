import axios from "axios";

export const getCoodinate = (address) => {
  return axios
    .get(
      `https://dapi.kakao.com/v2/local/search/address.json?query=${address}`,
      {
        headers: {
          Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO}`,
        },
      }
    )
    .then((res) => {
      console.log(res.data.documents[0]);
    });
};
