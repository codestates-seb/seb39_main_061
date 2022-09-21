import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div>
      <image src="">logo</image>
      <Link to="/profile">
        <img src = ""></img>
      </Link>
      <Link to="/create-Code">
        <button>QR CreateCode</button>
      </Link>
      <Link to="/management">
        <button>QR Management</button>
      </Link>
      <Link to="/">
        <button>Booking Management</button>
      </Link>
      <Link to="/">
        <button>Menu management</button>
      </Link>
    </div>
  );
};

export default Sidebar;
