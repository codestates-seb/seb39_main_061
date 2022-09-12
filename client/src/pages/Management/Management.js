import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
const Management = () => {
  return (
    <div>
      <h1>Management Page</h1>
      <Sidebar />
      <Link to="/managementDetail">
        <button>managementDetail</button>
      </Link>
    </div>
  );
};
export default Management;
