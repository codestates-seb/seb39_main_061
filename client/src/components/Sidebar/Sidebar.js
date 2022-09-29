import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { authActions } from "../../store/auth";
import { useNavigate } from "react-router-dom";
import { persistor } from "../../index";
import { useEffect } from "react";
import { getProfile } from "../../api/services/user";
import { userAction } from "../../store/user";
import StoreManagement from "../../pages/StoreManagement/StoreManagement";

const Sidebar = () => {
  const isLogin = useSelector((state) => state.auth.isAuthenticated);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("token") && isLogin === true) {
      getProfile()
        .then((res) => {
          console.log("로그인 유지 성공!");
          dispatch(userAction.setUser(res));
        })
        .catch((err) => {
          console.log("로그인 유지 실패");
        });
    }
  }, []);

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
