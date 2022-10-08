import React from "react";
import Sidebar from "../../components/Sidebar/Sidebar";
import WeekApexChart from "../../components/BarChart/WeekApexChart";
import MonthApexChart from "../../components/BarChart/MonthApexChart";
import Piechart from "../../components/PieChart/PieChart";
import DashboardCalendar from "../../components/Calendar/Calendar";
import QRcodeManageDetail from "./../../components/QRmanageDetail/QRmanageDetail"

import Header from "../../components/Header/Header";
import { useState, useEffect } from "react";
import styles from "./Dashboard.module.css";
import { useDispatch } from "react-redux";
import { dashboardActions } from "../../store/dashboard";
import { getDashboard } from "./../../api/services/dashboard";
import moment from "moment";

const Dashboard = () => {
  const title = "대시보드";
  const dispatch = useDispatch();
  const [isBarChart, setIsBarChart] = useState(true);

  console.log("파라미터는?", window.location.pathname);
  if (window.location.pathname === "/oauth2") {
    window.history.pushState("", "페이지타이틀", `/dashboard`);
  }

  const weekBtnHandler = () => {
    setIsBarChart(true);
  };

  const monthBtnHandler = () => {
    setIsBarChart(false);
  };

  let today = moment().format("YYYYMMDD");

  useEffect(() => {
    getDashboard(today)
      .then((res) => {
        console.log(res);
        dispatch(dashboardActions.setMonth(res.month));
        dispatch(dashboardActions.setWeek(res.week));
        dispatch(dashboardActions.setTime(res.time));
      })
      .catch((err) => {
        if (err.response.data.status === 401) {
          alert("로그인 해주세요!");
        }
        // navigate("/login")
      });
  }, []);

  return (
    <div className={styles.container}>
      <Sidebar />

      <div className={styles.main_container}>
        <Header title={title} />
        <div className={styles.flex_container}>
          <div className={styles.componentSector}>
            <div className={styles.component}>
              <h3 className={styles.h3}>주간/월간 예약 현황</h3>
              <div className={styles.componentEl}>
                <div className={styles.barChartsBtn}>
                  <button onClick={weekBtnHandler}>Week</button>
                  <button onClick={monthBtnHandler}>Month</button>
                </div>
                <div>
                  {isBarChart ? (
                    <WeekApexChart className={styles.barChart} />
                  ) : (
                    <MonthApexChart className={styles.barChart} />
                  )}
                </div>
              </div>
            </div>
          </div>
          <div className={styles.component}>
            <h3 className={styles.h3}>Calendar</h3>
            <div className={styles.componentEl__calendar}>
              <DashboardCalendar className={styles.barChart} />
            </div>
          </div>
        </div>
        <div className={styles.flex_container}>
          <div className={styles.component}>
            {/* QR 코드 리스트는 QR코드 리스트 컴포넌트 연결(주영님 작업중) */}
            <h3 className={styles.h3}>QR 코드</h3>
            <div className={styles.componentEl__QR}>
              <QRcodeManageDetail />
            </div>
          </div>
          <div className={styles.component}>
            <h3 className={styles.h3}>시간 별 예약 현황</h3>
            <div className={styles.componentEl__piechart}>
              <Piechart />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Dashboard;
