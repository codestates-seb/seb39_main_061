import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  img: "img/default_image.png",
  name: ""
};
const profileSlice = createSlice({
  name: "profile",
  initialState: initialState,
  reducers: {
    ImgSubmit: (state, action) => {
      state.img = action.payload
    },
    changeName: (state, action) => {
      state.name = action.payload
    }
  }
});

export const profileAction = profileSlice.actions;

export default profileSlice;