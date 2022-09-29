import { createSlice } from "@reduxjs/toolkit";
import { PURGE } from "redux-persist";

const initialState = {
  address: "",
  lat: 0,
  lon: 0,
};
const mapSlice = createSlice({
  name: "location",
  initialState: initialState,
  reducers: {
    setAddress(state, action) {
      state.address = action.payload;
    },
    setlat(state, action) {
      state.lat = action.payload;
    },
    setlon(state, action) {
      state.lon = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(PURGE, () => initialState);
  },
});

export const mapActions = mapSlice.actions;

export default mapSlice.reducer;
