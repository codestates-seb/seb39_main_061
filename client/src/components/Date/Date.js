import React from 'react';
import { useState } from 'react'

const Today = () => {
  const [daylist, setDaylist] = useState([]);
  const [weaklist, setWeaklist] = useState([]);

  const now = new Date();
  console.log(now.getDay());
  const todayWeak = now.getDay(); // 오늘 일자
  const today = now.getDate(); // 오늘 요일(숫자) 월요일은 1
  const lastday = new Date(now.getFullYear(), now.getMonth() + 1, 0).getDate();

  // const getAlldate = (today, lastday) => {
  //   let dates = [];
  //   dates[0] = today;
  //   for (let i = 1; i <= 6; i++) {
  //     today++;
  //     //마지막 날보다 날짜가 클경우 today를 1로 초기화.
  //     if (today > lastday) {
  //       today = 1;
  //       dates[i] = today;
  //     }
  //     //일반 경우 그냥 날짜 추가
  //     else {
  //       dates[i] = today;
  //     }
  //   }
  //   //요일 정상적으로 뜨는지 확인해보자
  //   console.log(dates[1].getDay());
  //   return dates;
  // };

  function getCurrentWeek() {
    const day = new Date();
    const sunday = day.getTime() - 86400000 * day.getDay();
  
    day.setTime(sunday);
  
    const result = [day.toISOString().slice(0, 10)];
  
    for (let i = 1; i < 7; i++) {
      day.setTime(day.getTime() + 86400000);
      result.push(day.toISOString().slice(0, 10));
    }
  
    return result;
  }
  
  console.log(getCurrentWeek());
  출처: https://gold-dragon.tistory.com/166 [계속 쓰는 개발 노트:티스토리]

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