import Chart from "react-apexcharts";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

const ApexChart = () => {
  const timeData = useSelector(state => state.dashboard.time)
  console.log(timeData)
  const [time, setTime] = useState([]);

  useEffect(() => {
    const nTime = timeData.filter(day => day.deleted === "N")
    console.log(nTime)
    const timeHandler = () => {
      let hours = [];
      for (let i = 0; i <= 7; i++) {
        let date = nTime[i].date
        let count = nTime[i].count
        if (date > 21) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        if (date <= 21 && date > 18) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        if (date <= 18 && date > 15) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        if (date <= 15 && date > 12) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        if (date <= 12 && date > 9) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        if (date <= 9 && date > 6) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        if (date <= 6 && date > 3) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        if (date < 3) {
          hours.unshift(count)
        } else {
          hours.unshift(0)
        }
        return setTime(hours)
      }
    }
    console.log(timeHandler())
    setTimeout(timeHandler, 500)
  }, [timeData])
  console.log(time)
  
  const donutData = {
    series: time,
    // series: [0, 0, 3, 6, 10, 2, 13, 1],
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