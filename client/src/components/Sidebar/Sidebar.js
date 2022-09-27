import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { authActions } from "../../store/auth";
import { useNavigate } from "react-router-dom";
import { persistor } from "../../index";
import StoreManagement from "../../pages/StoreManagement/StoreManagement";

const Sidebar = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const logoutHanlder = () => {
    // deleteToken("token");
    localStorage.removeItem("token");
    dispatch(authActions.logout());
    console.log("로그아웃");
    navigate("/");
  };
  const purge = async () => {
    await persistor.purge();
  };
  return (
    <div>
      <Link to="/dashboard">
        <button>dashboard</button>
      </Link>
      <Link to="/profile">
        <button>Profile</button>
      </Link>
      <Link to="/create-Code">
        <button>CreateCode</button>
      </Link>
      <Link to="/management">
        <button>management</button>
      </Link>
      <Link to="/store-management">
        <button>StoreManagement</button>
      </Link>
      {isLogin && (
        <button
          onClick={async () => {
            await logoutHanlder();
            await setTimeout(() => purge(), 200);
          }}
        >
          로그아웃
        </button>
      )}
    </div>
  );
};

export default Sidebar;
