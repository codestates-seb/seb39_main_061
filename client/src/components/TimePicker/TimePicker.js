import styles from "./TimePicker.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import {
  faChevronUp,
  faChevronDown,
  faXmark,
} from "@fortawesome/free-solid-svg-icons";
import { useDispatch, useSelector } from "react-redux";
import { businessActions } from "../../store/business";
import React, { useEffect } from "react";

const TimePicker = ({ setOpen }) => {
  const startTime = useSelector((state) => state.business.startTime);
  const endTime = useSelector((state) => state.business.endTime);
  const startOrEnd = useSelector((state) => state.business.startOrEnd);
  console.log("엔드타임", endTime);
  console.log("분기처리", startOrEnd);

  const splitStartTime = startTime.split(":");
  const splitEndTime = endTime.split(":");
  console.log("스플릿 스타트타임", splitStartTime);
  console.log("스플릿 엔드타임", splitEndTime);

  const [h, setH] = useState("00");
  const [m, setM] = useState("00");
  const [hour, setHour] = useState(0);
  const [minute, setMinute] = useState(0);

  const dispatch = useDispatch();
  useEffect(() => {
    // 시작타임, 마감타임 분기처리
    if (startOrEnd === 1) {
      setH(splitStartTime[0]);
      setHour(Number(splitStartTime[0]));
      setMinute(Number(splitStartTime[1]));
      setM(splitStartTime[1]);
    }
    if (startOrEnd === 2) {
      setH(splitEndTime[0]);
      setHour(Number(splitEndTime[0]));
      setMinute(Number(splitEndTime[1]));
      setM(splitEndTime[1]);
    }
  }, []);

  if (hour > 23) {
    setHour(0);
    setH(String(0).padStart(2, "0"));
  }
  if (hour < 0) {
    setHour(23);
    setH(String(23).padStart(2, "0"));
  }
  if (minute > 55) {
    setMinute(0);
    setM(String(0).padStart(2, "0"));
  }
  if (minute < 0) {
    setMinute(55);
    setM(String(55).padStart(2, "0"));
  }
  const hourUpHandler = () => {
    setH(String(hour + 1).padStart(2, "0"));
    setHour(hour + 1);
  };

  const hourDownHandler = () => {
    setH(String(hour - 1).padStart(2, "0"));
    setHour(hour - 1);
  };
  const minuteUpHandler = () => {
    setM(String(minute + 5).padStart(2, "0"));
    setMinute(minute + 5);
  };

  const minuteDownHandler = () => {
    setM(String(minute - 5).padStart(2, "0"));
    setMinute(minute - 5);
  };

  // 고른 시간을 선택해서 리덕스 스토어에 디스패치
  const selectStartTime = () => {
    console.log("시작 시간", hour);
    console.log("시작 분", minute);
    if (startOrEnd === 1) {
      dispatch(businessActions.setStartTime(`${h}:${m}`));
      dispatch(businessActions.setCheckOpenTime(1));
      setOpen(false);
    }
    if (startOrEnd === 2) {
      dispatch(businessActions.setEndTime(`${h}:${m}`));
      dispatch(businessActions.setCheckOpenTime(2));
      setOpen(false);
    }
  };

  return (
    <div className={styles.timePicker}>
      <div className={styles.timePicker__title}>
        <div>{startOrEnd === 1 ? "오픈시간" : "마감시간"}</div>
        <span
          onClick={() => {
            setOpen(false);
          }}
        >
          <FontAwesomeIcon icon={faXmark} />
        </span>
      </div>
      <div className={styles.timePicker__time}>
        <div className={styles.firstTime}>
          <div onClick={hourUpHandler}>
            <FontAwesomeIcon icon={faChevronUp} />
          </div>
          <div>{hour < 10 && hour >= 0 ? h : hour}</div>
          <div onClick={hourDownHandler}>
            <FontAwesomeIcon icon={faChevronDown} />
          </div>
        </div>
        <div className={styles.secondTime}>
          <div>:</div>
        </div>
        <div className={styles.thirdTime}>
          <div onClick={minuteUpHandler}>
            <FontAwesomeIcon icon={faChevronUp} />
          </div>
          <div>{minute < 10 && minute >= 0 ? m : minute}</div>
          <div onClick={minuteDownHandler}>
            <FontAwesomeIcon icon={faChevronDown} />
          </div>
        </div>
      </div>
      <div className={styles.timePicker__btn}>
        <button onClick={selectStartTime}>선택</button>
      </div>
    </div>
  );
};

export default TimePicker;
