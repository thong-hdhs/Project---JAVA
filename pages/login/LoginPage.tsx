import React from 'react';
import './LoginPage.css';

const LoginPage: React.FC = () => {
  return (
    <div className="login-container">
      <div className="login-card">

        <div className="logo-section">
           <img src="assets/images/logouth.png" alt="UTH Logo" className="login-logo" />
        </div>
        
        <h2 className="login-title">LabOdc</h2>
        
        <form className="login-form">
          <div className="input-field">
            <input type="text" placeholder="Tài khoản đăng nhập" required />
          </div>
          
          <div className="input-field password-group">
            <input type="password" placeholder="Mật khẩu" required />
            <span className="show-password-icon"></span>
          </div>
          
          <button type="submit" className="btn-login">ĐĂNG NHẬP</button>
        </form>
        
        <div className="login-footer">
          <a href="#" className="forgot-password">QUÊN MẬT KHẨU?</a>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;