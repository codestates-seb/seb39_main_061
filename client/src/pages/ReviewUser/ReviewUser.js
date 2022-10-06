import React, { useState, useEffect } from "react";
import styles from "./ReviewUser.module.css";
import food from "../../Img/food.png";
import logo from "../../Img/Asset_2.png";
import { registerUserRev } from "../../api/services/review-user";
import Map from "../../components/Map/Map";
import { getBusinessInfoUser } from "../../api/services/store";
import { useParams } from "react-router-dom";
import { getUserResList } from "../../api/services/reservation-user";

function ReviewUser() {
  const [review, setReview] = useState([]);
  const [lat, setLat] = useState(33.450701);
  const [lng, setLng] = useState(126.570667);
  const [isLoading, setIsLoading] = useState(true);

  const axiosData = async () => {
    try {
      getUserResList(1, 1).then((res) => {
        console.log(res.data);
        setReview(res.data);
      });
    } catch (err) {
      console.log("Error >>", err);
    }
  };

  useEffect(() => {
    getUserResList(1, 1).then((res) => {
      console.log("리스트", res);
    });
    console.log(businessId);
    axiosData();
    let path = window.location.pathname;
    path = path.split("/");
    const businessId = path[3];
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
      registerUserRev(1, contents).then(() => axiosData());

      alert(`예약이 등록되었습니다.`);
    } else {
      alert(`200자 이내로 입력하세요`);
    }
  };

  return (
    <div className={styles.review}>
      <img className={styles.food} src={food} alt="대표음식" />
      <div className={styles.title}>덕이네 불족발</div>
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
            </div>

            <button className={styles.button_res} type="submit">
              등록
            </button>
          </form>
          <img className={styles.logo} src={logo} alt="로고" />
        </div>
      </div>
    </div>
  );
}

export default ReviewUser;
