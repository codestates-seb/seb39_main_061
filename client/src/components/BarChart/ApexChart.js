import Chart from "react-apexcharts";
import moment from 'moment';

const data = {
  series:
  {
    lastWeek: [43, 53, 50, 57, 35, 56, 23],
    currentWeek: [23, 52, 60, 37, 50, 20, 50]
  }
}

const ApexChart = () => {
  // 그래프 하단 요일 출력
  const beforeWeek = () => {
    const today = moment(new Date()).format("MM월 DD일 (ddd)")
    let redering = [today];
    console.log(redering)
    for (let i = 1; i < 7; i++) {
      redering.unshift(moment(new Date()).subtract([i], 'days').format("MM월 DD일 (ddd)"))
    }
    console.log(redering)
    return redering
  }

  const series = [
    {
      name: "지난주 예약자 수", //will be displayed on the y-axis
      data: data.series.lastWeek
    },
    {
      name: "이번주 예약자 수", //will be displayed on the y-axis
      data: data.series.currentWeek
    }
  ];
  const options = {
    chart: {
      id: "simple-bar"
    },
    xaxis: {
      categories: beforeWeek() //will be displayed on the x-asis
    }
  };
  return (
    <div>
      <Chart options={options} type="bar" series={series} width="60%" />
    </div>
  );
}

export default ApexChart;