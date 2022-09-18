import Chart from "react-apexcharts";

const data = {
  series: [43, 53, 50, 57],
}

const ApexChart = () => {
  const options = { labels: ["9월 1일", "9월 2일", "9울 3일", "9월 4일"] };
  const series = data.series; //our data
  return (
    <div>
      <Chart options={options} series={series} type="donut" width="380" />
    </div>
  );
}

export default ApexChart;