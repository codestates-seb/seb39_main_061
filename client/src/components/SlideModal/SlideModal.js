import React from "react";
import day from "./DayModal.module.css";
import DayPicker from "../DayPicker/DayPicker";
import TimePicker from "../TimePicker/TimePicker";
import time from "./TimeModal.module.css";

function SlideModal({ modalNum, setOpen }) {
  return (
    <>
      {modalNum === 1 ? (
        <div className={time.modalBackground}>
          <div className={time.modalContainer}>
            {modalNum === 1 && <TimePicker setOpen={setOpen} />}
          </div>
        </div>
      ) : (
        <div className={day.modalBackground}>
          <div className={day.modalContainer}>
            {modalNum === 2 && <DayPicker setOpen={setOpen} />}
          </div>
        </div>
      )}
    </>
  );
}

export default SlideModal;
