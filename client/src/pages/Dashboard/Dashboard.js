import Sidebar from "../../components/Sidebar/Sidebar";
import { getReservationChart } from "../../library/axios";
import { useEffect } from "react";
import axios from "axios";

const Dashboard = () => {

  useEffect(() => {
    getReservationChart()
  }, [])

  return (
    <div>
      <h1>Dashboard Page</h1>
      <Sidebar />
    </div>
  );
};
export default Dashboard;
