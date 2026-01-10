import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from '@/core/navigation/Navbar';
import Sidebar from '@/core/navigation/Sidebar';

const DashboardLayout: React.FC<{ children?: React.ReactNode }> = ({ children }) => {
  return (
    <div>
      <Navbar />
      <div style={{ display: 'flex' }}>
        <Sidebar />
        <main>
          {children ?? <Outlet />}
        </main>
      </div>
    </div>
  );
};

export default DashboardLayout;
