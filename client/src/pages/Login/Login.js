import { Link } from "react-router-dom";

const Login = () => {
  return (
    <div>
      <h1>LoginPage</h1>
      <form>
        <input placeholder="Email" />
        <input placeholder="Password" />
        <Link to="/dashboard">
          <button>Login</button>
        </Link>
        <Link to="/signup">
          <button>SignUp</button>
        </Link>
        <Link to="/find-password">
          <button>Find Password</button>
        </Link>
      </form>
    </div>
  );
};
export default Login;
