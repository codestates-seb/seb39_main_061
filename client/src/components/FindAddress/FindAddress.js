import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { mapActions } from "../../store/map";
import styles from "./FindAddress.module.css";
const { kakao } = window;

const FindAddress = ({ toggle }) => {
  // 검색결과 배열에 담아줌
  const [Places, setPlaces] = useState([]);
  const [address, setAddress] = useState("");

  const addressHandler = (e) => {
    setAddress(e.target.value);
  };
  const dispatch = useDispatch();

  useEffect(() => {
    var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
    const container = document.getElementById("myMap");
    const options = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 3,
      size: 7,
    };
    const map = new kakao.maps.Map(container, options);

    if (address.length > 0) {
      const ps = new kakao.maps.services.Places();

      ps.keywordSearch(address, placesSearchCB, options);
    }

    function placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        let bounds = new kakao.maps.LatLngBounds();
        console.log("데이터는?", data);
        for (let i = 0; i < data.length; i++) {
          displayMarker(data[i]);
          bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }

        map.setBounds(bounds);

        // 페이지 목록 보여주는 displayPagination() 추가
        displayPagination(pagination);
        setPlaces(data);
      }
    }

    // 검색결과 목록 하단에 페이지 번호 표시
    function displayPagination(pagination) {
      var paginationEl = document.getElementById("pagination"),
        fragment = document.createDocumentFragment(),
        i;

      // 기존에 추가된 페이지 번호 삭제
      while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
      }

      for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement("a");
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
          el.className = "on";
        } else {
          el.onclick = (function (i) {
            return function () {
              pagination.gotoPage(i);
            };
          })(i);
        }

        fragment.appendChild(el);
      }
      paginationEl.appendChild(fragment);
    }

    function displayMarker(place) {
      let marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
      });

      kakao.maps.event.addListener(marker, "click", function () {
        infowindow.setContent(
          '<div style="padding:5px;font-size:12px;">' +
            place.place_name +
            "</div>"
        );
        infowindow.open(map, marker);
      });
    }
    // var coords = new kakao.maps.LatLng(lat, lng);
  }, [address]);

  return (
    <div className={styles.FindAddress}>
      <div
        id="myMap"
        className={styles.myMap}

        // style={{ width: "80%", height: "80%" }}
      ></div>

      <div className={styles.resultList}>
        <div>
          <input
            value={address}
            onChange={addressHandler}
            placeholder="예: 코드스테이츠"
          />

          {Places.map((item, i) => (
            <div key={i} className={styles.location}>
              <div>
                <h5
                  onClick={() => {
                    setAddress(item.place_name);
                  }}
                >
                  {item.place_name}
                </h5>
                {item.road_address_name ? (
                  <div>
                    <span>{item.road_address_name}</span>
                  </div>
                ) : (
                  <span>{item.address_name}</span>
                )}
              </div>
              <button
                onClick={() => {
                  dispatch(
                    mapActions.setAddress(
                      item.road_address_name
                        ? item.road_address_name
                        : item.address_name
                    )
                  );
                  dispatch(mapActions.setlat(Number(item.x)));
                  dispatch(mapActions.setlon(Number(item.y)));
                  toggle();
                }}
              >
                선택
              </button>
            </div>
          ))}

          <div id="pagination" className={styles.pagination}></div>
        </div>
      </div>
    </div>
  );
};

export default FindAddress;
