import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import React, { useState } from 'react';
import moment from 'moment';
import styles from "./Calendar.module.css";

const data = [
  {
    date: 20220915,
    count: 20,
    id: 1
  },
  {
    date: 20220916,
    count: 10,
    id: 2
  },
  {
    date: 20220917,
    count: 26,
    id: 3
  },
]

const DashboardCalendar = () => {

  const [value, onChange] = useState(new Date());

  let clickDate = moment(value).format("YYYYMMDD")
  console.log(JSON.parse(clickDate))

  return (
    <div className={styles.calendar_container}>
      <Calendar
        onChange={onChange}
        value={value}
        className={styles.calendar}
      />
      <div className={styles.clickDate}>
        <div>
          {/* 클릭한 날짜 하단에 표시 */}
          {moment(value).format("YYYY년 MM월 DD일")}
        </div>
        <div>
          {data.map((item, idx) => {
            if (JSON.parse(clickDate) === item.date) {
              return <div key={idx}>{item.count}명</div>
            }
          })}
        </div>
      </div>
    </div>
  );
};

export default DashboardCalendar;