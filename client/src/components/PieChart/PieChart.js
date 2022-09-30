import Chart from "react-apexcharts";

const ApexChart = () => {

  const donutData = {
    series: [0, 0, 3, 6, 10, 2, 13, 1],
    options: {
      chart: {
        type: 'donut',
      },
      legend: {
        position: 'bottom'
      },
      responsive: [{
        breakpoint: 480,
      }],
      plotOptions: {
        pie: {
          donut: {
            labels: {
              show: true,
              total: {
                showAlways: true,
                show: true,
                label: 'Total',
                fontSize: '20px',
                color: 'red'
              },
              value: {
                fontSize: '2rem',
                show: true,
                color: 'blue',
              },
            },
          }
        }
      },
      labels: ["00~03", "03~06", "06~09", "09~12", "12~15", "15~18", "18~21", "21~24"],
      title: {
        text: '',
        align: 'center'
      },
      colors: ['#93C3EE', '#E5C6A0', '#669DB5', '#94A74A', '#256D85', '#e6ce66', '#e69166']
    },
  }

  return (
    <div>
      <Chart options={donutData.options}
        series={donutData.series}
        type="donut" width="95%" />
    </div>
  );
}

export default ApexChart;