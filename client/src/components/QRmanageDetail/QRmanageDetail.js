import React from "react";
import styles from "./QRmanageDetail.module.css";
import { useDispatch, useSelector } from "react-redux";
import moment, { invalid } from "moment";
import noneQrImg from "../../Img/noneQRImg.png";
import { imgURL } from "../../api/axios";
import ReactToPrint from "react-to-print";
import { useRef } from "react";
import { useState, useEffect } from "react";
import {
  getBusinessId,
  getQRcodeInfo,
  deleteQRcodeImg,
} from "../../api/services/createQrcode";
import { qrcodeActions } from "../../store/qrCode";

const QRmanageDetail = () => {
  const [getQrCodeImg, setGetQrCodeImg] = useState();
  const [qrTarget, setQrTarget] = useState();
  // const dueDateSelector = useSelector((state) => state.qrcode.dueDate);
  const businessIdSelector = useSelector((state) => state.qrcode.businessId);
  const qrcodeIdSelector = useSelector((state) => state.qrcode.qrCodeId);
  const qrcodeImgSelector = useSelector((state) => state.qrcode.qrcodeImg);
  const targetSelector = useSelector((state) => state.qrcode.target);
  let componentRef = useRef();
  const dispatch = useDispatch();

  // let dueDate = moment(dueDateSelector).format("YYYY년 MM월 DD일");

  const firstDataRendering = async () => {
    const resBusinessId = await getBusinessId()
    dispatch(qrcodeActions.setBusinessId(resBusinessId.businessId))
    console.log("businessId: ", resBusinessId.businessId)
    const resQrcodeId = await getQRcodeInfo(resBusinessId.businessId)
    console.log(resQrcodeId)
    if (resQrcodeId.length !== 0) {
      dispatch(qrcodeActions.setQrCodeId(resQrcodeId[0].qrCodeId))
      setGetQrCodeImg(resQrcodeId[0].qrCodeImg)
      setQrTarget(resQrcodeId[0].target)
      // dispatch(qrcodeActions.setQrcodeImg(resQrcodeId[0].qrCodeImg))
      // dispatch(qrcodeActions.setTarget(resQrcodeId[0].target))
      console.log("qrcodeId: ", resQrcodeId[0].qrCodeId)
    }
  };

  useEffect(() => {
    firstDataRendering();
  }, []);

  const deleteQRcode = async () => {
    const resBusinessId = await getBusinessId()
    const resQrcodeId = await getQRcodeInfo(resBusinessId.businessId)
    console.log(resQrcodeId)
    deleteQRcodeImg(resBusinessId.businessId, resQrcodeId[0].qrCodeId)
    window.location.reload()
  }

  return (
    <div className={styles.container}>
      <div className={styles.info}>
        <img src={getQrCodeImg ? imgURL + getQrCodeImg : noneQrImg} alt="QR코드" className={styles.qrImg} ref={(el) => (componentRef = el)} />
      </div>
      <div className={styles.info}>
        <div className={styles.texts}>
          <div className={styles.qr__txt}>QR 코드 명 : {qrTarget === "target" ? "" : qrTarget}</div>
          {/* <div className={styles.qr__txt}>만료 기간 : {dueDate === "Invalid date" ? "" : dueDate + " 까지"}</div> */}
          <ReactToPrint
            trigger={() => <button className={styles.qr__btn}>출력하기</button>}
            content={() => componentRef}
          />
          <button className={styles.qr__btn} onClick={deleteQRcode}>
            삭제하기
          </button>
        </div>
      </div>
    </div>
  );
};

export default QRmanageDetail;
