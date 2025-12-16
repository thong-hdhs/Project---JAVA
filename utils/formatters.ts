/**
 * Định dạng tiền tệ (VND)
 * @example formatCurrency(1000000) => "1.000.000 ₫"
 */
export const formatCurrency = (amount: number): string => {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND',
  }).format(amount);
};

/**
 * Định dạng ngày tháng
 * @example formatDate(new Date()) => "16/12/2025"
 */
export const formatDate = (date: string | Date): string => {
  return new Intl.DateTimeFormat('vi-VN').format(new Date(date));
};

/**
 * Định dạng ngày giờ
 * @example formatDateTime(new Date()) => "16/12/2025 13:45"
 */
export const formatDateTime = (date: string | Date): string => {
  return new Intl.DateTimeFormat('vi-VN', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  }).format(new Date(date));
};

/**
 * Định dạng thời gian tương đối
 * @example formatRelativeTime(yesterday) => "1 ngày trước"
 */
export const formatRelativeTime = (date: string | Date): string => {
  const now = new Date();
  const then = new Date(date);
  const diffMs = now.getTime() - then.getTime();
  const diffMins = Math.floor(diffMs / 60000);
  const diffHours = Math.floor(diffMs / 3600000);
  const diffDays = Math.floor(diffMs / 86400000);

  if (diffMins < 1) return 'Vừa xong';
  if (diffMins < 60) return `${diffMins} phút trước`;
  if (diffHours < 24) return `${diffHours} giờ trước`;
  if (diffDays < 7) return `${diffDays} ngày trước`;
  return formatDate(date);
};

/**
 * Định dạng kích thước file
 * @example formatFileSize(1024) => "1 KB"
 */
export const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes';
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i];
};

/**
 * Cắt ngắn văn bản
 * @example truncate("Văn bản dài...", 10) => "Văn bản d..."
 */
export const truncate = (text: string, length: number): string => {
  if (text.length <= length) return text;
  return text.substring(0, length) + '...';
};