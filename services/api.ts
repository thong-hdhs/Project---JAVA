import axios, { AxiosError, InternalAxiosRequestConfig } from 'axios';
import { ENV } from '@/config/env';
import { storage } from '@/utils/storage';
import { ApiResponse } from '@/types/common.types';

// Tạo axios instance
const api = axios.create({
  baseURL: ENV.API_BASE_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor - Thêm auth token vào header
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = storage.getAccessToken();
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// Response interceptor - Xử lý lỗi và làm mới token
api.interceptors.response.use(
  (response) => {
    return response.data;
  },
  async (error: AxiosError<ApiResponse>) => {
    const originalRequest = error.config as InternalAxiosRequestConfig & {
      _retry?: boolean;
    };

    // Xử lý 401 Unauthorized - Token hết hạn
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = storage.getRefreshToken();
        if (!refreshToken) {
          throw new Error('Không có refresh token');
        }

        // Gọi API làm mới token
        const response = await axios.post<ApiResponse<{ accessToken: string }>>(
          `${ENV.API_BASE_URL}/auth/refresh`,
          { refreshToken }
        );

        const { accessToken } = response.data.data!;
        storage.setAccessToken(accessToken);

        // Thử lại request ban đầu
        if (originalRequest.headers) {
          originalRequest.headers.Authorization = `Bearer ${accessToken}`;
        }
        return api(originalRequest);
      } catch (refreshError) {
        // Làm mới token thất bại - đăng xuất người dùng
        storage.clearAuth();
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }

    // Xử lý các lỗi khác
    const errorMessage =
      error.response?.data?.message ||
      error.message ||
      'Đã có lỗi xảy ra. Vui lòng thử lại.';

    return Promise.reject({
      message: errorMessage,
      status: error.response?.status,
      data: error.response?.data,
    });
  }
);

export default api;

// Các hàm helper cho các loại request

/**
 * GET request
 */
export const get = <T>(url: string, params?: any): Promise<ApiResponse<T>> => {
  return api.get(url, { params });
};

/**
 * POST request
 */
export const post = <T>(url: string, data?: any): Promise<ApiResponse<T>> => {
  return api.post(url, data);
};

/**
 * PUT request
 */
export const put = <T>(url: string, data?: any): Promise<ApiResponse<T>> => {
  return api.put(url, data);
};

/**
 * DELETE request
 */
export const del = <T>(url: string): Promise<ApiResponse<T>> => {
  return api.delete(url);
};

/**
 * PATCH request
 */
export const patch = <T>(url: string, data?: any): Promise<ApiResponse<T>> => {
  return api.patch(url, data);
};