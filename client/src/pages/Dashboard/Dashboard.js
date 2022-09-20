// import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import WeekApexChart from "../../components/BarChart/WeekApexChart";
import MonthApexChart from "../../components/BarChart/MonthApexChart";
import Piechart from "../../components/PieChart/PieChart"
import DashboardCalendar from "../../components/Calendar/Calendar";
import { useState, useEffect } from "react";
import axios from "axios";


const Dashboard = () => {
  const [isBarChart, setIsBarChart] = useState(true);
  const [weekCount, setWeekCount] = useState([]);
  const [monthCount, setMonthCount] = useState([]);


  const weekBtnHandler = () => {
    setIsBarChart(true);
  }

  const monthBtnHandler = () => {
    setIsBarChart(false);
  }

  const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJob25lc3R5NDA3QGdtYWlsLmNvbSIsInJvbGUiOiJST0xFX0dVRVNUIiwiaWF0IjoxNjYzMjk2NDUzLCJleHAiOjE2NjMzMDAwNTN9.OjAoWxek5iG8sGZOp5saXNGNpxYghLFV6sTdEPZg6jjc9pTAMgg67YgQdg48AaMKpVpZ6kJdPnt63HFP-76i8Q"

  useEffect(() => {
    axios.get("http://localhost:8080/api/v1/members/profile",
      {
        headers: { Authorization: "Bearer " + token }
      }
    )
      .then(userData => {
        console.log(userData.data.data)

      })
  }, [])


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
