import React, { useState, useEffect, useRef } from "react";
import Modal from "react-modal"; // 추가
import MapContainer from "./MapContainer/MapContainer";
import { useSelector } from "react-redux";

const MapModal = ({ toggle, isOpen, setIsOpen }) => {
  const address = useSelector((state) => state.map.address);

  console.log("select address", address);

  // Modal 스타일
  const customStyles = {
    overlay: {
      backgroundColor: "rgba(0,0,0,0.5)",
    },
    content: {
      left: "0",
      margin: "auto",
      width: "1000px",
      height: "700px",
      padding: "0",
    },
  };

  return (
    <div>
      <Modal
        onClick={console.log("click")}
        isOpen={isOpen}
        ariaHideApp={false}
        style={customStyles}
        onRequestClose={toggle}
      >
        <MapContainer toggle={toggle} />
      </Modal>
    </div>
  );
};

export default MapModal;
