import { STORAGE_KEYS } from './constants';

/**
 * Tiện ích quản lý localStorage có kiểm tra kiểu dữ liệu
 */
class StorageUtil {
  private getItem<T>(key: string): T | null {
    try {
      const item = localStorage.getItem(key);
      return item ? JSON.parse(item) : null;
    } catch {
      return null;
    }
  }

  private setItem<T>(key: string, value: T): void {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
      console.error('Lỗi khi lưu vào localStorage:', error);
    }
  }

  private removeItem(key: string): void {
    try {
      localStorage.removeItem(key);
    } catch (error) {
      console.error('Lỗi khi xóa khỏi localStorage:', error);
    }
  }

  // Access Token
  getAccessToken(): string | null {
    return this.getItem<string>(STORAGE_KEYS.ACCESS_TOKEN);
  }

  setAccessToken(token: string): void {
    this.setItem(STORAGE_KEYS.ACCESS_TOKEN, token);
  }

  removeAccessToken(): void {
    this.removeItem(STORAGE_KEYS.ACCESS_TOKEN);
  }

  // Refresh Token
  getRefreshToken(): string | null {
    return this.getItem<string>(STORAGE_KEYS.REFRESH_TOKEN);
  }

  setRefreshToken(token: string): void {
    this.setItem(STORAGE_KEYS.REFRESH_TOKEN, token);
  }

  removeRefreshToken(): void {
    this.removeItem(STORAGE_KEYS.REFRESH_TOKEN);
  }

  // Thông tin người dùng
  getUser<T>(): T | null {
    return this.getItem<T>(STORAGE_KEYS.USER);
  }

  setUser<T>(user: T): void {
    this.setItem(STORAGE_KEYS.USER, user);
  }

  removeUser(): void {
    this.removeItem(STORAGE_KEYS.USER);
  }

  // Xóa toàn bộ dữ liệu xác thực
  clearAuth(): void {
    this.removeAccessToken();
    this.removeRefreshToken();
    this.removeUser();
  }

  // Giao diện (theme)
  getTheme(): 'light' | 'dark' | null {
    return this.getItem<'light' | 'dark'>(STORAGE_KEYS.THEME);
  }

  setTheme(theme: 'light' | 'dark'): void {
    this.setItem(STORAGE_KEYS.THEME, theme);
  }
}

export const storage = new StorageUtil();