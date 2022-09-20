// import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import WeekApexChart from "../../components/BarChart/WeekApexChart";
import MonthApexChart from "../../components/BarChart/MonthApexChart";
import Piechart from "../../components/PieChart/PieChart"
import DashboardCalendar from "../../components/Calendar/Calendar";
import { useState } from "react";


const Dashboard = () => {
  const [isBarChart, setIsBarChart] = useState(true);

  const weekBtnHandler = () => {
    setIsBarChart(true);
  }

  const monthBtnHandler = () => {
    setIsBarChart(false);
  }

  return (
    <div>
      <Sidebar />
      <h1>Dashboard</h1>
      <div>
        <div>
          <button onClick={weekBtnHandler}>Week</button>
          <button onClick={monthBtnHandler}>Month</button>
          {isBarChart ? <WeekApexChart /> : <MonthApexChart />}
        </div>
        <div>
          <DashboardCalendar />
        </div>
        <div>
          {/* QR 코드 리스트는 QR코드 리스트 컴포넌트 연결(주영님 작업중) */}
          <div>QR Code List</div>
        </div>
        <Piechart />
      </div>
    </div>
  );
};
export default Dashboard;
