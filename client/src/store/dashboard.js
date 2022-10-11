import { createSlice } from "@reduxjs/toolkit";
// import { asyncChartAxios } from "../library/axios";

const initialState = {
<<<<<<< HEAD
    month: [{ deleted: 'N', date: '2022-09', count: 0 }],
    week: [{ deleted: 'N', date: '2022-09-26', count: 0 }],
    time: [],
    clickDate: null,
    businessId: null,
    qrCodeId: null,
    clickTimeData: [],
=======
    month: [{ deleted: 'N', date: '2022-09', count: 3 }],
    week: [{ deleted: 'N', date: '2022-09-26', count: 1 }],
    time: [],
>>>>>>> 145c87271e82f01f09dfbf385c17bc4c4710b77b
};

const dashboardSlice = createSlice({
  name: "statics",
  initialState: initialState,
  reducers: {
    setMonth(state, action) {
      state.month = action.payload
    },
    setWeek(state, action) {
      state.week = action.payload
    },
    setTime(state, action) {
      state.time = action.payload
    },
<<<<<<< HEAD
    setClickDate(state, action) {
      state.clickDate = action.payload
    },
    setBusinessId(state, action) {
      state.businessId = action.payload
    },
    setQrCodeId(state, action) {
      state.qrCodeId = action.payload
    },
    setClickTimeData(state, action) {
      state.clickTimeData = action.payload
    },
=======
>>>>>>> 145c87271e82f01f09dfbf385c17bc4c4710b77b
  },

});

export const dashboardActions = dashboardSlice.actions;

export default dashboardSlice.reducer;
