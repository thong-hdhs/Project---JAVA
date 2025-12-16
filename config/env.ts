export const ENV = {
  API_BASE_URL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  CLOUDINARY_CLOUD_NAME: import.meta.env.VITE_CLOUDINARY_CLOUD_NAME || '',
  CLOUDINARY_UPLOAD_PRESET: import.meta.env.VITE_CLOUDINARY_UPLOAD_PRESET || '',
  APP_NAME: 'LabOdc',
  APP_VERSION: '1.0.0',
} as const;

// Kiểm tra biến môi trường bắt buộc
const validateEnv = () => {
  const requiredVars = ['API_BASE_URL'] as const;
  
  for (const varName of requiredVars) {
    if (!ENV[varName]) {
      console.warn(`Cảnh báo: ${varName} chưa được thiết lập trong biến môi trường`);
    }
  }
};

validateEnv();