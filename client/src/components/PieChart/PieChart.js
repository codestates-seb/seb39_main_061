import Chart from "react-apexcharts";

// const data = {
//   series: [43, 53, 50, 57],
// }

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
            // hollow: {  
            //   margin: 15,
            //   size: '70%',
            //   image: '../../css/images/a-icon.jpg',
            //   imageWidth: 64,
            //   imageHeight: 64,
            //   imageClipped: false
            // },
            labels: {
              show: true,
              total: {
                showAlways: true,
                show: true,
                label: 'Total',
                fontSize: '15px',
                color: 'red'
              },
              value: {
                fontSize: '30px',
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

  // const options = { labels: ["9월 1일", "9월 2일", "9울 3일", "9월 4일"] };
  // const series = data.series; //our data
  return (
    <div>
      <Chart options={donutData.options}
        series={donutData.series}
        type="donut" width="95%" />
    </div>
  );
}

export default ApexChart;

// import React from 'react';
// import { ResponsivePie } from '@nivo/pie'

// const ApexChart = () => {
//   const data = [
//       {
//         "id": "javascript",
//         "label": "javascript",
//         "value": 461,
//         "color": "hsl(224, 70%, 50%)"
//       },
//       {
//         "id": "python",
//         "label": "python",
//         "value": 71,
//         "color": "hsl(268, 70%, 50%)"
//       },
//       {
//         "id": "sass",
//         "label": "sass",
//         "value": 197,
//         "color": "hsl(99, 70%, 50%)"
//       },
//       {
//         "id": "ruby",
//         "label": "ruby",
//         "value": 299,
//         "color": "hsl(290, 70%, 50%)"
//       },
//       {
//         "id": "c",
//         "label": "c",
//         "value": 333,
//         "color": "hsl(223, 70%, 50%)"
//       }
//     ]

//   return (
//     <div>
//       <ResponsivePie
//         data={data}
//         margin={{ top: 40, right: 80, bottom: 80, left: 80 }}
//         startAngle={-180}
//         innerRadius={0.5}
//         padAngle={0.7}
//         activeOuterRadiusOffset={8}
//         borderWidth={1}
//         borderColor={{
//           from: 'color',
//           modifiers: [
//             [
//               'darker',
//               '0.2'
//             ]
//           ]
//         }}
//         arcLinkLabelsTextOffset={7}
//         arcLinkLabelsTextColor="#333333"
//         arcLinkLabelsOffset={-11}
//         arcLinkLabelsDiagonalLength={26}
//         arcLinkLabelsStraightLength={29}
//         arcLinkLabelsThickness={3}
//         arcLinkLabelsColor={{ from: 'color' }}
//         arcLabelsTextColor={{
//           from: 'color',
//           modifiers: [
//             [
//               'darker',
//               '3'
//             ]
//           ]
//         }}
//         defs={[
//           {
//             id: 'dots',
//             type: 'patternDots',
//             background: 'inherit',
//             color: 'rgba(255, 255, 255, 0.3)',
//             size: 4,
//             padding: 1,
//             stagger: true
//           },
//           {
//             id: 'lines',
//             type: 'patternLines',
//             background: 'inherit',
//             color: 'rgba(255, 255, 255, 0.3)',
//             rotation: -45,
//             lineWidth: 6,
//             spacing: 10
//           }
//         ]}
//         fill={[
//           {
//             match: {
//               id: 'ruby'
//             },
//             id: 'dots'
//           },
//           {
//             match: {
//               id: 'c'
//             },
//             id: 'dots'
//           },
//           {
//             match: {
//               id: 'go'
//             },
//             id: 'dots'
//           },
//           {
//             match: {
//               id: 'python'
//             },
//             id: 'dots'
//           },
//           {
//             match: {
//               id: 'scala'
//             },
//             id: 'lines'
//           },
//           {
//             match: {
//               id: 'lisp'
//             },
//             id: 'lines'
//           },
//           {
//             match: {
//               id: 'elixir'
//             },
//             id: 'lines'
//           },
//           {
//             match: {
//               id: 'javascript'
//             },
//             id: 'lines'
//           }
//         ]}
//         legends={[
//           {
//             anchor: 'right',
//             direction: 'column',
//             justify: false,
//             translateX: -49,
//             translateY: 10,
//             itemsSpacing: 3,
//             itemWidth: 101,
//             itemHeight: 31,
//             itemTextColor: '#999',
//             itemDirection: 'left-to-right',
//             itemOpacity: 1,
//             symbolSize: 15,
//             symbolShape: 'circle',
//             effects: [
//               {
//                 on: 'hover',
//                 style: {
//                   itemTextColor: '#000'
//                 }
//               }
//             ]
//           }
//         ]}
//       />
//     </div>
//   )
// }

// export default ApexChart;