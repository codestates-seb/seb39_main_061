// import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import BarChart from "../../components/BarChart/BarChart";
import DashboardCalendar from "../../components/Calendar/Calendar";

const Dashboard = () => {

  return (
    <div>
      <Sidebar />
      <h1>Dashboard</h1>
      <div>
        <div>
          <BarChart />
          <button>Download</button>
        </div>
        <div>
          <DashboardCalendar />
        </div>
        <div>
          {/* QR 코드 리스트는 QR코드 리스트 컴포넌트 연결(주영님 작업중) */}
          <div>QR Code List</div>
        </div>
        <div>파이 그래프</div>
      </div>
    </div>
  );
};
export default Dashboard;
