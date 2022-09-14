import { Route, Routes, BrowserRouter } from "react-router-dom";
import Login from "./pages/Login/Login.js";
import SignUp from "./pages/SignUp/SignUp";
import Dashboard from "./pages/Dashboard/Dashboard";
import Profile from "./pages/Profile/Profile";
// import ProfileEdit from "./pages/Profile/ProfileEdit";
import Management from "./pages/Management/Management";
import ManagementDetail from "./pages/ManagementDetail/ManagementDetail";
import UserPage from "./pages/UserPage/UserPage";
import MainPage from "./pages/MainPage/MainPage";
import CreateCode from "./pages/CreateCode/CreateCode.js";
import FindPassword from "./pages/FindPassword/FindPassword.js";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/login" element={<Login />} />
          <Route path="/find-password" element={<FindPassword />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/profile" element={<Profile />} />
          {/* <Route path="/profile-edit" element={<ProfileEdit />} /> */}
          <Route path="/create-code" element={<CreateCode />} />
          <Route path="/management" element={<Management />} />
          <Route
            path="/management-Detail"
            element={<ManagementDetail />}
          ></Route>
          <Route path="/userPage" element={<UserPage />}></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
