import apiClient from './apiClient';
import type { Task, TaskCreateRequest, TaskComment, Report, ReportCreateRequest } from '../types';

export const taskService = {
  // Tasks
  async getTasks(params?: {
    project_id?: string;
    assigned_to?: string;
    status?: string;
    priority?: string;
  }): Promise<Task[]> {
    const response = await apiClient.get('/tasks', { params });
    return response.data;
  },

  async getTask(id: string): Promise<Task> {
    const response = await apiClient.get(`/tasks/${id}`);
    return response.data;
  },

  async createTask(data: TaskCreateRequest): Promise<Task> {
    const response = await apiClient.post('/tasks', data);
    return response.data;
  },

  async updateTask(id: string, data: Partial<Task>): Promise<Task> {
    const response = await apiClient.patch(`/tasks/${id}`, data);
    return response.data;
  },

  async deleteTask(id: string): Promise<void> {
    await apiClient.delete(`/tasks/${id}`);
  },

  // Task comments
  async getTaskComments(taskId: string): Promise<TaskComment[]> {
    const response = await apiClient.get('/task-comments', {
      params: { task_id: taskId }
    });
    return response.data;
  },

  async addTaskComment(data: Omit<TaskComment, 'id' | 'created_at'>): Promise<TaskComment> {
    const response = await apiClient.post('/task-comments', data);
    return response.data;
  },

  async updateTaskComment(id: string, content: string): Promise<TaskComment> {
    const response = await apiClient.patch(`/task-comments/${id}`, { content });
    return response.data;
  },

  async deleteTaskComment(id: string): Promise<void> {
    await apiClient.delete(`/task-comments/${id}`);
  },

  // Reports
  async getReports(params?: {
    project_id?: string;
    report_type?: string;
    status?: string;
  }): Promise<Report[]> {
    const response = await apiClient.get('/reports', { params });
    return response.data;
  },

  async getReport(id: string): Promise<Report> {
    const response = await apiClient.get(`/reports/${id}`);
    return response.data;
  },

  async createReport(data: ReportCreateRequest): Promise<Report> {
    const response = await apiClient.post('/reports', data);
    return response.data;
  },

  async updateReport(id: string, data: Partial<Report>): Promise<Report> {
    const response = await apiClient.patch(`/reports/${id}`, data);
    return response.data;
  },

  async deleteReport(id: string): Promise<void> {
    await apiClient.delete(`/reports/${id}`);
  }
};
