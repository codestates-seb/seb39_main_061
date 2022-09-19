// import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import ApexChart from "../../components/BarChart/ApexChart";
import Today from "../../components/Date/Date"
import Piechart from "../../components/PieChart/PieChart"
import DashboardCalendar from "../../components/Calendar/Calendar";



const Dashboard = () => {

  return (
    <div>
      <Sidebar />
      <h1>Dashboard</h1>
      <div>
        <div>
          <ApexChart />
          {/* <button>Download</button> */}
        </div>
        <div>
          <DashboardCalendar />
        </div>
        <div>
          {/* QR 코드 리스트는 QR코드 리스트 컴포넌트 연결(주영님 작업중) */}
          <div>QR Code List</div>
        </div>
        <Piechart />
        <Today />
      </div>
    </div>
  );
};
export default Dashboard;
