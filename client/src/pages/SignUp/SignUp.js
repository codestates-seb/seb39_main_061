import { Link } from "react-router-dom";

const SignUp = () => {
  return (
    <div>
      <h1>SignUp Page</h1>
      <form>
        <input placeholder="Email" />
        <input placeholder="Password" />
        <Link to="/login">
          <button>회원가입</button>
        </Link>
      </form>
    </div>
  );
};
export default SignUp;
