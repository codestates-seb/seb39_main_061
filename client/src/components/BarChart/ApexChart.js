import Chart from "react-apexcharts";

const data = {
  series: [43, 53, 50, 57],
}

const ApexChart = () => {
  const series = [
    {
      name: "예약대기 수", //will be displayed on the y-axis
      data: data.series
    }
  ];
  const options = {
    chart: {
      id: "simple-bar"
    },
    xaxis: {
      categories: ["9월 1일", "9월 2일", "9월 3일", "9월 4일"] //will be displayed on the x-asis
    }
  };
  return (
    <div>
      <Chart options={options} type="bar" series={series} width="50%" />
    </div>
  );
}

export default ApexChart;