import React from "react";
import "./Modal.css";

function Modal({ num }) {
  return (
    <div className="modalBackground">
      <div className="modalContainer">
        <div className="body">
         
          <div className="success-checkmark">
            <div className="check-icon">
              <span className="icon-line line-tip"></span>
              <span className="icon-line line-long"></span>
              <div className="icon-circle"></div>
              <div className="icon-fix"></div>
            </div>
          </div>
          <div className="success-text">
            {num === 0 && <p>환영합니다.</p>}
            {num === 0 && <p>잠시후 대시보드로 이동합니다</p>}

            {num === 1 && <p>인증 메일이 발송되었습니다</p>}
            {num === 1 && <p>메일을 확인해주세요</p>}

            {num === 2 && <p>환영합니다.</p>}
            {num === 2 && <p>잠시후 대시보드로 이동합니다</p>}
          </div>
        </div>
       
      </div>
    </div>
  );
}

export default Modal;
