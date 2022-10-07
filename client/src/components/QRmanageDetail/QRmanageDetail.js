import React from "react";
import styles from "./QRmanageDetail.module.css";
import { useDispatch, useSelector } from "react-redux";
import moment, { invalid } from "moment";
import noneQrImg from "../../Img/noneQRImg.png";
import { imgURL } from "../../api/axios";
import ReactToPrint from "react-to-print";
import { useRef } from "react";

const QRmanageDetail = (qrCodeImg) => {
  const qrcodeImgSelector = useSelector((state) => state.qrcode.qrcodeImg);
  const targetSelector = useSelector((state) => state.qrcode.target);
  const dueDateSelector = useSelector((state) => state.qrcode.dueDate);
  let componentRef = useRef();

  let dueDate = moment(dueDateSelector).format("YYYY년 MM월 DD일");

  return (
    <div className={styles.container}>
      <div className={styles.info}>
        <img src={qrcodeImgSelector ? imgURL + qrcodeImgSelector : noneQrImg} alt="나는 큐알코드야" className={styles.qrImg} ref={(el) => (componentRef = el)} />
      </div>
      <div className={styles.info}>
        <div className={styles.texts}>
          <div className={styles.qr__txt}>QR 코드 명 : {targetSelector}</div>
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
