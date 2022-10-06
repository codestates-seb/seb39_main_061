import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import React from "react";

const Management = () => {
  return (
    <div>
      <h1>Management Page</h1>
      <Sidebar />
      <Link to="/management-detail">
        <button>management Detail Page</button>
      </Link>
    </div>
  );
};
export default Management;
