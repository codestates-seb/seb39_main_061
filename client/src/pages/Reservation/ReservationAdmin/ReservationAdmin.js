import React, { useState, useEffect } from "react";
import Modal from "../../../components/Reservation/Modal/Modal.js";
import styles from "./ReservationAdmin.module.css";
import Sidebar from "../../../components/Sidebar/Sidebar.js";
import {
  deleteAdminRes,
  getAdminResList,
  getResNotification,
} from "../../../api/services/reservation-admin.js";
import { getBusInfo } from "../../../api/services/reservation-admin.js";
import { HiTrash } from "react-icons/hi";
import { BsFillPhoneVibrateFill } from "react-icons/bs";

function ReservationAdmin() {
  const [businessId, setBusinessId] = useState("");

  const [storeName, setStoreName] = useState("");

  const [res, setRes] = useState([]);

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
          getAdminResList(businessId, 1).then((res) => {
            return setRes(res.data);
          });
        }
      })

      .catch((err) => {
        console.log("Error >>", err);
      });
  };

  function notification(reservationId, name) {
    getResNotification(businessId, 1, reservationId)
      .then((res) => {
        console.log(reservationId);
        return axiosData();
      })
      .catch((err) => {});
    alert(`${name}님께 알림을 보냅니다.`);
  }

  function deleteUser(reservationId, name, phone) {
    deleteAdminRes(businessId, 1, reservationId)
      .then((res) => {
        //console.log(res.message);
        return axiosData();
      })
      .catch((err) => {});
    alert(`${name}님의 예약이 삭제되었습니다.`);
  }

  return (
    <div className={styles.container}>
      <Sidebar />

      <div className={styles.pages}>
        <div className={styles.title}>예약 관리</div>
        <div className={styles.subtitle}>{storeName}</div>
        <div className={styles.tables}>
          <table className={styles.table}>
            <thead className={styles.thead}>
              <tr>
                <th className={styles.th}>번호</th>
                <th className={styles.th}>이름</th>
                <th className={styles.th}>연락처</th>
                <th className={styles.th}>인원</th>
                <th className={styles.th}></th>
              </tr>
            </thead>
            <tbody className={styles.tbody}>
              {res.data &&
                res.data
                  .sort((a, b) => {
                    if (a.reservationId > b.reservationId) return 1;
                    if (a.reservationId < b.reservationId) return -1;
                    return 0;
                  })
                  .map((re) => {
                    return (
                      <tr className={styles.tr} key={re.reservationId}>
                        <td className={styles.td1}>{re.reservationId}</td>
                        <td className={styles.td}>{re.name}</td>
                        <td className={styles.td}>{re.phone}</td>
                        <td className={styles.td}>{re.count}</td>
                        <td className={styles.td4}>
                          <BsFillPhoneVibrateFill
                            className={styles.phone}
                            size="25"
                            color="#256D85"
                            onClick={() =>
                              notification(re.reservationId, re.name)
                            }
                          ></BsFillPhoneVibrateFill>
                          <HiTrash
                            onClick={() =>
                              deleteUser(re.reservationId, re.name, re.phone)
                            }
                            className={styles.trash}
                            size="25"
                            color="#256D85"
                          ></HiTrash>
                        </td>
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

export default ReservationAdmin;
