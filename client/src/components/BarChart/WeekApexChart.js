import Chart from "react-apexcharts";
import moment from 'moment';
import styles from "./WeekApexChart.module.css";
import React from "react";
import { useSelector, useDispatch } from "react-redux";
// import { useEffect } from "react";
// import { getStatisticsChart } from "../../library/axios";
// import barCharts, { barChartsAction } from "../../store/barCharts";

const data = {
  series:
  {
    week: [23, 30, 60, 37, 50, 20, 50],
    cancelData: [5, 10, 3, 6, 7, 8, 5]
  }
}

const WeekApexChart = () => {
  const chartData = useSelector(state => state.barCharts)
  console.log(chartData)
  // 그래프 하단 요일 출력
  const beforeWeek = () => {
    const today = moment(new Date()).format("MM/DD")
    let rendering = [today];
    for (let i = 1; i < 7; i++) {
      rendering.unshift(moment(new Date()).subtract([i], 'days').format("MM/DD"))
    }
    return rendering;
  }

  const series = [
    {
      name: "주간 예약자 수",
      data: data.series.week
    },
    {
      name: "주간 예약 취소자 수",
      data: data.series.cancelData
    }
  ];
  const options = {
    chart: {
      id: "simple-bar"
    },
    xaxis: {
      categories: beforeWeek()
    },
    colors: [ '#e69166', '#669DB5', '#94A74A', '#256D85', '#e6ce66', '#e69166']
  };
  return (
    <div>
      <Chart className={styles.barChart}
        options={options}
        type="bar"
        series={series}
        width="95%"
      />
    </div>
  );
}

export default WeekApexChart;