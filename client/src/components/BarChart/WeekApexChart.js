import Chart from "react-apexcharts";
import moment from 'moment';
import styles from "./WeekApexChart.module.css";
import { useSelector } from "react-redux";

// 더미데이터
// const data = {
//   series:
//   {
//     week: [23, 30, 60, 37, 50, 20, 50],
//     cancelData: [5, 10, 3, 6, 7, 8, 5]
//   }
// }

const WeekApexChart = () => {
  const weekData = useSelector(state => state.dashboard.week)

  // 주간 예약자 통계
  const weekBook = () => {
    const Nweek = weekData.filter(day => day.deleted === "N")
    const arr = []
    for (let i = 0; i < 7; i++) {
      if (Nweek[i] === undefined) {
        arr.unshift(0)
      } else {
        arr.unshift(Nweek[i].count)
      }
    }
    return arr
  }
  console.log(weekBook())

  // 주간 취소자 통계
  const weekCancel = () => {
    const Yweek = weekData.filter(day => day.deleted === "Y")
    const arr = []
    for (let i = 0; i < 7; i++) {
      if (Yweek[i] === undefined) {
        arr.unshift(0)
      } else {
        arr.unshift(Yweek[i].count)
      }
    }
    return arr
  }
  console.log(weekCancel())

  // 그래프 X 축 일자 출력
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
      data: weekBook()
    },
    {
      name: "주간 예약 취소자 수",
      data: weekCancel(),
    }
  ];
  const options = {
    chart: {
      id: "simple-bar"
    },
    xaxis: {
      categories: beforeWeek()
    },
    colors: ['#e69166', '#669DB5', '#94A74A', '#256D85', '#e6ce66', '#e69166']
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