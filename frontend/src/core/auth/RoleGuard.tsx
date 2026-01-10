import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '@/core/auth/AuthContext';

const RoleGuard: React.FC<{ allowedRoles: string[]; children: React.ReactNode }> = ({
  allowedRoles,
  children,
}) => {
  const { userRole } = useAuth();

  if (!allowedRoles.includes(userRole!)) {
    return <Navigate to="/unauthorized" />;
  }

  return <>{children}</>;
};

export default RoleGuard;
