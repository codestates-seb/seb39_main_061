import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div>
      <Link to="/profile">
        <button>Profile</button>
      </Link>
      <Link to="/create-Code">
        <button>CreateCode</button>
      </Link>
      <Link to="/management">
        <button>management</button>
      </Link>
    </div>
  );
};

export default Sidebar;
