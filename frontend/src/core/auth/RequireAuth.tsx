import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '@/core/auth/AuthContext';

const RequireAuth: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }

  return <>{children}</>;
};

export default RequireAuth;
