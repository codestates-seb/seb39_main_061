import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { useState } from 'react';
import moment from 'moment';

const DashboardCalendar = () => {

  const [value, onChange] = useState(new Date());
  console.log(value)

  return (
    <div>
      <Calendar onChange={onChange} value={value} />
      <div>
        {/* 클릭한 날짜 하단에 표시 */}
        {moment(value).format("YYYY년 MM월 DD일")}
      </div>
    </div>
  );
};

export default DashboardCalendar;