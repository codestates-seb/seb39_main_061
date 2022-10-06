import styles from './QRmanageDetail.module.css';
import { useDispatch, useSelector } from "react-redux";
import moment, { invalid } from "moment";
import noneQrImg from "../../Img/noneQRImg.png";



const QRmanageDetail = (qrCodeImg) => {
  const qrcodeImgSelector = useSelector(state => state.qrcode.qrcodeImg);
  const targetSelector = useSelector(state => state.qrcode.target);
  const dueDateSelector = useSelector(state => state.qrcode.dueDate);

  let dueDate = moment(dueDateSelector).format("YYYY년 MM월 DD일");

  return (
    <div className={styles.container}>
      <div className={styles.info}>
        <img src={qrcodeImgSelector? "http://localhost:8080" + qrcodeImgSelector : noneQrImg} alt="나는 큐알코드야" className={styles.qrImg} />
      </div>
      <div className={styles.info}>
        <div className={styles.texts}>
          <div>QR 코드 명 : {targetSelector}</div>
          <div>QR 코드 만료 기간 : {dueDate === "Invalid date"? "" : dueDate + " 까지"}</div>
        </div>
      </div>
    </div>
  );
};

export default QRmanageDetail;