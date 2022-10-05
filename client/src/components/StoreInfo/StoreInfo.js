import React from 'react';
import styles from "./StoreInfo.module.css";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getBusinessInfo, postBusinessInfo } from "../../api/services/store";
import FindAddress from "../FindAddress/FindAddress";
import { useEffect } from "react";
import Modal from "react-modal";
import { mapActions } from "../../store/map";
import { authActions } from "../../store/auth";
import { businessActions } from "../../store/business";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretDown, faCaretUp } from "@fortawesome/free-solid-svg-icons";
import ConfirmModal from "../Modal/Modal";
import { modalActions } from "../../store/modal";
import SlideModal from "../SlideModal/SlideModal";

const StoreInfo = () => {
  const address = useSelector((state) => state.map.address);
  const lat = useSelector((state) => state.map.lat);
  const lon = useSelector((state) => state.map.lon);
  const startTime = useSelector((state) => state.business.startTime);
  const endTime = useSelector((state) => state.business.endTime);
  const startOrEnd = useSelector((state) => state.business.startOrEnd);
  const isModalOpen = useSelector((state) => state.modal.isModalOpen);

  const [canEdit, setCanEdit] = useState(false);
  const [name, setName] = useState("");
  const openTime = `${startTime}~${endTime}`;
  // const [holiday, setHoliday] = useState("");
  const holidayList = useSelector((state) => state.business.holidayList);

  const [phone, setPhone] = useState("");
  const [introduction, setIntroduction] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const businessId = useSelector((state) => state.business.businessId);

  const [isLoading, setIsLoading] = useState(false);
  const [startPickerOpen, setStartPickerOpen] = useState(false);
  const [endPickerOpen, setEndPickerOpen] = useState(false);
  const [dayPickerOpen, setDayPickerOpen] = useState(false);
  const dispatch = useDispatch();
  const checkOpenTime = useSelector((state) => state.business.checkOpenTime);

  //
  const timePickerToggle = (num) => {
    if (num === 1 && canEdit) {
      dispatch(businessActions.setStartOrEnd(1));
      setStartPickerOpen(!startPickerOpen);
    }
    if (num === 2 && canEdit) {
      dispatch(businessActions.setStartOrEnd(2));

      setEndPickerOpen(!endPickerOpen);
    }
  };
  //
  const dayPickerToggle = () => {
    setDayPickerOpen(!dayPickerOpen);
  };

  useEffect(() => {
    getBusinessInfo()
      .then((res) => {
        console.log("비즈니스인포 제일 처음?", res.data.data);
        dispatch(businessActions.setBusinessId(res.data.data.businessId));
        setName(res.data.data.name);
        dispatch(businessActions.setCheckOpenTime(res.data.data.openTime));
        if (res.data.data.holiday !== null) {
          const splitHoliday = res.data.data.holiday.split(",");
          dispatch(businessActions.setHolidayList([...splitHoliday]));
        }

        setPhone(res.data.data.phone);
        setIntroduction(res.data.data.introduction);
        setIntroduction(res.data.data.introduction);
        setIntroduction(res.data.data.introduction);
        dispatch(mapActions.setlat(res.data.data.lat));
        dispatch(mapActions.setlon(res.data.data.lon));
        dispatch(mapActions.setAddress(res.data.data.address));
        setIsLoading(true);
        if (res.data.data.openTime !== null) {
          const splitTime = res.data.data.openTime.split("~");
          const startTime = splitTime[0].substr(0, 6);
          const endTime = splitTime[1].substr(0, 6);
          dispatch(businessActions.setStartTime(startTime));
          dispatch(businessActions.setEndTime(endTime));
        }
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
    console.log("매장정보 수정 요청!");
    // 1. 완료버튼을 누르면 유효성검사하고
    // 2. Axios patch로 수정 전송!
    // 3. 수정전송이 완료되고 200ok면 버튼을 수정으로 바꾸고 새로고침 되게 만들기
    setDayPickerOpen(false);

    //holiday 다시 string으로 바꾸고 전송

    let holiday = "";
    if (holidayList.length === 2) {
      holiday = `${holidayList[0]},${holidayList[1]}`;
    }
    if (holidayList.length === 1) {
      holiday = `${holidayList[0]}`;
    }

    console.log("휴무일은?", holiday);
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
    ).catch((err) => {
      alert("수정에 실패하였습니다 잠시후 다시 시도해주세요");
      window.location.reload();
    });
    if (res) {
      console.log("매장정보 수정?", res);
      setCanEdit(true);
      dispatch(modalActions.setIsModalOpen(true));
      setTimeout(() => {
        window.location.reload();
      }, 1500);
    }
  };
  const nameHandler = (e) => {
    setName(e.target.value);
  };

  const phoneHandler = (e) => {
    setPhone(
      e.target.value
        .replace(/[^0-9]/g, "")
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`)
    );
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
      backgroundColor: "rgba(0,0,0,0.2)",
      marginLeft: "310px",
    },
    content: {
      marginLeft: "690px",
      left: "0",
      margin: "auto",
      width: "750px",
      height: "600px",
      padding: "0",
      borderRadius: "20px",
      paddingLeft: "20px",
    },
  };

  return (
    <div className={styles.storeInfo__container}>
      {isLoading && (
        <form onSubmit={storeSubmitHanlder} className={styles.StoreInfo}>
          <div className={styles.storeInfo__title}>
            <h1>매장 정보</h1>
          </div>
          <div className={styles.storeInfo__input}>
            <div className={styles.storeInfo__input__grid1}>
              <div>
                <span>이름</span>
              </div>
              <input
                className={
                  canEdit
                    ? (styles.grid1__input, styles.canEdit)
                    : styles.grid1__input
                }
                readOnly={canEdit === false}
                value={name === null ? "" : name}
                onChange={nameHandler}
                placeholder="ex:덕이네 곱창"
                spellCheck={false}
                maxLength={15}
              />
            </div>
            <div className={styles.storeInfo__input__grid2}>
              <div>
                <span>영업 시간</span>
              </div>
              <>
                {startPickerOpen === true && canEdit ? (
                  <SlideModal
                    modalNum={1}
                    open={startPickerOpen}
                    setOpen={setStartPickerOpen}
                  ></SlideModal>
                ) : null}
                {endPickerOpen === true && canEdit ? (
                  <SlideModal
                    open={endPickerOpen}
                    modalNum={1}
                    setOpen={setEndPickerOpen}
                  ></SlideModal>
                ) : null}
              </>
              <input
                readOnly
                spellCheck={false}
                value={checkOpenTime !== null ? "~" : ""}
                draggable={false}
                className={
                  canEdit
                    ? (styles.grid2__input, styles.canEdit)
                    : styles.grid2__input
                }
                placeholder={checkOpenTime === null ? "10:00 ~ 21:00" : ""}
              />
              {canEdit && (
                <span
                  onClick={() => {
                    timePickerToggle(1);
                  }}
                  className={styles.startIcon}
                >
                  {startPickerOpen === true ? (
                    <FontAwesomeIcon icon={faCaretUp} />
                  ) : (
                    <FontAwesomeIcon icon={faCaretDown} />
                  )}
                </span>
              )}

              {checkOpenTime !== null && (
                <button className={styles.startTimeBtn}>{startTime}</button>
              )}
              {canEdit && (
                <span
                  onClick={() => {
                    timePickerToggle(2);
                  }}
                  className={styles.endIcon}
                >
                  {endPickerOpen === true ? (
                    <FontAwesomeIcon icon={faCaretUp} />
                  ) : (
                    <FontAwesomeIcon icon={faCaretDown} />
                  )}
                </span>
              )}

              {checkOpenTime !== null && (
                <button className={styles.endTimeBtn}>{endTime}</button>
              )}
            </div>
            <div className={styles.storeInfo__input__grid3}>
              <div>
                <span>소개글</span>
              </div>
              <textarea
                className={
                  canEdit
                    ? (styles.grid3__textArea, styles.grid3__canEdit)
                    : styles.grid3__textArea
                }
                spellCheck="false"
                maxLength={90}
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
                spellCheck="false"
                value={holidayList === null ? "" : holidayList}
                placeholder="ex:매주 월요일"
                className={
                  canEdit
                    ? (styles.grid4__input, styles.canEdit)
                    : styles.grid4__input
                }
              />
              <>
                {dayPickerOpen === true && canEdit ? (
                  <SlideModal
                    modalNum={2}
                    open={dayPickerOpen}
                    setOpen={setDayPickerOpen}
                  ></SlideModal>
                ) : null}
              </>
              {canEdit && (
                <span
                  onClick={() => {
                    dayPickerToggle();
                  }}
                  className={styles.endIcon}
                >
                  {dayPickerOpen === true ? (
                    <FontAwesomeIcon icon={faCaretUp} />
                  ) : (
                    <FontAwesomeIcon icon={faCaretDown} />
                  )}
                </span>
              )}
            </div>
            <div className={styles.storeInfo__input__grid5}>
              <div>
                <span>전화번호 (-제외)</span>
              </div>
              <input
                onInput={(e) => {
                  if (e.target.value.length > e.target.maxLength)
                    e.target.value = e.target.value.slice(
                      0,
                      e.target.maxLength
                    );
                }}
                maxLength={14}
                readOnly={canEdit === false}
                spellCheck="false"
                value={phone === null ? "" : phone}
                onChange={phoneHandler}
                placeholder="ex:0319473334"
                className={
                  canEdit
                    ? (styles.grid5__input, styles.canEdit)
                    : styles.grid5__input
                }
              />
            </div>
            <div className={styles.storeInfo__input__grid6}>
              <div>
                <span>위치</span>
              </div>
              <div className={styles.grid6__div2}>
                <input
                  className={styles.grid6__input}
                  spellCheck="false"
                  readOnly
                  value={address === null ? "" : address}
                  placeholder="ex: 서울특별시 동작구 밤리단길 369 B1"
                />
                {canEdit && <button onClick={toggle}>검색</button>}
              </div>
            </div>

            <Modal
              // onClick={console.log("click")}

              isOpen={isOpen}
              ariaHideApp={false}
              style={customStyles}
              onRequestClose={toggle}
            >
              <FindAddress toggle={toggle} />
            </Modal>
          </div>
          <div className={styles.storeInfo__input__btn}>
            <div>
              {!canEdit && <button onClick={changeBtnHandler}>수정</button>}
              {canEdit && <button onClick={editSubmitHandler}>저장</button>}
              {canEdit && <button onClick={cancelHandler}>취소</button>}
            </div>
          </div>
          {isModalOpen && <ConfirmModal num={5} />}
        </form>
      )}
    </div>
  );
};

export default StoreInfo;
