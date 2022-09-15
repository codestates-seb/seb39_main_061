import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  value: {
    image_file: "",
    preview_URL: "img/default_image.png",
  }
};
const profileImgSlice = createSlice({
  name: "profileImg",
  initialState: initialState,
  reducers: {
    ImgSubmit: (state, action) => {
      state.value = action.payload
    }
  }
});

export const profileImgActions = profileImgSlice.actions;

export default profileImgSlice;