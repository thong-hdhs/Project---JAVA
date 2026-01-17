import { createSlice } from "@reduxjs/toolkit";
import { v4 as uuidv4 } from "uuid";

const storedUser = JSON.parse(localStorage.getItem("user"));
const storedToken = localStorage.getItem("token");

export const authSlice = createSlice({
  name: "auth",
  initialState: {
    user: storedUser || null,
    token: storedToken || null,
    isAuth: !!storedUser && !!storedToken,
  },
  reducers: {
    setUser: (state, action) => {
      const { user, token } = action.payload;
      state.user = user;
      state.token = token;
      state.isAuth = true;

      // Persist to localStorage
      localStorage.setItem("user", JSON.stringify(user));
      localStorage.setItem("token", token);
    },
    logOut: (state, action) => {
      state.user = null;
      state.token = null;
      state.isAuth = false;

      // Clear localStorage
      localStorage.removeItem("user");
      localStorage.removeItem("token");
    },
  },
});

export const { setUser, logOut } = authSlice.actions;
export default authSlice.reducer;
