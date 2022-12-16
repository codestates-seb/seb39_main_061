import React from "react";
import Sidebar from "../../components/Sidebar/Sidebar";
import WeekApexChart from "../../components/BarChart/WeekApexChart";
import MonthApexChart from "../../components/BarChart/MonthApexChart";
import Piechart from "../../components/PieChart/PieChart";
import DashboardCalendar from "../../components/Calendar/Calendar";
import QRcodeManageDetail from "./../../components/QRmanageDetail/QRmanageDetail";

import Header from "../../components/Header/Header";
import { useState, useEffect } from "react";
import styles from "./Dashboard.module.css";
import { useDispatch, useSelector } from "react-redux";
import { dashboardActions } from "../../store/dashboard";
import { qrcodeActions } from "../../store/qrCode";
import { getDashboard } from "./../../api/services/dashboard";
import { getBusinessId, getQRcodeInfo } from "../../api/services/createQrcode";
import moment from "moment";
import { useNavigate } from "react-router-dom";
import Modal from "../../components/Modal/Modal";

const Dashboard = () => {
  const title = "대시보드";
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isBarChart, setIsBarChart] = useState(true);
  const [loading, setLoading] = useState(true);
  const [openModal, setOpenModal] = useState(false);
  // const [businessIdget, setBusinessIdget] = useState(0);
  // const [qrcodeIdget, setQrcodeIdget] = useState(0);
  const businessIdSelector = useSelector((state) => state.dashboard.businessId);
  const qrCodeIdSelector = useSelector((state) => state.dashboard.qrCodeId);
  const timeSelector = useSelector((state) => state.dashboard.time);

  // console.log("파라미터는?", window.location.pathname);
  // if (window.location.pathname === "/oauth2") {
  //   window.history.pushState("", "페이지타이틀", `/dashboard`);
  // }

  const weekBtnHandler = () => {
    setIsBarChart(true);
  };

  const monthBtnHandler = () => {
    setIsBarChart(false);
  };

  let today = moment().format("YYYYMMDD");

  const firstDataRendering = async () => {
    const resQrcodeId = await getQRcodeInfo(businessIdSelector);
    console.log(resQrcodeId);
    if (resQrcodeId.length !== 0) {
      const resDashboardData = await getDashboard(
        businessIdSelector,
        resQrcodeId[0].qrCodeId,
        today
      );
      console.log(resDashboardData);
      dispatch(dashboardActions.setMonth(resDashboardData.month));
      dispatch(dashboardActions.setWeek(resDashboardData.week));
      dispatch(dashboardActions.setTime(resDashboardData.time));
      setLoading(false);
    } else {
      setOpenModal(true);
      setTimeout(() => navigate("/qrcode-management"), 1500);
    }
  };

  useEffect(() => {
    getBusinessId().then((res) => {
      dispatch(qrcodeActions.setBusinessId(res.businessId));
      firstDataRendering();
    });
  }, []);

  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.main_container}>
        <Header title={title} className={styles.header} />
        {loading === false && (
          <div className={styles.contentsWrap}>
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
                <h3 className={styles.h3}>캘린더</h3>
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
        )}
        {openModal && <Modal num={10} />}
      </div>
    </div>
  );
};
export default Dashboard;
