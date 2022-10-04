import { createSlice } from "@reduxjs/toolkit";
// import { asyncChartAxios } from "../library/axios";

const initialState = {
  value: {
    month: [{ deleted: 'N', date: '2022-09', count: 3 }],
    week: [{ deleted: 'N', date: '2022-09-26', count: 1 }],
    time: [{ deleted: 'N', date: '8', count: 2 }],
  }
};
const barChartsSlice = createSlice({
  name: "barCharts",
  initialState: initialState,
  reducers: {
    barCharts(state, action) {
      state.value = Object.assign({}, state.value, action.payload)
    },
  },
  // extraReducers: (builder) => {
  //   builder.addCase(asyncChartAxios.pending, (state,action) => {
  //     state.status = "Loading...";
  //   })
  //   builder.addCase(asyncChartAxios.fulfilled, (state,action) => {
  //     state.data = action.payload;
  //     state.status = "Complete";
  //   })
  //   builder.addCase(asyncChartAxios.rejected, (state,action) => {
  //     state.status = "Fail";
  //   })
  // },
});

// export const {barCharts, extraReducers} = barChartsSlice.actions;
export const barChartsAction = barChartsSlice.actions;

export default barChartsSlice;
