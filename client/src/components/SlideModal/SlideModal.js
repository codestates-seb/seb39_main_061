import React from "react";
import day from "./DayModal.module.css";
import DayPicker from "../DayPicker/DayPicker";
import TimePicker from "../TimePicker/TimePicker";
import time from "./TimeModal.module.css";
import { useEffect, useRef } from "react";

function SlideModal({ open, modalNum, setOpen }) {
  const modalRef = useRef();

  useEffect(() => {
    document.addEventListener("mousedown", clickModalOutside);

    return () => {
      document.removeEventListener("mousedown", clickModalOutside);
    };
  });

  const clickModalOutside = (event) => {
    if (open && !modalRef.current.contains(event.target)) {
      setOpen(false);
    }
  };
  return (
    <>
      {modalNum === 1 ? (
        <div ref={modalRef} className={time.modalBackground}>
          <div className={time.modalContainer}>
            {modalNum === 1 && <TimePicker setOpen={setOpen} />}
          </div>
        </div>
      ) : (
        <div ref={modalRef} className={day.modalBackground}>
          <div className={day.modalContainer}>
            {modalNum === 2 && <DayPicker setOpen={setOpen} />}
          </div>
        </div>
      )}
    </>
  );
}

export default SlideModal;
