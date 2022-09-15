import { Link, Router } from "react-router-dom";

const MainPage = () => {
  return (
    <div>
      <h1>Main Page</h1>
      <Link to="/login">
        <button>Login</button>
      </Link>
      <Link to="/signup">
        <button>SignUp</button>
      </Link>
    </div>
  );
};
export default MainPage;
