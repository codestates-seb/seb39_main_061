import React from "react";
import "./Modal.css";

function Modal({ num }) {
  return (
    <div className="modalBackground">
      <div className="modalContainer">
        <div className="body">
          {/* <svg
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 130.2 130.2"
          >
            <circle
              className="path circle"
              fill="none"
              stroke="#D06079"
              strokeWidth="6"
              stroke-miterlimit="10"
              cx="65.1"
              cy="65.1"
              r="62.1"
            />
            <line
              className="path line"
              fill="none"
              stroke="#D06079"
              strokeWidth="6"
              stroke-linecap="round"
              stroke-miterlimit="10"
              x1="34.4"
              y1="37.9"
              x2="95.8"
              y2="92.3"
            />
            <line
              className="path line"
              fill="none"
              stroke="#D06079"
              stroke-width="6"
              stroke-linecap="round"
              stroke-miterlimit="10"
              x1="95.8"
              y1="38"
              x2="34.4"
              y2="92.2"
            />
          </svg> */}
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
            {num === 1 && <p>잠시후 로그인페이지로 이동합니다</p>}

            {num === 2 && <p>환영합니다.</p>}
            {num === 2 && <p>잠시후 대시보드로 이동합니다</p>}
          </div>
        </div>
        {/* <div className="footer">
          <button
            onClick={() => {
              setOpenModal(false);
            }}
            id="cancelBtn"
          >
            Cancel
          </button>
          <button>Continue</button>
        </div> */}
      </div>
    </div>
  );
}

export default Modal;
