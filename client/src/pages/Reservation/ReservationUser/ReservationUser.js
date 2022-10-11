import React, { useState, useEffect, useRef } from "react";
import food from "../../../Img/food.png";
import logo from "../../../Img/Asset_2.png";
import styles from "./ReservationUser.module.css";
import { Link, useParams, useLocation } from "react-router-dom";
import {
  getUserResList,
  registerUserRes,
  getUserStoreInfo,
  getUserFoodList,
} from "../../../api/services/reservation-user";
import Modal from "../../../components/Modal/Modal";
import MenuModal from "react-modal";
import MenuInfo from "../../../components/MenuInfo/MenuInfo";
import UserMenu from "../../../components/UserMenu/UserMenu";

function ReservationUser() {
  const [storeName, setStoreName] = useState("");
  // const [food, setFood] = useState("");
  const [address, setAddress] = useState("");
  const [num, setNum] = useState("");
  const phoneRef = useRef();
  const [res, setRes] = useState([]);
  const location = useLocation();
  const path = location.pathname;
  const businessId = path.substr(10, 1);
  const qrCodeId = path.substr(20, 1);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [reservationId, setReservationId] = useState(0);
  const [resCount, setResCount] = useState("");
  const [holiday, setHoliday] = useState("");
  const [openTime, setOpenTime] = useState("");
  const [isOpen, setIsOpen] = useState(false);

  const axiosData = async () => {
    getUserStoreInfo(businessId)
      .then((res) => {
        setStoreName(res.data.data.name);
        setAddress(res.data.data.address);
        setHoliday(res.data.data.holiday);
        setOpenTime(res.data.data.openTime);
      })
      // .then(() => {
      //   getUserFoodList(businessId).then((res) => {
      //     setFood(res.data.data.img);
      //     console.log(food);
      //   });
      // })
      .then(() => {
        getUserResList(businessId, qrCodeId).then((res) => {
          setRes(res.data);
        });
      });
  };

  useEffect(() => {
    axiosData();

    setInterval(() => {
      axiosData();
    }, 60000);
  }, []);

  const handlePhone = (e) => {
    setNum(e.target.value.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3"));
  };

  const onSubmitHandler = (e) => {
    e.preventDefault();
    const name = e.target.name.value;
    const phone = e.target.phone.value;
    const count = e.target.count.value;

    if (phone.includes("*") === true || phone.length === 13) {
      registerUserRes(businessId, qrCodeId, name, phone, count)
        .catch((err) => {
          console.log("에러", err.response.data.message);
          if (err.response.data.message === "RESERVATION IS ALREADY EXISTS") {
            alert("이미 예약된 사용자 입니다");
          }
          console.log("이유", err.response.data);
          if (err.response.data.fieldErrors) {
            alert(err.response.data.fieldErrors[0].reason);
          }
        })
        .then((res) => {
          axiosData().catch((err) => {
            console.log(err);
          });
        });

      e.target.name.value = "";
      setNum("");
      e.target.count.value = "";
    }
  };

  const trClickHandler = (resId) => {
    setIsModalOpen(true);
    // console.log("click", resId);
  };
  const customStyles = {
    overlay: {
      backgroundColor: "rgba(0,0,0,0.2)",

      zIndex: "1",
    },
    content: {
      margin: "auto",
      width: "340px",
      height: "600px",
      padding: "0",
      left: "0px",
      right: "0px",
      borderRadius: "20px",
    },
  };
  const toggle = (e) => {
    setIsOpen(!isOpen);
  };

  return (
    <div className={styles.reservationUser}>
      <div className={styles.reservationContainer}>
        <img className={styles.food} src={food} alt="대표음식" />
        <div className={styles.pages}>
          <div className={styles.userhaed}>
            <div className={styles.address}>
              <div className={styles.title}>{storeName}</div>

              <div className={styles.holiday}>
                {openTime.length !== 0 ? `영업시간: ${openTime}` : ""}
              </div>
              <div className={styles.holiday}>
                {holiday.length !== 0 ? `휴무일: ${holiday}` : ""}
              </div>

              <div className={styles.subtitle}>
                <button onClick={toggle} className={styles.link}>
                  메뉴보기
                </button>

                <div>{address}</div>
                <Link to={`/review/business/${businessId}/qr-code/${qrCodeId}`}>
                  <button className={styles.link}>리뷰쓰기</button>
                </Link>
              </div>
            </div>
          </div>
          <div className={styles.tables}>
            <table className={styles.table}>
              <thead className={styles.thead}>
                <tr className={styles.tr}>
                  <th className={styles.th1}>번호</th>
                  <th className={styles.th}>이름</th>
                  <th className={styles.th}>연락처</th>
                  <th className={styles.th4}>인원</th>
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
                        <tr
                          onClick={() => {
                            setReservationId(re.reservationId);
                            setResCount(re.count);
                            trClickHandler(re.reservationId);
                          }}
                          className={styles.tr}
                          key={re.reservationId}
                        >
                          <td className={styles.td1}>{re.reservationId}</td>
                          <td className={styles.td}>{re.name}</td>
                          <td className={styles.td}>{re.phone}</td>
                          <td className={styles.td4}>{re.count}</td>
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
                  name="name"
                  required="required"
                  placeholder=" "
                />
                <label htmlFor="" className={styles.label}>
                  이름
                </label>
              </div>
              <div className={styles.div}>
                <input
                  className={styles.input}
                  type="tel"
                  name="phone"
                  required="required"
                  value={num}
                  ref={phoneRef}
                  placeholder=" "
                  onChange={handlePhone}
                />
                <label htmlFor="" className={styles.label}>
                  연락처 -제외
                </label>
              </div>
              <div className={styles.submit}>
                <input
                  className={styles.input}
                  type="number"
                  name="count"
                  required="required"
                  placeholder=" "
                />
                <label htmlFor="" className={styles.label}>
                  인원 수
                </label>
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
      <MenuModal
        isOpen={isOpen}
        ariaHideApp={false}
        // className={styles.reactModal}
        // overlayClassName={styles.overlay}
        style={customStyles}
        onRequestClose={toggle}
      >
        <UserMenu toggle={toggle} businessId={businessId} />
      </MenuModal>

      {isModalOpen && (
        <Modal
          setIsModalOpen={setIsModalOpen}
          num={13}
          businessId={businessId}
          qrCodeId={Number(qrCodeId)}
          reservationId={reservationId}
          resCount={resCount}
        />
      )}
    </div>
  );
}

export default ReservationUser;
