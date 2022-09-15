import Sidebar from "../../components/Sidebar/Sidebar";
import CreateQr from "../../components/CreateQr";


const CreateCode = () => {
  return (
    <div>
      <h1>QR 코드 생성</h1>
      <Sidebar />
      <CreateQr />
    </div>
  );
};
export default CreateCode;
