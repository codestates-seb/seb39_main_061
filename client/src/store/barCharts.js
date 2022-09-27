import { createSlice } from "@reduxjs/toolkit";
import { PURGE } from "redux-persist";

const initialState = {
  value: {
    month: [],
    week: [],
    time: [],
  }
};
const barChartsSlice = createSlice({
  name: "barCharts",
  initialState: initialState,
  reducers: {
    barChartsData(state, action) {
      state.value = action.payload
    },
  },
  extraReducers: (builder) => {
    builder.addCase(PURGE, () => initialState);
  },
});

export const { barChartsData } = barChartsSlice.actions;

export default barChartsSlice.reducer;
