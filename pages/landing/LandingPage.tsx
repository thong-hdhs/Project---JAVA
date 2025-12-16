import React from 'react';
import './LandingPage.css';
import { useNavigate } from 'react-router-dom';

const LandingPage: React.FC = () => {
    const navigate = useNavigate();
  return (
    <div className="landing-container">
      {/* Header nằm trên cùng */}
      <header className="header">
        <div className="header-left">
          <img src ="assets/images/logouth.png" alt="UTH Logo" className="logo" />
          <span  className="lab-name" >LabOdc</span>
        </div>
        <nav className="header-right">
          <button className="login-btn" onClick={() => navigate('/login')}>Đăng nhập hệ thống</button>
        </nav>
      </header>

      <main className="content">
        <div className="hero-box">
          <h1>Hệ thống quản lý dự án thực tế</h1>
          <p>Cầu nối giữa Doanh nghiệp và Sinh viên UTH qua mô hình ODC phi lợi nhuận.</p>
        </div>

        <h2 className="fund-rule-title">Quy tắc phân bổ quỹ minh bạch</h2>
        
        <div className="grid-container">
          <div className="card">
            <h3>70%</h3>
            <p>Nhóm sinh viên thực hiện</p>
          </div>
          <div className="card">
            <h3>20%</h3>
            <p>Người hướng dẫn (Mentor)</p>
          </div>
          <div className="card">
            <h3>10%</h3>
            <p>Quỹ vận hành Lab</p>
          </div>
        </div>
      </main>
    </div>
  );
};

export default LandingPage;