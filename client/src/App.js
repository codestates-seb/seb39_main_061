import { Route, Routes, BrowserRouter } from "react-router-dom";
import Login from "./pages/Login/Login.js";
import SignUp from "./pages/SignUp/SignUp";
import Dashboard from "./pages/Dashboard/Dashboard";
import Profile from "./pages/Profile/Profile";
import Management from "./pages/Management/Management";
import ManagementDetail from "./pages/ManagementDetail/ManagementDetail";
import UserPage from "./pages/UserPage/UserPage";
import MainPage from "./pages/MainPage/MainPage";
import CreateCode from "./pages/CreateCode/CreateCode.js";
import FindPassword from "./pages/FindPassword/FindPassword.js";
import Reservation from "./pages/Reservation/Reservation.js";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Routes>
          <Route path="/" element={<MainPage />}></Route>
          <Route path="/signup" element={<SignUp />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/find-password" element={<FindPassword />}></Route>
          <Route path="/dashboard" element={<Dashboard />}></Route>
          <Route path="/profile" element={<Profile />}></Route>
          <Route path="/create-code" element={<CreateCode />}></Route>
          <Route path="/management" element={<Management />}></Route>
          <Route path="/reservation" element={<Reservation />}></Route>
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
