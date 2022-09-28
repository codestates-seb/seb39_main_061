import Styles from "./StoreInfo.module.css";
import { useRef, useState } from "react";
import MapModal from "../MapModal";
import { useSelector } from "react-redux";
import { getBusinessInfo } from "../../api/services/store";

const StoreInfo = () => {
  const address = useSelector((state) => state.map.address);
  const nameRef = useRef();
  const openTimeRef = useRef();
  const holidayRef = useRef();
  const phoneRef = useRef();
  const introductionRef = useRef();
  const detailAddress = useRef();

  const storeSubmitHanlder = async (e) => {
    e.preventDefault();
    const name = nameRef.current.value;
    const openTime = openTimeRef.current.value;
    const holiday = holidayRef.current.value;
    const phone = phoneRef.current.value;
    const introduction = introductionRef.current.value;

    // 매장 정보 post

    // 매장 정보 가져오기
  };

  return (
    <div>
      <form onSubmit={storeSubmitHanlder} className={Styles.StoreInfo}>
        <div className={Styles.StoreInfo__title}>
          <h1>매장 정보</h1>
        </div>
        <div className={Styles.StoreInfo__input}>
          <div className={Styles.StoreInfo__input__wrap1}>
            <div className={Styles.StoreInfo__input__wrap1__info1}>
              <div className={Styles.time}>
                <span>매장 이름</span>
                <input ref={nameRef} placeholder="ex:덕이네 곱창" />
              </div>
              <div>
                <span>영업 시간</span>
                <input ref={openTimeRef} placeholder="ex:10:00 ~ 21:00" />
              </div>
            </div>
            <div className={Styles.StoreInfo__input__wrap1__info2}>
              <div>
                <span>휴무일</span>
                <input ref={holidayRef} placeholder="ex:매주 월요일" />
              </div>
              <div>
                <span>매장 전화번호</span>
                <input ref={phoneRef} placeholder="ex:031-947-3334" />
              </div>
            </div>
          </div>
          <div className={Styles.StoreInfo__input__wrap2}>
            <div className={Styles.StoreInfo__input__wrap2__info1}>
              <span>소개글</span>
              <textarea
                ref={introductionRef}
                placeholder="ex: 우리집 맛집이에요 ! 많이들 드시러 오세요"
              ></textarea>
            </div>
            <div className={Styles.StoreInfo__input__wrap2__info2}>
              <div className={Styles.StoreInfo__input__wrap2__info2__title}>
                <span>매장 위치</span>
              </div>
              <div className={Styles.StoreInfo__input__wrap2__info2__address}>
                <input
                  value={address}
                  disabled
                  placeholder="ex: 서울특별시 동작구 밤리단길 369 B1"
                />
                <MapModal />
              </div>
              <input ref={detailAddress} placeholder="상세주소" />
            </div>
          </div>
        </div>
        <div className={Styles.StoreInfo__input__btn}>
          <button>수정</button>
          <button>취소</button>
        </div>
      </form>
    </div>
  );
};

export default StoreInfo;
