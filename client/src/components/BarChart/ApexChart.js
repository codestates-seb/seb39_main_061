import Chart from "react-apexcharts";
import moment from 'moment';

const data = {
  series:
  {
    lastWeek: [43, 53, 50, 57, 35, 56, 23],
    currentWeek: [23, 52, 60, 37, 50, 20, 50],
    cancelData: [5, 7, 3, 6, 7, 8, 5]
  }
}

const ApexChart = () => {
  // 그래프 하단 요일 출력
  const beforeWeek = () => {
    const today = moment(new Date()).format("MM/DD")
    let rendering = [today];
    console.log(rendering)
    for (let i = 1; i < 7; i++) {
      rendering.unshift(moment(new Date()).subtract([i], 'days').format("MM/DD"))
    }
    console.log(rendering)
    return rendering;
  }

  const series = [
    {
      name: "지난주 예약자 수",
      data: data.series.lastWeek
    },
    {
      name: "이번주 예약자 수",
      data: data.series.currentWeek
    },
    {
      name: "예약 취소자 수",
      data: data.series.cancelData
    }
  ];
  const options = {
    chart: {
      id: "simple-bar"
    },
    xaxis: {
      categories: beforeWeek()
    }
  };
  return (
    <div>
      <Chart options={options} type="bar" series={series} width="55%" />
    </div>
  );
}

export default ApexChart;