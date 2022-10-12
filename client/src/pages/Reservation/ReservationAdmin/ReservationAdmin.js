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
import Header from "../../../components/Header/Header.js";
import qrCode from "../../../store/qrCode.js";
import { getQRcodeInfo } from "../../../api/services/createQrcode.js";
function ReservationAdmin() {
  const title = "예약관리";

  const [businessId, setBusinessId] = useState("");

  const [storeName, setStoreName] = useState("");
  const [qrCodeId, setQrCodeId] = useState();

  const [res, setRes] = useState([]);

  useEffect(() => {
    getInfo();
    // eslint-disable-next-line
  }, [businessId]);

  const getInfo = async () => {
    // 비즈니스 ID 가져오기
    const business = await getBusInfo();
    setBusinessId(business.data.data.businessId);
    setStoreName(business.data.data.name);
    // QR코드 ID 가져오기
    const qr = await getQRcodeInfo(business.data.data.businessId);
    setQrCodeId(qr[0].qrCodeId);
    // 예약자 명단가져오기
    const adminResList = await getAdminResList(
      business.data.data.businessId,
      qr[0].qrCodeId
    );
    // 예약자 리스트 데이터
    setRes(adminResList.data);
  };

  const axiosData = async () => {
    getBusInfo().then((res) => {
      console.log(res);
      setBusinessId(res.data.data.businessId);
      setStoreName(res.data.data.name);
    });
  };

  function notification(reservationId, name, qrcodeId) {
    getResNotification(businessId, qrCodeId, reservationId)
      .then((res) => {
        console.log("알림 성공", reservationId);
        return getInfo();
      })
      .catch((err) => {});
    alert(`${name}님께 알림을 보냅니다.`);
  }

  function deleteUser(reservationId, name, phone) {
    deleteAdminRes(businessId, qrCodeId, reservationId)
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
      <div className={styles.main_container}>
        <Header title={title} />
        <div className={styles.flex_container}>
          <div className={styles.pages}>
            <div className={styles.subtitle}>{storeName}</div>

            <div className={styles.tables}>
              <table className={styles.table}>
                <thead className={styles.thead}>
                  <tr className={styles.firstTR}>
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
                                  deleteUser(
                                    re.reservationId,
                                    re.name,
                                    re.phone
                                  )
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
      </div>
    </div>
  );
}

export default ReservationAdmin;
