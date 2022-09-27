// import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import WeekApexChart from "../../components/BarChart/WeekApexChart";
import MonthApexChart from "../../components/BarChart/MonthApexChart";
import Piechart from "../../components/PieChart/PieChart"
import DashboardCalendar from "../../components/Calendar/Calendar";
import { useState, useEffect } from "react";
import axios from "axios";
import styles from "./Dashboard.module.css";

const Dashboard = () => {
  const [isBarChart, setIsBarChart] = useState(true);

  const weekBtnHandler = () => {
    setIsBarChart(true);
  }

  const monthBtnHandler = () => {
    setIsBarChart(false);
  }

  const token = ""

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
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.main_container}>
      <h1 className={styles.title}>Dashboard</h1>
        <div className={styles.container}>
          <div className={styles.component}>
            <div>
              <h3 className={styles.h3}>주간/월간 예약 현황</h3>
              <div className={styles.barChartsBtn}>
                <button onClick={weekBtnHandler}>Week</button>
                <button onClick={monthBtnHandler}>Month</button>
              </div>
              <div>
                {isBarChart ? <WeekApexChart className={styles.barChart} /> : <MonthApexChart className={styles.barChart} />}
              </div>
            </div>
          </div>
          <div className={styles.component}>
            <h3 className={styles.h3}>Calendar</h3>
            <DashboardCalendar />
          </div>
        </div>
        <div className={styles.container}>
          <div className={styles.component}>
            {/* QR 코드 리스트는 QR코드 리스트 컴포넌트 연결(주영님 작업중) */}
            <h3 className={styles.h3}>QR 코드 목록</h3>
          </div>
          <div className={styles.component}>
            <h3 className={styles.h3}>시간 별 예약 현황</h3>

            <Piechart />
          </div>
        </div>
      </div>
    </div>
  );
};
export default Dashboard;
