import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./auth";
import storage from "redux-persist/lib/storage";
import { combineReducers } from "redux";
import { persistReducer, PERSIST, PURGE } from "redux-persist";
import userReducer from "./user";
import mapReducer from "./map";
import businessReducer from "./business";
import modalReducer from "./modal";
import menuReducer from "./menu";
import dashboardReducer from "./dashboard";
import logger from "redux-logger";

const reducers = combineReducers({
  user: userReducer,
  auth: authReducer,
  map: mapReducer,
  business: businessReducer,
  modal: modalReducer,
  menu: menuReducer,
  dashboard: dashboardReducer,
});

const persistConfig = {
  key: "root",
  version: 1,
  storage,
  blacklist: ["map", "business", "modal"],
};
const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    //미들웨어 작성시 에러 주의
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [PERSIST, PURGE],
      },
    }),
});

export default store;
