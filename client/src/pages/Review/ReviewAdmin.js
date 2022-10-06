import { useState, useEffect } from "react";
import styles from "./ReviewAdmin.module.css";
import { getRevAdminList } from "../../api/services/review";
import { getBusInfo } from "../../api/services/reservation-admin";
import Sidebar from "../../components/Sidebar/Sidebar";

function ReviewAdmin() {
  const [review, setReview] = useState([]);
  const [businessId, setBusinessId] = useState("");
  const [storeName, setStoreName] = useState("");
  useEffect(() => {
    axiosData();
    // eslint-disable-next-line
  }, [businessId]);

  const axiosData = async () => {
    getBusInfo()
      .then((res) => {
        setBusinessId(res.data.data.businessId);
        setStoreName(res.data.data.name);
      })

      .then(() => {
        if (typeof businessId === "number") {
          getRevAdminList(businessId).then((res) => {
            return setReview(res.data);
          });
        }
      })

      .catch((err) => {
        console.log("Error >>", err);
      });
  };

  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.pages}>
        <div className={styles.title}>리뷰 관리</div>
        <div className={styles.subtitle}>{storeName}</div>
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
                        <td className={styles.td1}>{rev.reviewId}</td>
                        <td className={styles.td}>{rev.contents}</td>
                      </tr>
                    );
                  })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default ReviewAdmin;
