import React from "react";
import "./Modal.css";

function Modal({ key }) {
  return (
    <div className="modalBackground">
      <div className="modalContainer">
        {/* <div className="titleCloseBtn">
          <button
            onClick={() => {
              setOpenModal(false);
            }}
          >
            X
          </button>
        </div> */}

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
            {key === 1 ? (
              <p>인증 메일이 발송되었습니다</p>
            ) : (
              <p>환영합니다. 회원가입이 완료되었습니다 </p>
            )}
            {key === 1 ? (
              <p>잠시후 로그인 페이지로 이동합니다</p>
            ) : (
              <p>잠시후 대시보드 페이지로 이동합니다</p>
            )}
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
