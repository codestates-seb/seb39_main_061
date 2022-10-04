import React from "react";
import "./Modal.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { deleteMenu } from "../../api/services/menu";
import { faCircleXmark } from "@fortawesome/free-regular-svg-icons";
import { useState } from "react";
import { useSelector } from "react-redux";

function Modal({ num, setIsConfirmModalOpen, menuId }) {
  console.log(menuId);
  const [isClickDelete, setIsClickDelete] = useState(false);
  const businessId = useSelector((state) => state.business.businessId);
  //
  const deleteMenuHandler = async () => {
    console.log("비즈메뉴아이디", businessId, menuId);
    deleteMenu(businessId, menuId).then((res) => {
      setIsClickDelete(true);
      setTimeout(() => {
        window.location.reload();
      }, 2000);
    });
  };
  return (
    <div className="modalBackground">
      <div className="modalContainer">
        <div className="body">
          {num === 7 && (
            <div className="delete-xmark">
              <FontAwesomeIcon icon={faCircleXmark} />
            </div>
          )}
          {num !== 7 && (
            <div className="success-checkmark">
              <div className="check-icon">
                <span className="icon-line line-tip"></span>
                <span className="icon-line line-long"></span>
                <div className="icon-circle"></div>
                <div className="icon-fix"></div>
              </div>
            </div>
          )}

          <div className="success-text">
            {num === 0 && <p>환영합니다</p>}
            {num === 0 && <p>잠시후 대시보드로 이동합니다</p>}
            {num === 1 && <p>인증 메일이 발송되었습니다</p>}
            {num === 1 && <p>메일을 확인해주세요</p>}
            {num === 2 && <p>회원가입이 완료되었습니다 </p>}
            {num === 2 && <p>로그인 화면으로 이동합니다</p>}
            {num === 5 && <p>변경사항이 저장 완료 되었습니다.</p>}
            {num === 6 && <p>메뉴등록이 완료 되었습니다</p>}
            {num === 7 && isClickDelete === false && (
              <p className="modal__deleteMSG">삭제 하시겠습니까?</p>
            )}
            {num === 7 && isClickDelete === true && (
              <p className="modal__deleteSucessMSG">삭제 처리되었습니다</p>
            )}
            {num === 7 && isClickDelete === true && (
              <p className="modal__deleteSucessMSG"></p>
            )}
            {num === 7 && isClickDelete === false && (
              <button
                onClick={() => {
                  deleteMenuHandler();
                }}
                className="modal__deleteBtn"
              >
                삭제
              </button>
            )}
            {num === 7 && isClickDelete === false && (
              <button
                onClick={() => {
                  setIsConfirmModalOpen(false);
                }}
                className="modal__cancelBtn"
              >
                취소
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Modal;
