import { createSlice } from "@reduxjs/toolkit";
import { PURGE } from "redux-persist";

const initialState = {
  menuList: [],
};
const menuSlice = createSlice({
  name: "menu",
  initialState: initialState,
  reducers: {
    setMenuList(state, action) {
      state.menuList = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(PURGE, () => initialState);
  },
});

export const menuActions = menuSlice.actions;

export default menuSlice.reducer;
