import React, { useCallback } from "react";
import Chart from "react-apexcharts";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

const ApexChart = () => {
  const timeData = useSelector(state => state.dashboard.time)
<<<<<<< HEAD
  const clickData = useSelector(state => state.dashboard.clickDate)
  const [time, setTime] = useState([]);
  console.log("달력에서 클릭한 날짜:", clickData)
  console.log("달력에서 클릭한 날짜 데이터:", timeData)
  // console.log(time)

  // ntime = 
  // 0:{ deleted: 'N', date: '17', count: 5 }
  // 1:{ deleted: 'N', date: '18', count: 5 }
  const nTime = timeData.filter(day => day.deleted === "N")

  const timeHandler = useCallback(async () => {
    console.log(nTime)
    let hours = [0,0,0,0,0,0,0,0];
    console.log(nTime)
    if (nTime.length !== 0) {
      console.log(nTime[0])
      for (let i = 0; i < nTime.length; i++) {
        let dated = nTime[i].date // 시간
        let count = nTime[i].count // 예약자 수
        if (dated >= 21) {
          hours[7] = count
        } 
        if (dated < 21 && dated >= 18) {
          hours[6] = count
        } 
        if (dated < 18 && dated >= 15) {
          hours[5] = count
        } 
        if (dated < 15 && dated >= 12) {
          hours[4] = count
        } 
        if (dated < 12 && dated >= 9) {
          hours[3] = count
        } 
        if (dated < 9 && dated >= 6) {
          hours[2] = count
        } 
        if (dated < 6 && dated >= 3) {
          hours[1] = count
        } 
        if (dated < 3) {
          hours[0] = count
        }
      }
      console.log(hours)
      setTime(hours)
    } else {
      setTime(hours)
    }
    // console.log(hours)
  }, [nTime])

  useEffect(() => {
    timeHandler()
    // setTimeout(timeHandler, 3000)
  }, [timeData])

=======
  console.log(timeData)
  const [time, setTime] = useState([]);

  // useEffect(() => {
  //   const nTime = timeData.filter(day => day.deleted === "N")
  //   console.log(nTime)
  //   const timeHandler = () => {
  //     let hours = [];
  //     for (let i = 0; i <= 7; i++) {
  //       let date = nTime[i].date
  //       let count = nTime[i].count
  //       if (date > 21) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       if (date <= 21 && date > 18) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       if (date <= 18 && date > 15) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       if (date <= 15 && date > 12) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       if (date <= 12 && date > 9) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       if (date <= 9 && date > 6) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       if (date <= 6 && date > 3) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       if (date < 3) {
  //         hours.unshift(count)
  //       } else {
  //         hours.unshift(0)
  //       }
  //       return setTime(hours)
  //     }
  //   }
  //   console.log(timeHandler())
  //   setTimeout(timeHandler, 500)
  // }, [timeData])
  // console.log(time)
  
>>>>>>> 145c87271e82f01f09dfbf385c17bc4c4710b77b
  const donutData = {
    // series: time,
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
      {time && <Chart options={donutData.options}
        series={donutData.series}
<<<<<<< HEAD
        // series={datehandler()}
        type="donut" width="95%" />}
=======
        type="donut" width="95%" />
>>>>>>> 145c87271e82f01f09dfbf385c17bc4c4710b77b
    </div>
  );
}

export default ApexChart;