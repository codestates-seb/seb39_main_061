import styles from './QRmanageDetail.module.css';
import { useDispatch, useSelector } from "react-redux";


const QRmanageDetail = (qrCodeImg) => {
  const qrcodeImgSelector = useSelector(state => state.qrcode.qrcodeImg);

  return (
    <div className={styles.container}>
      <div className={styles.info}>
        <img src={"http://localhost:8080" + qrcodeImgSelector} alt="나는 큐알코드야" className={styles.qrImg} />
      </div>
      <div className={styles.info}>
        <div className={styles.texts}>
          <div>QR 코드 명 : 굽네치킨 큐알코드</div>
          <div>QR 코드 만료 기간 : 2022년 10월 29일 까지</div>
        </div>
      </div>
    </div>
  );
};

export default QRmanageDetail;