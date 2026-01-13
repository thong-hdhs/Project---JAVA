import axios from 'axios';

const axiosClient = axios.create({
  baseURL: 'http://your-backend-api-url', // Set the base URL of your backend here
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor for attaching JWT token
axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('auth_token'); // Get JWT from localStorage
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor to handle errors (401, 403, etc.)
axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response.status === 401 || error.response.status === 403) {
      // Handle token expiry or unauthorized access
      console.log('Unauthorized or expired token');
    }
    return Promise.reject(error);
  }
);

export default axiosClient;
