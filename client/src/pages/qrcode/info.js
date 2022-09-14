import { Link } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import Checkbox from '@mui/material/Checkbox';
import TableCell from '@mui/material/TableCell';

import "./css/info.css";
const Info = () => {
    function EnhancedTableHead(props) {
        const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort } =
          props;
        const createSortHandler = (property) => (event) => {
          onRequestSort(event, property);
        };
  return (
    <div className="main">
        <div className="container">
      <Sidebar />    
            <div className="setting-1">
                <div className="title-1">
                    <a>브라운 돈까스</a>
                </div>
                  <div className="check">
                      <TableCell padding="checkbox">
                          <Checkbox
                              color="primary"
                              indeterminate={numSelected > 0 && numSelected < rowCount}
                              checked={rowCount > 0 && numSelected === rowCount}
                              onChange={onSelectAllClick}
                              inputProps={{
                                  'aria-label': 'select all desserts',
                              }}
                          />
                      </TableCell>
                    <a>번호</a>
                        <a>예약관리 명</a>
                        <a>QR코드 유효기간</a>
                        
                </div>
                <div className="check">
                    <div><input type='checkbox'></input></div>  
                        <a>1</a>
                        <a>돈까스-점심</a>
                        <a>22/09/11/13:00 - 22/09/11/22:00</a>
                        <button className="timeEdit">수정</button>
                </div>
                <div className="check">  
                    <input type='checkbox'></input>
                        <a>2</a>
                        <a>돈까스-저녁</a>
                        <a>22/09/13/13:00 - 22/09/13/22:00</a>
                        <button className="timeEdit">수정</button>
                </div>
                <div className="check">  
                    <input type='checkbox'></input>
                        <a>3</a>
                        <a>돈까스-저녁</a>
                        <a>22/09/13/13:00 - 22/09/13/22:00</a>
                        <button className="timeEdit">수정</button>
                </div>
                <div className="check">  
                    <input type='checkbox'></input>
                        <a>4</a>
                        <a>돈까스-저녁</a>
                        <a>22/09/13/13:00 - 22/09/13/22:00</a>
                        <button className="timeEdit">수정</button>
                </div>
                <div className="check">  
                    <input type='checkbox'></input>
                        <a>5</a>
                        <a>돈까스-저녁</a>
                        <a>22/09/13/13:00 - 22/09/13/22:00</a>
                        <button className="timeEdit">수정</button>
                </div>
                <div className="check">  
                    <input type='checkbox'></input>
                        <a>6</a>
                        <a>돈까스-저녁</a>
                        <a>22/09/13/13:00 - 22/09/13/22:00</a>
                        <button className="timeEdit">수정</button>
                </div>
                    <div className="cate">
                        <button>삭제</button>
                        <button>연장</button>
                    </div>    
                </div>

        </div>
    </div>
  );
};
}
export default Info;
