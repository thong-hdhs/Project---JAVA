// Authentication & User Types
export type UserRole = 'SYSTEM_ADMIN' | 'LAB_ADMIN' | 'COMPANY' | 'MENTOR' | 'TALENT' | 'TALENT_LEADER';

export interface User {
  id: string;
  email: string;
  fullName: string;
  username: string;
  phone?: string;
  avatarUrl?: string;
  roles: string[];
  permissions: string[];
  isActive: boolean;
  emailVerified: boolean;
  emailVerifiedAt?: string;
  lastLoginAt?: string;
  createdAt: string;
  updatedAt: string;
}

export interface AuthState {
  user: User | null;
  isAuth: boolean;
  token?: string;
}

export interface LoginRequest {
  // Backend uses `username`. Keep `email` optional for backward compatibility.
  username?: string;
  email?: string;
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
