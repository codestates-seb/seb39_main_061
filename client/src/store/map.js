import { createSlice } from "@reduxjs/toolkit";
import { PURGE } from "redux-persist";

const initialState = {
  address: "",
};
const mapSlice = createSlice({
  name: "location",
  initialState: initialState,
  reducers: {
    setAddress(state, action) {
      state.address = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(PURGE, () => initialState);
  },
});

export const mapActions = mapSlice.actions;

export default mapSlice.reducer;
