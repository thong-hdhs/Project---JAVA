import apiClient from "./apiClient";
import type {
  LoginRequest,
  RegisterRequest,
  AuthResponse,
  User,
} from "../types";

export const authService = {
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await apiClient.post("/auth/login", credentials);
    return response.data;
  },

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    const response = await apiClient.post("/auth/register", userData);
    return response.data;
  },

  async getCurrentUser(): Promise<User> {
    const response = await apiClient.get("/auth/me");
    return response.data;
  },

  async updateProfile(userId: string, data: Partial<User>): Promise<User> {
    const response = await apiClient.patch(`/users/${userId}`, data);
    return response.data;
  },

  async updateAvatarsForAllUsers(
    avatarUrl: string,
    userIds: string[]
  ): Promise<void> {
    const updatePromises = userIds.map((userId) =>
      apiClient.patch(`/users/${userId}`, { avatar: avatarUrl })
    );
    await Promise.all(updatePromises);
  },

  async forgotPassword(email: string): Promise<void> {
    await apiClient.post("/auth/forgot-password", { email });
  },

  async resetPassword(token: string, password: string): Promise<void> {
    await apiClient.post("/auth/reset-password", { token, password });
  },

  async verifyEmail(token: string): Promise<void> {
    await apiClient.post("/auth/verify-email", { token });
  },

  logout(): void {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  },
};
