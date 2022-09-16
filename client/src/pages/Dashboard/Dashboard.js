import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import axiosInstance from "../../library/axios";
import { userAction } from "../../store/user";

const Dashboard = () => {
  const dispatch = useDispatch();
  const getProfile = async () => {
    try {
      let responose = await axiosInstance.get("/api/v1/members/profile");
      if (responose.status === 200) {
        dispatch(userAction.setUser(responose.data.data));
      }
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    getProfile();
  }, []);

  return (
    <div>
      <h1>Dashboard Page</h1>
      <Sidebar />
    </div>
  );
};
export default Dashboard;
