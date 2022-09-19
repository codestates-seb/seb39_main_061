import Chart from "react-apexcharts";

const data = {
  series:
  {
    lastWeek: [43, 53, 50, 57, 35, 56, 23],
    currentWeek: [23, 52, 60, 37, 50, 20, 50]
  }
}

const ApexChart = () => {
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
      categories: ["9월 1일", "9월 2일", "9월 3일", "9월 4일", "9월 5일", "9월 6일", "9월 7일"] //will be displayed on the x-asis
    }
  };
  return (
    <div>
      <Chart options={options} type="bar" series={series} width="50%" />
    </div>
  );
}

export default ApexChart;