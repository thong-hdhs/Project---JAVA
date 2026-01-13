import { createSlice, configureStore } from '@reduxjs/toolkit';

// Authentication state
const authSlice = createSlice({
  name: 'auth',
  initialState: {
    isAuthenticated: false,
    userRole: null,
  },
  reducers: {
    login(state) {
      state.isAuthenticated = true;
    },
    logout(state) {
      state.isAuthenticated = false;
      state.userRole = null;
    },
    setRole(state, action) {
      state.userRole = action.payload;
    },
  },
});

// Export actions and reducer
export const { login, logout, setRole } = authSlice.actions;
export const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
  },
});
