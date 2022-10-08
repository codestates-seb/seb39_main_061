import { createSlice } from "@reduxjs/toolkit";
// import { asyncChartAxios } from "../library/axios";

const initialState = {
    month: [{ deleted: 'N', date: '2022-09', count: 3 }],
    week: [{ deleted: 'N', date: '2022-09-26', count: 1 }],
    date: null,
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
    setDate(state, action) {
      state.date = action.payload
    },
  },

});

export const dashboardActions = dashboardSlice.actions;

export default dashboardSlice.reducer;
