import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";

const Qrcode = () => {
  return (
    <div>
      <h1>Dashboard Page</h1>
      <Sidebar />
      <div className="qr-code">QR코드 관리</div>
      <button className="btn-service">관리서비스</button>
      <button className="btn-service2">예약 -대기 서비스</button>
    </div>
  );
};
export default Qrcode;
