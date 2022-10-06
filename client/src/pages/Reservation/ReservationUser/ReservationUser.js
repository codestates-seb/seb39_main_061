import { useState, useEffect, useRef } from "react";
import food from "../../../Img/food.png";
import logo from "../../../Img/Asset_2.png";
import styles from "./ReservationUser.module.css";
import { Link } from "react-router-dom";
import {
  getUserResList,
  registerUserRes,
} from "../../../api/services/reservation-user";

function ReservationUser() {
  const [num, setNum] = useState("");
  const phoneRef = useRef();
  const [res, setRes] = useState([]);

  const axiosData = async () => {
    try {
      getUserResList(1, 1).then((res) => {
        setRes(res.data);
      });
    } catch (err) {
      console.log("Error >>", err);
    }
  };

  useEffect(() => {
    axiosData();

    setInterval(() => {
      axiosData();
    }, 180000);
    // eslint-disable-next-line
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
      registerUserRes(1, 1, name, phone, count).then(() => axiosData());
      alert(`${name}님의 예약이 등록되었습니다.`);
    } else {
      alert(`연락처 11자리를 입력하세요 (-제외)`);
    }
  };

  return (
    <>
      {/* const imgStr = get().respoonse[0].img // a.png
      <img className={styles.food} src={`${imgStr}`} /> */}
      <img className={styles.food} src={food} alt="대표음식" />
      <div className={styles.pages}>
        <div className={styles.userhaed}>
          <div className={styles.address}>
            <div className={styles.title}>덕이네 불족발</div>
            <div className={styles.subtitle}>서울시 강동구 새파람길 34</div>
            <Link to="/review-user" className={styles.link}>
              리뷰쓰기
            </Link>
          </div>
        </div>
        <div className={styles.tables}>
          <table className={styles.table}>
            <thead className={styles.thead}>
              <tr>
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
                      <tr className={styles.tr} key={re.reservationId}>
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
              <label for="" className={styles.label}>
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
              <label for="" className={styles.label}>
                연락처 -제외
              </label>
            </div>
            <div className={styles.div}>
              <input
                className={styles.input}
                type="number"
                name="count"
                required="required"
                placeholder=" "
              />
              <label for="" className={styles.label}>
                인원 수
              </label>
              <button className={styles.button_res} type="submit">
                등록
              </button>
            </div>
            <img className={styles.logo} src={logo} alt="로고" />
          </form>
        </div>
      </div>
    </>
  );
}

export default ReservationUser;
