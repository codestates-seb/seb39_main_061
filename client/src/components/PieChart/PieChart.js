import React from "react";
import Chart from "react-apexcharts";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

// 더미데이터 테스트
// const data = {
//   series:
//   {
//     month:
//       ["20221007", "20221008", "20221009", "20221010", "20221011", "20221012"],
//   }
// }
// const six = [0, 0, 5, 4, 1, 7, 3]
// const seven = [0, 0, 4, 8, 2, 9, 2]
// const eight = [0, 0, 4, 8, 2, 9, 2]
// const nine = [0, 0, 4, 8, 2, 9, 2]
// const ten = [0, 0, 4, 8, 2, 9, 2]
// const eleven = [0, 0, 4, 8, 2, 9, 2]
// const datehandler = () => {
//   if (data.series.month[0] === timeData) {
//     return six
//   } else if (data.series.month[1] === timeData) {
//     return seven
//   } else if (data.series.month[2] === timeData) {
//     return eight
//   } else if (data.series.month[3] === timeData) {
//     return nine
//   } else if (data.series.month[4] === timeData) {
//     return ten
//   } else if (data.series.month[5] === timeData) {
//     return eleven
//   }
// }
// console.log(datehandler())

const ApexChart = () => {
  const timeData = useSelector(state => state.dashboard.time)
  const clickData = useSelector(state => state.dashboard.clickDate)
  const [time, setTime] = useState([]);
  // console.log("달력에서 클릭한 날짜:", clickData)
  // console.log("달력에서 클릭한 날짜 데이터:", timeData)
  // console.log(time)

  useEffect(() => {
    // const nTime = timeData.filter(day => day.deleted === "N")
    // ntime = 
    // 0:{ deleted: 'N', date: '17', count: 5 }
    // 1:{ deleted: 'N', date: '18', count: 5 }
    // const timeHandler = () => {
    //   let hours = [];
    //   for (let i = 0; i <= 7; i++) {
    //     let dated = nTime[i].date
    //     let count = nTime[i].count
    //     if (dated >= 21) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     if (dated < 21 && dated >= 18) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     if (dated < 18 && dated >= 15) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     if (dated < 15 && dated >= 12) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     if (dated < 12 && dated >= 9) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     if (dated < 9 && dated >= 6) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     if (dated < 6 && dated >= 3) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     if (dated < 3) {
    //       hours.unshift(count)
    //     } else {
    //       hours.unshift(0)
    //     }
    //     return setTime(hours)
    //   }
    // }
    // console.log(timeHandler())
    // setTimeout(timeHandler, 30000)
  }, [])
  // console.log(time)

  const donutData = {
    series: time,
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
        // series={datehandler()}
        type="donut" width="95%" />
    </div>
  );
}

export default ApexChart;