import Styles from "./StoreInfo.module.css";
import { useRef, useState } from "react";
import { useSelector } from "react-redux";
import { getBusinessInfo, postBusinessInfo } from "../../api/services/store";
import Modal from "react-modal"; // 추가
import MapContainer from "../MapContainer/MapContainer";
import { useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const StoreInfo = () => {
  const profile = useSelector((state) => state.user.userProfile);
  const address = useSelector((state) => state.map.address);
  const lat = useSelector((state) => state.map.lat);
  const lon = useSelector((state) => state.map.lon);
  console.log("위도,경도? ", lat, lon);
  const [canEdit, setCanEdit] = useState(false);
  const [name, setName] = useState("");
  const [openTime, setOpenTime] = useState("");
  const [holiday, setHoliday] = useState("");
  const [phone, setPhone] = useState("");
  const [introduction, setIntroduction] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const [memberId, setMemberId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    getBusinessInfo().then((res) => {
      setMemberId(res.data.data.businessId);
      console.log("멤버 id", res);
      console.log("매장정보 조회", res);
    });
  }, []);

  const storeSubmitHanlder = (event) => {
    event.preventDefault();

    // const form = new FormData();
    // form.append("data", {
    //   memberId,
    // name,
    // introduction,
    // openTime,
    // holiday,
    // address,
    // phone,
    // lon,
    // lat,
    // });

    // 매장 정보 수정 axios

    // 매장 정보 가져오기
  };
  const changeBtnHandler = () => {
    // 수정하기 버튼누르면 버튼이름을 "완료"로 바꾸는 함수
    setCanEdit(true);
  };

  const editSubmitHandler = async () => {
    // 1. 완료버튼을 누르면 유효성검사하고
    // 2. Axios patch로 수정 전송!
    // 3. 수정전송이 완료되고 200ok면 버튼을 수정으로 바꾸고 새로고침 되게 만들기
    console.log(
      "입력 확인",
      name,
      introduction,
      openTime,
      holiday,
      address,
      phone,
      lon,
      lat
    );
    const res = await postBusinessInfo(
      name,
      introduction,
      openTime,
      holiday,
      address,
      phone,
      lon,
      lat
    );
    console.log("매장정보 수정?", res);
    setCanEdit(false);
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
  const cancelHandler = () => {
    window.location.reload();
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
                  readOnly={canEdit === false}
                  value={name}
                  onChange={nameHandler}
                  placeholder="ex:덕이네 곱창"
                />
              </div>
              <div>
                <span>영업 시간</span>
                <input
                  readOnly={canEdit === false}
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
                  readOnly={canEdit === false}
                  value={holiday}
                  onChange={holidayHandler}
                  placeholder="ex:매주 월요일"
                />
              </div>
              <div>
                <span>매장 전화번호</span>
                <input
                  readOnly={canEdit === false}
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
                readOnly={canEdit === false}
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
                  readOnly={canEdit === false}
                  value={address}
                  disabled
                  placeholder="ex: 서울특별시 동작구 밤리단길 369 B1"
                />
                <button onClick={toggle}>주소검색</button>
                <Modal
                  // onClick={console.log("click")}
                  isOpen={isOpen}
                  ariaHideApp={false}
                  style={customStyles}
                  // onRequestClose={toggle}
                >
                  <MapContainer toggle={toggle} />
                </Modal>
              </div>
            </div>
          </div>
        </div>
        <div className={Styles.StoreInfo__input__btn}>
          {!canEdit && <button onClick={changeBtnHandler}>수정</button>}
          {canEdit && <button onClick={editSubmitHandler}>완료</button>}
          {canEdit && <button onClick={cancelHandler}>취소</button>}
        </div>
      </form>
    </div>
  );
};

export default StoreInfo;
