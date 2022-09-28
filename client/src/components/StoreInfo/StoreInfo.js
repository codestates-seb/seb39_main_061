import Styles from "./StoreInfo.module.css";
import { useRef, useState } from "react";
import { useSelector } from "react-redux";
import { getBusinessInfo, postBusinessInfo } from "../../api/services/store";
import Modal from "react-modal"; // 추가
import MapContainer from "../MapContainer/MapContainer";
import { useEffect } from "react";
import axios from "axios";

const StoreInfo = () => {
  const profile = useSelector((state) => state.user.userProfile);
  console.log(profile);
  const address = useSelector((state) => state.map.address);
  const lat = useSelector((state) => state.map.lat);
  const lon = useSelector((state) => state.map.lon);
  const [btn, setBtn] = useState(false);
  const [name, setName] = useState("");
  const [openTime, setOpenTime] = useState(0);
  const [holiday, setHoliday] = useState("");
  const [phone, setPhone] = useState(0);
  const [introduction, setIntroduction] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const [memberId, setMemberId] = useState(null);

  useEffect(() => {
    getBusinessInfo().then((res) => {
      setMemberId(res.data.data.businessId);
      console.log("멤버 id", res.data.data.businessId);
      console.log("매장정보 조회", res);
    });
  }, []);

  const storeSubmitHanlder = (event) => {
    event.preventDefault();

    // const form = new FormData();
    // form.append("data", {
    //   memberId,
    //   name,
    //   introduction,
    //   openTime,
    //   holiday,
    //   address,
    //   phone,
    //   lon,
    //   lat,
    // });

    // 매장 정보 수정 axios
    postBusinessInfo();
    // 매장 정보 가져오기
  };

  const editSubmitHandler = () => {
    // 수정, 완료 버튼 체인지
    setBtn(!btn);
  };
  const nameHandler = (e) => {
    setName(e.target.value);
  };
  const openTimeHandler = (e) => {
    setOpenTime(e.target.value);
  };
  const holidayHandler = (e) => {
    setHoliday(e.target.value);
  };
  const phoneHandler = (e) => {
    setPhone(e.target.value);
  };
  const introHandler = (e) => {
    setIntroduction(e.target.value);
  };
  const toggle = (e) => {
    setIsOpen(!isOpen);
  };

  // Modal 스타일
  const customStyles = {
    overlay: {
      backgroundColor: "rgba(0,0,0,0.5)",
    },
    content: {
      left: "0",
      margin: "auto",
      width: "1000px",
      height: "700px",
      padding: "0",
    },
  };

  return (
    <div>
      <form onSubmit={storeSubmitHanlder} className={Styles.StoreInfo}>
        <div className={Styles.StoreInfo__title}>
          <h1>매장 정보</h1>
        </div>
        <div className={Styles.StoreInfo__input}>
          <div className={Styles.StoreInfo__input__wrap1}>
            <div className={Styles.StoreInfo__input__wrap1__info1}>
              <div className={Styles.time}>
                <span>매장 이름</span>
                <input
                  value={name}
                  onChange={nameHandler}
                  placeholder="ex:덕이네 곱창"
                />
              </div>
              <div>
                <span>영업 시간</span>
                <input
                  value={openTime === 0 ? "" : openTime}
                  onChange={openTimeHandler}
                  placeholder="ex:10:00 ~ 21:00"
                />
              </div>
            </div>
            <div className={Styles.StoreInfo__input__wrap1__info2}>
              <div>
                <span>휴무일</span>
                <input
                  value={holiday}
                  onChange={holidayHandler}
                  placeholder="ex:매주 월요일"
                />
              </div>
              <div>
                <span>매장 전화번호</span>
                <input
                  value={phone === 0 ? "" : phone}
                  onChange={phoneHandler}
                  placeholder="ex:031-947-3334"
                />
              </div>
            </div>
          </div>
          <div className={Styles.StoreInfo__input__wrap2}>
            <div className={Styles.StoreInfo__input__wrap2__info1}>
              <span>소개글</span>
              <textarea
                value={introduction}
                onChange={introHandler}
                placeholder="ex: 우리집 맛집이에요 ! 많이들 드시러 오세요"
              ></textarea>
            </div>
            <div className={Styles.StoreInfo__input__wrap2__info2}>
              <div className={Styles.StoreInfo__input__wrap2__info2__title}>
                <span>매장 위치</span>
              </div>
              <div className={Styles.StoreInfo__input__wrap2__info2__address}>
                <input
                  value={address}
                  disabled
                  placeholder="ex: 서울특별시 동작구 밤리단길 369 B1"
                />
                <button onClick={toggle}>주소검색</button>
                <Modal
                  onClick={console.log("click")}
                  isOpen={isOpen}
                  ariaHideApp={false}
                  style={customStyles}
                  onRequestClose={toggle}
                >
                  <MapContainer toggle={toggle} />
                </Modal>
              </div>
            </div>
          </div>
        </div>
        <div className={Styles.StoreInfo__input__btn}>
          <button onClick={editSubmitHandler}>
            {btn && "완료"}
            {!btn && "수정"}
          </button>
          <button>취소</button>
        </div>
      </form>
    </div>
  );
};

export default StoreInfo;
