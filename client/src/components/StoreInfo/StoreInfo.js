import styles from "./StoreInfo.module.css";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getBusinessInfo, postBusinessInfo } from "../../api/services/store";
import Modal from "react-modal"; // 추가
import MapContainer from "../MapContainer/MapContainer";
import { useEffect } from "react";
import { mapActions } from "../../store/map";
import { authActions } from "../../store/auth";

const StoreInfo = () => {
  const address = useSelector((state) => state.map.address);
  const lat = useSelector((state) => state.map.lat);
  const lon = useSelector((state) => state.map.lon);

  const [canEdit, setCanEdit] = useState(false);
  const [name, setName] = useState("");
  const [openTime, setOpenTime] = useState("");
  const [holiday, setHoliday] = useState("");
  const [phone, setPhone] = useState("");
  const [introduction, setIntroduction] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const [businessId, setBusinessId] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const dispatch = useDispatch();

  useEffect(() => {
    getBusinessInfo()
      .then((res) => {
        setBusinessId(res.data.data.businessId);
        setName(res.data.data.name);
        setOpenTime(res.data.data.openTime);
        setHoliday(res.data.data.holiday);
        setPhone(res.data.data.phone);
        setIntroduction(res.data.data.introduction);
        setIntroduction(res.data.data.introduction);
        setIntroduction(res.data.data.introduction);
        dispatch(mapActions.setlat(res.data.data.lat));
        dispatch(mapActions.setlon(res.data.data.lon));
        dispatch(mapActions.setAddress(res.data.data.address));
        setIsLoading(true);
      })
      .catch((err) => {
        console.log(err);
        dispatch(authActions.logout());
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

    const res = await postBusinessInfo(
      businessId,
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
      {isLoading && (
        <form onSubmit={storeSubmitHanlder} className={styles.StoreInfo}>
          <div className={styles.storeInfo__title}>
            <h1>매장 정보</h1>
          </div>
          <div className={styles.storeInfo__input}>
            <div className={styles.storeInfo__input__grid1}>
              <div>
                <span>매장 이름</span>
              </div>

              <input
                readOnly={canEdit === false}
                value={name === null ? "" : name}
                onChange={nameHandler}
                placeholder="ex:덕이네 곱창"
              />
            </div>
            <div className={styles.storeInfo__input__grid2}>
              <div>
                <span>영업 시간</span>
              </div>
              <input
                readOnly={canEdit === false}
                value={openTime === null ? "" : openTime}
                onChange={openTimeHandler}
                placeholder="ex:10:00 ~ 21:00"
              />
            </div>
            <div className={styles.storeInfo__input__grid3}>
              <div>
                <span>소개글</span>
              </div>
              <textarea
                defaultChecked
                maxLength={80}
                readOnly={canEdit === false}
                value={introduction === null ? "" : introduction}
                onChange={introHandler}
                placeholder="ex: 우리집 맛집이에요 ! 많이들 드시러 오세요"
              ></textarea>
            </div>

            <div className={styles.storeInfo__input__grid4}>
              <div>
                <span>휴무일</span>
              </div>
              <input
                readOnly={canEdit === false}
                value={holiday === null ? "" : holiday}
                onChange={holidayHandler}
                placeholder="ex:매주 월요일"
              />
            </div>
            <div className={styles.storeInfo__input__grid5}>
              <div>
                <span>매장 전화번호</span>
              </div>
              <input
                readOnly={canEdit === false}
                value={phone === null ? "" : phone}
                onChange={phoneHandler}
                placeholder="ex:031-947-3334"
              />
            </div>
            <div className={styles.storeInfo__input__grid6}>
              <div>
                <span>매장 위치</span>
              </div>
              <div className={styles.grid6__div2}>
                <input
                  readOnly={canEdit === false}
                  value={address === null ? "" : address}
                  placeholder="ex: 서울특별시 동작구 밤리단길 369 B1"
                />
                {canEdit && <button onClick={toggle}>주소검색</button>}
              </div>
            </div>
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
          <div className={styles.storeInfo__input__btn}>
            {!canEdit && <button onClick={changeBtnHandler}>수정</button>}
            {canEdit && <button onClick={editSubmitHandler}>저장</button>}
            {canEdit && <button onClick={cancelHandler}>취소</button>}
          </div>
        </form>
      )}
    </div>
  );
};

export default StoreInfo;
