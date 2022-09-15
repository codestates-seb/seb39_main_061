import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { authActions } from "../../store/auth";
import { useNavigate } from "react-router-dom";
import {
  deleteCookie,
  getLoginCookie,
  setLoginCookie,
} from "../../library/cookie";

const Sidebar = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const token = useSelector((state) => state.auth.token);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const logoutHanlder = () => {
    dispatch(authActions.logout());
    deleteCookie(token);
    navigate("/");
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
      {isLogin && <button onClick={logoutHanlder}>로그아웃</button>}
    </div>
  );
};

export default Sidebar;