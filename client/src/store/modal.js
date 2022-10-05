import { createSlice } from "@reduxjs/toolkit";
import { PURGE } from "redux-persist";

const initialState = {
  isModalOpen: false,
};
const modalSlice = createSlice({
  name: "modal",
  initialState: initialState,
  reducers: {
    setIsModalOpen(state, action) {
      state.isModalOpen = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(PURGE, () => initialState);
  },
});

export const modalActions = modalSlice.actions;

export default modalSlice.reducer;
