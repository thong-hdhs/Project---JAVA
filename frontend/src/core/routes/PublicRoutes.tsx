import React from 'react';
import { Route, Routes } from 'react-router-dom';
import LandingPage from '@/Ml_labodc/public/pages/LandingPage';
import LoginPage from '@/Ml_labodc/public/pages/LoginPage';
import RegisterPage from '@/Ml_labodc/public/pages/RegisterPage';

const PublicRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
    </Routes>
  );
};

export default PublicRoutes;
