import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import ChartBar from "../../components/ChartBar/ChartBar";
import DashboardCalendar from "../../components/Calendar/Calendar";

const Dashboard = () => {

  return (
    <div>
      <Sidebar />
      <h1>Dashboard</h1>
      <div>
        <div>
          <ChartBar />
          <button>Download</button>
        </div>
        <div>
          <DashboardCalendar />
        </div>
        <div>
          <div>QR Code List</div>
        </div>
        <div>파이 그래프</div>
      </div>
    </div>
  );
};
export default Dashboard;
