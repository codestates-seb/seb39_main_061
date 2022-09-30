import React from "react";
import styles from "./TimeModal.module.css";
import TimePicker from "../TimePicker/TimePicker";

function TimeModal({ setTimePickerOpen }) {
  return (
    <div className={styles.modalBackground}>
      <div className={styles.modalContainer}>
        <TimePicker setTimePickerOpen={setTimePickerOpen} />
      </div>
    </div>
  );
}

export default TimeModal;
