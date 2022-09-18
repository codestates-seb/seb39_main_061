import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { useState } from 'react';
import moment from 'moment';

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
  console.log(value)

  let clickDate = moment(value).format("YYYYMMDD")
  console.log(JSON.parse(clickDate))
  // console.log(clickDate)
  // console.log(data[0].date)
  // if(clickDate === data[0].date){

  // }

  return (
    <div>
      <Calendar onChange={onChange} value={value} />
      <div>
        {/* 클릭한 날짜 하단에 표시 */}
        {moment(value).format("YYYY년 MM월 DD일")}
        {data.map((item, idx) => {
          if (JSON.parse(clickDate) === item.date) {
            return <div key={idx}>{item.count}명</div>
          }
        })}
      </div>
    </div>
  );
};

export default DashboardCalendar;