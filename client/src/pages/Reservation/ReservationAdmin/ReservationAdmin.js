import { useState, useEffect } from "react";
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
  const [modalOpen, setModalOpen] = useState(false);
  const openModal = () => {
    setModalOpen(true);
  };
  const closeModal = () => {
    setModalOpen(false);
  };
  const [res, setRes] = useState([]);
  const [modalInput, setModalInput] = useState("");

  useEffect(() => {
    getBusInfo().then((res) => {
      setBusinessId(res.data.data.businessId);
    });
    axiosData();
    // eslint-disable-next-line
  }, [businessId]);

  const onModal = (e) => {
    e.preventDefault();
    setModalInput(e.target.value.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3"));
  };

  const axiosData = async () => {
    try {
      getAdminResList(businessId, 1).then((res) => {
        setRes(res.data);

        console.log(businessId);
      });
    } catch (err) {
      console.log("Error >>", err);
    }
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
    if (modalInput === phone) {
      deleteAdminRes(businessId, 1, reservationId)
        .then((res) => {
          //console.log(res.message);
          return axiosData();
        })
        .catch((err) => {});
      alert(`${name}님의 예약이 삭제되었습니다.`);
      closeModal();
      setModalInput("");
    } else window.confirm("연락처가 일치하지 않습니다.");

    setModalInput("");
  }

  return (
    <div className={styles.container}>
      <Sidebar />

      <div className={styles.pages}>
        <div className={styles.title}>예약 관리</div>
        <div className={styles.subtitle}>매장 정보</div>
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
                            className={styles.trash}
                            size="25"
                            color="#256D85"
                            onClick={openModal}
                          ></HiTrash>

                          <Modal
                            open={modalOpen}
                            close={closeModal}
                            header="예약 연락처를 입력하세요"
                          >
                            <main>
                              <input
                                key={re.id}
                                type="tel"
                                name="modal_input"
                                required="required"
                                value={modalInput}
                                onChange={onModal}
                                className={styles.input_modal}
                                placeholder="연락처 입력 -제외"
                              />
                              <button
                                onClick={() =>
                                  deleteUser(
                                    re.reservationId,
                                    re.name,
                                    re.phone
                                  )
                                }
                                className={styles.button_confirm}
                              >
                                확인
                              </button>
                            </main>
                          </Modal>
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
