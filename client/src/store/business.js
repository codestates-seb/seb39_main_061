import { createSlice } from "@reduxjs/toolkit";
import { PURGE } from "redux-persist";

const initialState = {
  startTime: "10:30",
  endTime: "21:00",
  startOrEnd: 1,
  checkOpenTime: null,
  holidayList: "",
};
const businessSlice = createSlice({
  name: "business",
  initialState: initialState,
  reducers: {
    setStartTime(state, action) {
      state.startTime = action.payload;
    },
    setEndTime(state, action) {
      state.endTime = action.payload;
    },
    setStartOrEnd(state, action) {
      state.startOrEnd = action.payload;
    },
    setCheckOpenTime(state, action) {
      state.checkOpenTime = action.payload;
    },
    setHolidayList(state, action) {
      state.holidayList = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(PURGE, () => initialState);
  },
});

export const businessActions = businessSlice.actions;

export default businessSlice.reducer;
