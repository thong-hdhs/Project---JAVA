import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom'; // import công cụ

// import các trang 
import LandingPage from './pages/landing/LandingPage';
import LoginPage from './pages/login/LoginPage'; 

// xóa dòng import 

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        {/* Đường dẫn mặc định là trang chủ */}
        <Route path="/" element={<LandingPage />} />
        {/* Đường dẫn khi nhấn đăng nhập */}
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);