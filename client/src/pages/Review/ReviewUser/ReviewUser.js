import React, { useState, useEffect } from "react";
import styles from "./ReviewUser.module.css";
import food from "../../../Img/food.png";
import logo from "../../../Img/Asset_2.png";
import { registerUserRev } from "../../../api/services/review";
import Map from "../../../components/Map/Map";
import { getBusinessInfoUser } from "../../../api/services/store";
import { useParams } from "react-router-dom";
import { getUserResList } from "../../../api/services/reservation-user";
import { getRevUserList } from "../../../api/services/review";
import { getUserStoreInfo } from "../../../api/services/reservation-user";
import { useLocation } from "react-router-dom";
import { Link } from "react-router-dom";

function ReviewUser() {
  const [review, setReview] = useState([]);
  const [storeName, setStoreName] = useState("");
  const [openTime, setOpenTime] = useState("");
  const [lat, setLat] = useState(33.450701);
  const [lng, setLng] = useState(126.570667);
  const [isLoading, setIsLoading] = useState(true);
  const location = useLocation();
  const path = location.pathname;
  const businessId = path.substr(17, 1);
  const qrCodeId = path.substr(27, 1);
  const [holiday, setHoliday] = useState("");

  const axiosData = async () => {
    getUserStoreInfo(businessId)
      .then((res) => {
        setStoreName(res.data.data.name);
        setOpenTime(res.data.data.openTime);
        setHoliday(res.data.data.holiday);
        console.log(businessId);
        console.log(qrCodeId);
      })
      .then(() => {
        getRevUserList(businessId, qrCodeId).then((res) => {
          setReview(res.data);
        });
      });
  };

  useEffect(() => {
    getUserResList(businessId, qrCodeId).then((res) => {
      console.log("리스트", res);
    });

    axiosData();
    // let path = window.location.pathname;
    // path = path.split("/");
    // const businessId = path[3];
    console.log("비즈니스", businessId);
    getBusinessInfoUser(businessId).then((res) => {
      console.log("매장정보", res.data.data);
      setIsLoading(false);
      if (res.data.data.lat !== 0 && res.data.data.lng !== 0) {
        setLat(res.data.data.lon);
        setLng(res.data.data.lat);
      }
    });

    // setInterval(() => {
    //     axiosData();
    // }, 60000);
  }, []);

  const handleInput = (e) => {
    if (e.target.value.length > 200) {
      alert(`200자 이내로 입력하세요`);
      return false;
    }
  };

  const onSubmitHandler = (e) => {
    const contents = e.target.contents.value;

    if (contents.length < 200) {
      registerUserRev(businessId, contents).then(() => axiosData());
    } else {
      alert(`200자 이내로 입력하세요`);
    }
  };

  const menuHandler = () => {};

  return (
    <div className={styles.review}>
      <div className={styles.reviewContainer}>
        <img className={styles.food} src={food} alt="대표음식" />
        <div className={styles.title}>{storeName}</div>
        <div className={styles.subtitle}>영업시간: {openTime}</div>
        <div className={styles.secondTitle}>
          <div className={styles.subtitle}>휴무일: {holiday}</div>
          <Link to={`/business/${businessId}/qr-code/${qrCodeId}`}>
            <button className={styles.link}>에약대기</button>
          </Link>
        </div>

        {!isLoading && <Map lat={lat} lng={lng} level={3} />}
        <div className={styles.pages}>
          <div className={styles.tables}>
            <table className={styles.table}>
              <tbody className={styles.tbody}>
                {review.data &&
                  review.data
                    .sort((a, b) => {
                      if (a.reviewId > b.reviewId) return 1;
                      if (a.reviewId < b.reviewId) return -1;
                      return 0;
                    })
                    .map((rev) => {
                      return (
                        <tr className={styles.tr} key={rev.reviewId}>
                          <td className={styles.td}>
                            {rev.reviewId}
                            <div>{rev.contents}</div>
                          </td>
                        </tr>
                      );
                    })}
              </tbody>
            </table>
          </div>

          <div className={styles.upform}>
            <form className={styles.form} onSubmit={onSubmitHandler}>
              <div className={styles.div}>
                <input
                  className={styles.input}
                  type="text"
                  name="contents"
                  required="required"
                  onChange={handleInput}
                  placeholder="  200자 이내로 입력이 가능합니다."
                />
                {/* <label for="" className={styles.label}>200자 이내로 입력이 가능합니다.</label> */}
                <button className={styles.button_res} type="submit">
                  등록
                </button>
              </div>

              <div className={styles.logo}>
                <img src={logo} alt="로고" />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReviewUser;
