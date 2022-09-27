import Chart from "react-apexcharts";
import moment from 'moment';
import styles from "./MonthApexChart.module.css";


const data = {
  series:
  {
    month: [123, 143, 90, 101, 87, 120, 95],
    cancelData: [5, 7, 3, 6, 7, 8, 5]
  }
}

const MonthApexChart = () => {
  // 그래프 하단 요일 출력
  const beforeMonth = () => {
    const today = moment(new Date()).format("MM/DD")
    let rendering = [today];
    for (let i = 1; i < 7; i++) {
      rendering.unshift(moment(new Date()).subtract([i], 'days').format("MM/DD"))
    }
    return rendering;
  }

  const series = [
    {
      name: "월간 예약자 수",
      data: data.series.month
    },
    {
      name: "월간 예약 취소자 수",
      data: data.series.cancelData
    }
  ];
  const options = {
    chart: {
      id: "simple-bar"
    },
    xaxis: {
      categories: beforeMonth()
    },
    colors: [ '#256D85', '#e6ce66', '#94A74A', '#256D85', '#e6ce66', '#e69166']
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