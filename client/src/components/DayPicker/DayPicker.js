import dayPic from "./DayPicker.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { businessActions } from "../../store/business";

const DayPicker = () => {
  const holidayList = useSelector((state) => state.business.holidayList);
  const dispatch = useDispatch();
  console.log(holidayList);

  const checkTheDay = (day) => {
    let holiday = Array.from(holidayList);
    //1. 만약에 이미 리스트에 있는데 또 클릭했다면 리스트에서 삭제
    if (holiday.indexOf(day) !== -1) {
      holiday = holiday.filter((d) => {
        return d !== day;
      });
      dispatch(businessActions.setHolidayList(holiday));
      return;
    }
    //2.  2개 이상 선택하면 제일 먼저 선택한거 삭제
    if (holiday.length === 2) {
      holiday.shift();
    }

    //3. 들어온 day를 holiday에 추가
    if (day === "월요일") {
      console.log("월 드러옴");
      holiday = [...holiday, "월요일"];
      dispatch(businessActions.setHolidayList(holiday));
    }
    if (day === "화요일") {
      holiday = [...holiday, "화요일"];
      dispatch(businessActions.setHolidayList(holiday));
    }
    if (day === "수요일") {
      holiday = [...holiday, "수요일"];
      dispatch(businessActions.setHolidayList(holiday));
    }
    if (day === "목요일") {
      holiday = [...holiday, "목요일"];
      dispatch(businessActions.setHolidayList(holiday));
    }
    if (day === "금요일") {
      holiday = [...holiday, "금요일"];
      dispatch(businessActions.setHolidayList(holiday));
    }
    if (day === "토요일") {
      holiday = [...holiday, "토요일"];
      dispatch(businessActions.setHolidayList(holiday));
    }
    if (day === "일요일") {
      holiday = [...holiday, "일요일"];
      dispatch(businessActions.setHolidayList(holiday));
    }
  };

  return (
    <div className={dayPic.dayPicker}>
      <div
        onClick={() => {
          checkTheDay("월요일");
        }}
        className={dayPic.dayPicker__mon}
      >
        <span>월요일</span>
        {holidayList.indexOf("월요일") !== -1 && (
          <FontAwesomeIcon icon={faCheck} />
        )}
      </div>
      <div
        onClick={() => {
          checkTheDay("화요일");
        }}
        className={dayPic.dayPicker__tue}
      >
        <span>화요일</span>
        {holidayList.indexOf("화요일") !== -1 && (
          <FontAwesomeIcon icon={faCheck} />
        )}
      </div>
      <div
        onClick={() => {
          checkTheDay("수요일");
        }}
        className={dayPic.dayPicker__wed}
      >
        <span>수요일</span>
        {holidayList.indexOf("수요일") !== -1 && (
          <FontAwesomeIcon icon={faCheck} />
        )}
      </div>
      <div
        onClick={() => {
          checkTheDay("목요일");
        }}
        className={dayPic.dayPicker__thu}
      >
        <span>목요일</span>
        {holidayList.indexOf("목요일") !== -1 && (
          <FontAwesomeIcon icon={faCheck} />
        )}
      </div>
      <div
        onClick={() => {
          checkTheDay("금요일");
        }}
        className={dayPic.dayPicker__fri}
      >
        <span>금요일</span>
        {holidayList.indexOf("금요일") !== -1 && (
          <FontAwesomeIcon icon={faCheck} />
        )}
      </div>
      <div
        onClick={() => {
          checkTheDay("토요일");
        }}
        className={dayPic.dayPicker__sat}
      >
        <span>토요일</span>
        {holidayList.indexOf("토요일") !== -1 && (
          <FontAwesomeIcon icon={faCheck} />
        )}
      </div>
      <div
        onClick={() => {
          checkTheDay("일요일");
        }}
        className={dayPic.dayPicker__sun}
      >
        <span>일요일</span>
        {holidayList.indexOf("일요일") !== -1 && (
          <FontAwesomeIcon icon={faCheck} />
        )}
      </div>
    </div>
  );
};

export default DayPicker;
