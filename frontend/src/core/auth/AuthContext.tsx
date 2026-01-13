import React, { createContext, useState, useContext, useMemo, useCallback } from 'react';

interface AuthContextType {
  isAuthenticated: boolean;
  login: () => void;
  logout: () => void;
  userRole: string | null;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC = ({ children }: { children?: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userRole, setUserRole] = useState<string | null>(null);

  const login = useCallback(() => setIsAuthenticated(true), []);
  const logout = useCallback(() => {
    setIsAuthenticated(false);
    setUserRole(null);
  }, []);

  const value = useMemo(
    () => ({ isAuthenticated, login, logout, userRole }),
    [isAuthenticated, login, logout, userRole]
  );

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
