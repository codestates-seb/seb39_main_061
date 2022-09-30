import React from "react";
import styles from "./DayModal.module.css";
import DayPicker from "../DayPicker/DayPicker";

function DayModal({ setDayModalOpen }) {
  return (
    <div className={styles.modalBackground}>
      <div className={styles.modalContainer}>
        <DayPicker setTimePickerOpen={setDayModalOpen} />
      </div>
    </div>
  );
}

export default DayModal;
