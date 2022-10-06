import { createSlice } from "@reduxjs/toolkit";
import { PURGE } from "redux-persist";

const initialState = {
  qrCodeId: null,
  businessId: null,
  qrcodeImg: null,
  target: null,
  dueDate: null,
};
const qrcodeSlice = createSlice({
  name: "business",
  initialState: initialState,
  reducers: {
    setQrCodeId(state, action) {
      state.qrCodeId = action.payload;
    },
    setBusinessId(state, action) {
      state.businessId = action.payload;
    },
    setQrcodeImg(state, action) {
      state.qrcodeImg = action.payload;
    },
    setTarget(state, action) {
      state.target = action.payload;
    },
    setDuedate(state, action) {
      state.dueDate = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(PURGE, () => initialState);
  },
});

export const qrcodeActions = qrcodeSlice.actions;

export default qrcodeSlice.reducer;
