import React from 'react';
import { useState } from 'react'

const Today = () => {
  const [daylist, setDaylist] = useState([]);
  const [weaklist, setWeaklist] = useState([]);

  const now = new Date();
  const todayWeak = now.getDay();
  const today = now.getDate();
  const lastday = new Date(now.getFullYear(), now.getMonth() + 1, 0).getDate();

  const getAlldate = (today, lastday) => {
    let dates = [];
    dates[0] = today;
    for (let i = 1; i <= 6; i++) {
      today++;
      //마지막 날보다 날짜가 클경우 today를 1로 초기화.
      if (today > lastday) {
        today = 1;
        dates[i] = today;
      }
      //일반 경우 그냥 날짜 추가
      else {
        dates[i] = today;
      }
    }
    //요일 정상적으로 뜨는지 확인해보자
    console.log(dates[1].getDay());
    return dates;
  };

  return (
    <div>
      {today}
      <br></br>
      {todayWeak}
      <br></br>
      {lastday}
    </div>
  );
};

export default Today;