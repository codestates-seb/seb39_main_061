import React from "react";
import styles from "./QRmanageDetail.module.css";
import { useDispatch, useSelector } from "react-redux";
import moment, { invalid } from "moment";
import noneQrImg from "../../Img/noneQRImg.png";
import { imgURL } from "../../api/axios"


const QRmanageDetail = (qrCodeImg) => {
  const qrcodeImgSelector = useSelector((state) => state.qrcode.qrcodeImg);
  const targetSelector = useSelector((state) => state.qrcode.target);
  const dueDateSelector = useSelector((state) => state.qrcode.dueDate);

  let dueDate = moment(dueDateSelector).format("YYYY년 MM월 DD일");

  return (
    <div className={styles.container}>
      <div className={styles.info}>
<<<<<<< HEAD
        <img
          src={
            qrcodeImgSelector
              ? "http://localhost:8080" + qrcodeImgSelector
              : noneQrImg
          }
          alt="나는 큐알코드야"
          className={styles.qrImg}
        />
=======
        <img src={qrcodeImgSelector? imgURL + qrcodeImgSelector : noneQrImg} alt="나는 큐알코드야" className={styles.qrImg} />
>>>>>>> bd090842b0f945d5e16e914be80660d3e0262e6c
      </div>
      <div className={styles.info}>
        <div className={styles.texts}>
          <div>QR 코드 명 : {targetSelector}</div>
<<<<<<< HEAD
          <div>
            QR 코드 만료 기간 :{" "}
            {dueDate === "Invalid date" ? "" : dueDate + " 까지"}
          </div>
=======
          <div>만료 기간 : {dueDate === "Invalid date"? "" : dueDate + " 까지"}</div>
>>>>>>> bd090842b0f945d5e16e914be80660d3e0262e6c
        </div>
      </div>
    </div>
  );
};

export default QRmanageDetail;
