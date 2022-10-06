import React, { useEffect, useState } from "react";
import styles from "./Map.module.css";
const { kakao } = window;

const Map = ({ lat, lng }) => {
  const [map, setMap] = useState(null);
  console.log(lat, lng);
  useEffect(() => {
    const container = document.getElementById("map");
    const options = {
      center: new kakao.maps.LatLng(lat, lng),
      level: 3,
    };
    const kakaoMap = new kakao.maps.Map(container, options);
    const markerPosition = new kakao.maps.LatLng(lat, lng);

    const marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(lat, lng),
    });
    marker.setMap(kakaoMap);
    setMap(kakaoMap);
  }, []);

  return (
    <div className={styles.map}>
      <div className={styles.map__container} id="map"></div>
    </div>
  );
};

export default Map;
