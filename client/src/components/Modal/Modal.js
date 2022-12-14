import React from "react";
import "./Modal.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { deleteMenu } from "../../api/services/menu";
import { faCircleXmark } from "@fortawesome/free-regular-svg-icons";
import { useState, useRef } from "react";
import { useSelector } from "react-redux";
import { deleteUserRes } from "../../api/services/reservation-user";

function Modal({
  num,
  setIsConfirmModalOpen,
  setIsModalOpen,
  businessId,
  qrCodeId,
  reservationId,
  resCount,
}) {
  console.log(menuId);
  const [isClickDelete, setIsClickDelete] = useState(false);
  const [isResDelete, setResDelete] = useState(null);
  const adminBusinessId = useSelector((state) => state.business.businessId);
  const menuId = useSelector((state) => state.menu.menuId);
  const [phone, setPhone] = useState();
  const nameRef = useRef();
  //
  const deleteMenuHandler = async () => {
    console.log("비즈메뉴아이디", adminBusinessId, menuId);
    deleteMenu(adminBusinessId, menuId).then((res) => {
      setIsClickDelete(true);
      setTimeout(() => {
        window.location.reload();
      }, 1000);
    });
  };
  const phoneHandler = (e) => {
    setPhone(
      e.target.value
        .replace(/[^0-9]/g, "")
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`)
    );
  };

  const resDeleteHandler = () => {
    const name = nameRef.current.value;
    const count = resCount;

    deleteUserRes(businessId, qrCodeId, reservationId, phone, name, count)
      .then((res) => {
        setResDelete(true);
        setTimeout(() => {
          window.location.reload();
        }, 1500);
      })
      .catch((err) => {
        setResDelete(false);
        setTimeout(() => {
          window.location.reload();
        }, 1500);
      });
  };
  return (
    <div className={"modalBackground"}>
      <div className="modalContainer">
        <div className="body">
          {num === 7 && (
            <div className="delete-xmark">
              <FontAwesomeIcon icon={faCircleXmark} />
            </div>
          )}
          {num !== 7 && num !== 13 && (
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
            {num === 10 && <p>QR코드가 없습니다</p>}
            {num === 10 && <p>QR코드를 먼저 생성해주세요</p>}
            {num === 9 && <p>재발급 메일이 발송되었습니다</p>}
            {num === 9 && <p>메일을 확인해주세요</p>}
            {num === 0 && <p>환영합니다</p>}
            {num === 0 && <p>잠시후 대시보드로 이동합니다</p>}
            {num === 1 && <p>인증 메일이 발송되었습니다</p>}
            {num === 1 && <p>메일을 확인해주세요</p>}
            {num === 2 && <p>회원가입이 완료되었습니다 </p>}
            {num === 2 && <p>로그인 화면으로 이동합니다</p>}
            {num === 5 && <p>변경사항이 저장 완료 되었습니다.</p>}
            {num === 5 && <p></p>}
            {num === 6 && <p>메뉴등록이 완료 되었습니다</p>}
            {num === 6 && <p></p>}
            {num === 14 && <p>메뉴등록이 완료 되었습니다</p>}
            {num === 14 && <p></p>}
            {num === 8 && <p>수정사항이 저장 되었습니다.</p>}
            {num === 8 && <p></p>}
            {num === 11 && <p>QR 코드가 생성되었습니다!</p>}
            {num === 11 && <p></p>}
            {num === 7 && isClickDelete === false && (
              <p className="modal__deleteMSG">삭제 하시겠습니까?</p>
            )}
            {num === 7 && isClickDelete === true && (
              <p className="modal__deleteSucessMSG">삭제 처리되었습니다</p>
            )}
            {num === 7 && isClickDelete === true && (
              <p className="modal__deleteSucessMSG"></p>
            )}
            {num === 13 && isResDelete === true && (
              <p className="res__deleteSucessMSG__top">대기 취소되었습니다</p>
            )}
            {num === 13 && isResDelete === true && (
              <p className="res__deleteSucessMSG"></p>
            )}
            {num === 13 && isResDelete === false && (
              <p className="res__deleteSucessMSG__top">
                정보가 일치하지 않습니다
              </p>
            )}
            {num === 13 && isResDelete === false && (
              <p className="res__deleteSucessMSG">다시 시도해 주세요</p>
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
            {num === 13 && isResDelete === null && (
              <div>
                <p className="cancelTitle"> 예약 취소</p>

                <div className="resNameWrap">
                  <label className="inputNameLabel" htmlFor="nameInput">
                    이름
                  </label>
                  <input id="nameInput" ref={nameRef} />
                </div>
                <div className="resPhoneWrap">
                  <label className="inputPhoneLabel" htmlFor="phoneInput">
                    연락처 (-제외)
                  </label>
                  <input
                    maxLength={13}
                    id="phoneInput"
                    value={phone}
                    onChange={phoneHandler}
                  />
                </div>

                <div>
                  <button
                    onClick={() => {
                      resDeleteHandler();
                    }}
                    className="res__submitBtn"
                  >
                    확인
                  </button>
                  <button
                    onClick={() => {
                      setIsModalOpen(false);
                      console.log("모달 닫기");
                    }}
                    className="res__cancelBtn"
                  >
                    취소
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Modal;
