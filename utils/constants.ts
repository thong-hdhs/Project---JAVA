// Vai trò người dùng
export const ROLES = {
  SYSTEM_ADMIN: 'SYSTEM_ADMIN',
  LAB_ADMIN: 'LAB_ADMIN',
  COMPANY: 'COMPANY',
  MENTOR: 'MENTOR',
  TALENT: 'TALENT',
  TALENT_LEADER: 'TALENT_LEADER',
} as const;

export type UserRole = typeof ROLES[keyof typeof ROLES];

// Trạng thái dự án
export const PROJECT_STATUS = {
  PENDING: 'PENDING',
  APPROVED: 'APPROVED',
  IN_PROGRESS: 'IN_PROGRESS',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
} as const;

export type ProjectStatus = typeof PROJECT_STATUS[keyof typeof PROJECT_STATUS];

// Nhãn hiển thị trạng thái dự án
export const PROJECT_STATUS_LABELS: Record<ProjectStatus, string> = {
  PENDING: 'Chờ duyệt',
  APPROVED: 'Đã duyệt',
  IN_PROGRESS: 'Đang thực hiện',
  COMPLETED: 'Hoàn thành',
  CANCELLED: 'Đã hủy',
};

// Trạng thái đơn ứng tuyển
export const APPLICATION_STATUS = {
  PENDING: 'PENDING',
  APPROVED: 'APPROVED',
  REJECTED: 'REJECTED',
} as const;

export const APPLICATION_STATUS_LABELS = {
  PENDING: 'Chờ xét duyệt',
  APPROVED: 'Đã chấp nhận',
  REJECTED: 'Đã từ chối',
};

// Trạng thái công việc
export const TASK_STATUS = {
  TODO: 'TODO',
  IN_PROGRESS: 'IN_PROGRESS',
  REVIEW: 'REVIEW',
  DONE: 'DONE',
} as const;

export const TASK_STATUS_LABELS = {
  TODO: 'Cần làm',
  IN_PROGRESS: 'Đang làm',
  REVIEW: 'Chờ review',
  DONE: 'Hoàn thành',
};

// Trạng thái thanh toán
export const PAYMENT_STATUS = {
  PENDING: 'PENDING',
  COMPLETED: 'COMPLETED',
  FAILED: 'FAILED',
  REFUNDED: 'REFUNDED',
} as const;

export const PAYMENT_STATUS_LABELS = {
  PENDING: 'Chờ thanh toán',
  COMPLETED: 'Đã thanh toán',
  FAILED: 'Thất bại',
  REFUNDED: 'Đã hoàn tiền',
};

// Phân bổ quỹ (70/20/10)
export const FUND_DISTRIBUTION = {
  TEAM: 0.7,
  MENTOR: 0.2,
  LAB: 0.1,
} as const;

// Giới hạn tải file
export const FILE_LIMITS = {
  MAX_SIZE_MB: 10,
  ALLOWED_IMAGE_TYPES: ['image/jpeg', 'image/png', 'image/webp'],
  ALLOWED_DOCUMENT_TYPES: [
    'application/pdf',
    'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    'application/vnd.ms-excel',
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
  ],
} as const;

// Phân trang
export const PAGINATION = {
  DEFAULT_PAGE: 1,
  DEFAULT_LIMIT: 10,
  MAX_LIMIT: 100,
} as const;

// Đường dẫn API
export const API_ROUTES = {
  AUTH: {
    LOGIN: '/auth/login',
    REGISTER: '/auth/register',
    LOGOUT: '/auth/logout',
    REFRESH: '/auth/refresh',
    FORGOT_PASSWORD: '/auth/forgot-password',
    RESET_PASSWORD: '/auth/reset-password',
    VERIFY_EMAIL: '/auth/verify-email',
  },
  USER: {
    PROFILE: '/user/profile',
    UPDATE_PROFILE: '/user/profile/update',
    UPLOAD_AVATAR: '/user/avatar',
    CHANGE_PASSWORD: '/user/change-password',
  },
  PROJECT: {
    LIST: '/projects',
    DETAIL: '/projects/:id',
    CREATE: '/projects/create',
    UPDATE: '/projects/:id/update',
    DELETE: '/projects/:id/delete',
    APPLY: '/projects/:id/apply',
    MY_PROJECTS: '/projects/my-projects',
  },
  TASK: {
    LIST: '/tasks',
    DETAIL: '/tasks/:id',
    CREATE: '/tasks/create',
    UPDATE: '/tasks/:id/update',
  },
  TEAM: {
    DETAIL: '/teams/:id',
    MEMBERS: '/teams/:id/members',
    REPORTS: '/teams/:id/reports',
    EVALUATIONS: '/teams/:id/evaluations',
  },
  FUND: {
    SUMMARY: '/funds/summary',
    REDISTRIBUTE: '/funds/redistribute',
    HISTORY: '/funds/history',
  },
} as const;

// Khóa lưu trữ LocalStorage
export const STORAGE_KEYS = {
  ACCESS_TOKEN: 'access_token',
  REFRESH_TOKEN: 'refresh_token',
  USER: 'user',
  THEME: 'theme',
} as const;