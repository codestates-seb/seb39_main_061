import React from "react";
import styles from "./QRmanageDetail.module.css";
import { useDispatch, useSelector } from "react-redux";
import moment, { invalid } from "moment";
import noneQrImg from "../../Img/noneQRImg.png";
import { imgURL } from "../../api/axios";
import ReactToPrint from "react-to-print";
import { useRef } from "react";
import { useState, useEffect } from "react";
import { getBusinessId, getQRcodeImg } from "../../api/services/createQrcode"

const QRmanageDetail = () => {
  const [BusinessIdId, setGetBusinessIdId] = useState();
  const [getQrCodeImg, setGetQrCodeImg] = useState();
  const [qrTarget, setQrTarget] = useState();
  const [qrDueDate, setQrDueDate] = useState();
  // const qrcodeImgSelector = useSelector((state) => state.qrcode.qrcodeImg);
  const targetSelector = useSelector((state) => state.qrcode.target);
  const dueDateSelector = useSelector((state) => state.qrcode.dueDate);
  let componentRef = useRef();

  let dueDate = moment(dueDateSelector).format("YYYY년 MM월 DD일");

  // businessId 가져오기 -> getBusinessId()
  // qrCodeId 가져오기 -> 코드 리스트 조회로 가져오자
  // 
  // getQRcode axios 요청 res로 qrcodeImg 받아오기
  // 없으면 none 이미지 렌더링
  useEffect(() => {
    getBusinessId()
      .then(res => setGetBusinessIdId(res.businessId))
    getQRcodeImg(BusinessIdId)
      .then(res => {
        setGetQrCodeImg(res[0].qrCodeId)
        setQrTarget(res[0].target)
      })
  }, [])

  return (
    <div className={styles.container}>
      <div className={styles.info}>
        <img src={getQrCodeImg ? imgURL + getQrCodeImg : noneQrImg} alt="QR코드" className={styles.qrImg} ref={(el) => (componentRef = el)} />
      </div>
      <div className={styles.info}>
        <div className={styles.texts}>
          <div className={styles.qr__txt}>QR 코드 명 : {qrTarget}</div>
          <div className={styles.qr__txt}>만료 기간 : {dueDate === "Invalid date" ? "" : dueDate + " 까지"}</div>
          <ReactToPrint
            trigger={() => <button className={styles.qr__btn}>출력하기</button>}
            content={() => componentRef}
          />
        </div>
      </div>
    </div>
  );
};

export default QRmanageDetail;
