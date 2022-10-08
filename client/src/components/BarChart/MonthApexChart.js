import React from "react";
import Chart from "react-apexcharts";
import moment from 'moment';
import styles from "./MonthApexChart.module.css";
import { useSelector } from "react-redux";


// 더미데이터
const data = {
  series:
  {
    month: [123, 143, 90, 101, 87, 120, 95],
    cancelData: [5, 7, 3, 6, 7, 8, 5]
  }
}

const MonthApexChart = () => {
  const monthData = useSelector(state => state.dashboard.month)
  // console.log(monthData)

  // 월간 예약자 통계
  const monthBook = () => {
    const nMonth = monthData.filter(month => month.deleted === "N")
    const nMonthSort = nMonth.reverse()
    const arr = []
    for (let i = 0; i < 12; i++) {
      if (nMonthSort[i] === undefined) {
        arr.unshift(0)
      } else {
        arr.unshift(nMonthSort[i].count)
      }
    }
    return arr
  }
  // console.log(monthBook())

  // 월간 취소자 통계
  const monthCancel = () => {
    const yMonth = monthData.filter(month => month.deleted === "Y")
    const yMonthSort = yMonth.reverse();
    const arr = []
    for (let i = 0; i < 12; i++) {
      if (yMonthSort[i] === undefined) {
        arr.unshift(0)
      } else {
        arr.unshift(yMonthSort[i].count)
      }
    }
    return arr
  }
  // console.log(monthCancel())

  // 그래프 X 축 월단위 출력
  const beforeMonth = () => {
    const today = moment(new Date()).format("MM월")
    let rendering = [today];
    for (let i = 1; i < 12; i++) {
      rendering.unshift(moment(new Date()).subtract([i], 'month').format("MM월"))
    }
    return rendering;
  }

  const series = [
    {
      name: "월간 예약자 수",
      data: monthBook()
      // data: data.series.month,
    },
    {
      name: "월간 예약 취소자 수",
      data: monthCancel()
      // data: data.series.cancelData,
    }
  ];
  const options = {
    chart: {
      id: "simple-bar"
    },
    xaxis: {
      categories: beforeMonth()
    },
    colors: ['#256D85', '#e6ce66', '#94A74A', '#256D85', '#e6ce66', '#e69166']
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

export default MonthApexChart;