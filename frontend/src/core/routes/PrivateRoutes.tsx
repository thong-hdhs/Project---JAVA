import React from 'react';
import { Route, Routes } from 'react-router-dom';
import RequireAuth from '@/core/auth/RequireAuth';
import DashboardLayout from '@/core/layout/DashboardLayout';
import MyProjects from '@/Ml_labodc/candidate/pages/MyProjects';

const PrivateRoutes = () => {
  return (
    <Routes>
      <Route path="/dashboard" element={<RequireAuth><DashboardLayout><MyProjects /></DashboardLayout></RequireAuth>} />
    </Routes>
  );
};

export default PrivateRoutes;
