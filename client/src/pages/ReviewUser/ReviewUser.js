import { useState, useEffect, useRef } from "react";
import styles from "./ReviewUser.module.css";
import food from "./food.png";
import logo from "./Asset_2.png";
import { getRevUserList } from "../../api/services/review-user";
import { registerUserRev } from "../../api/services/review-user";

function ReviewUser() {
  const [review, setReview] = useState([]);

  const axiosData = async () => {
    try {
      getRevUserList(1).then((res) => {
        setReview(res.data);
      });
    } catch (err) {
      console.log("Error >>", err);
    }
  };

  useEffect(() => {
    axiosData();

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
    <>
      <img className={styles.food} src={food} />
      <div className={styles.title}>덕이네 불족발</div>
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
          <img className={styles.logo} src={logo} />
        </div>
      </div>
    </>
  );
}

export default ReviewUser;
