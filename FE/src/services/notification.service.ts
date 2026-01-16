import apiClient from './apiClient';
import type { Notification } from '../types';

export const notificationService = {
  async getNotifications(params?: {
    is_read?: boolean;
    page?: number;
    limit?: number;
  }): Promise<{ data: Notification[]; total: number; unread_count: number }> {
    const response = await apiClient.get('/notifications', { params });
    return response.data;
  },

  async markAsRead(id: string): Promise<Notification> {
    const response = await apiClient.patch(`/notifications/${id}/read`);
    return response.data;
  },

  async markAllAsRead(): Promise<void> {
    await apiClient.patch('/notifications/mark-all-read');
  },

  async deleteNotification(id: string): Promise<void> {
    await apiClient.delete(`/notifications/${id}`);
  },

  async getUnreadCount(): Promise<number> {
    const response = await apiClient.get('/notifications/unread-count');
    return response.data.count;
  }
};
