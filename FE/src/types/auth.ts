// Authentication & User Types
export type UserRole = 'SYSTEM_ADMIN' | 'LAB_ADMIN' | 'COMPANY' | 'MENTOR' | 'TALENT' | 'TALENT_LEADER';

export interface User {
  id: string;
  email: string;
  full_name: string;
  phone?: string;
  avatar_url?: string;
  role: UserRole;
  is_active: boolean;
  email_verified: boolean;
  last_login_at?: Date;
  created_at: Date;
  updated_at: Date;
}

export interface AuthState {
  user: User | null;
  isAuth: boolean;
  token?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  full_name: string;
  role: UserRole;
}

export interface AuthResponse {
  user: User;
  token: string;
}
