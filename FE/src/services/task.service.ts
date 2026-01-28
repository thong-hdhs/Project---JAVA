import apiClient from './apiClient';
import type { Task, TaskCreateRequest, TaskComment, Report, ReportCreateRequest } from '../types';

type BackendApiResponse<T> = {
  success: boolean;
  message?: string;
  data?: T;
  errors?: string[];
  statusCode?: number;
};

type BackendTaskResponse = {
  id: string;
  projectId?: string;
  assignedTo?: string;
  createdBy?: string;
  taskName?: string;
  description?: string;
  priority?: string;
  status?: string;
  dueDate?: string;
  completedDate?: string;
  excelTemplateUrl?: string;
  attachments?: string[];
  createdAt?: string;
  updatedAt?: string;
};

const parseDateOrUndefined = (v: unknown): Date | undefined => {
  if (!v) return undefined;
  const d = new Date(String(v));
  return Number.isNaN(d.getTime()) ? undefined : d;
};

const mapBackendTaskToTask = (t: BackendTaskResponse): Task => {
  const createdAt = parseDateOrUndefined(t.createdAt) || new Date();
  const updatedAt = parseDateOrUndefined(t.updatedAt) || createdAt;
  return {
    id: String(t.id || ''),
    project_id: String(t.projectId || ''),
    title: String(t.taskName || ''),
    description: String(t.description || ''),
    status: (String(t.status || 'TODO').toUpperCase() as Task['status']),
    priority: (String(t.priority || 'MEDIUM').toUpperCase() as Task['priority']),
    assigned_to: t.assignedTo ? String(t.assignedTo) : undefined,
    created_by: String(t.createdBy || ''),
    excel_template_url: t.excelTemplateUrl ? String(t.excelTemplateUrl) : undefined,
    attachments: Array.isArray(t.attachments) ? t.attachments : undefined,
    due_date: parseDateOrUndefined(t.dueDate),
    completed_at: parseDateOrUndefined(t.completedDate),
    created_at: createdAt,
    updated_at: updatedAt,
  };
};

export const taskService = {
  // Tasks
  async getTasks(params?: {
    project_id?: string;
    assigned_to?: string;
    status?: string;
    priority?: string;
  }): Promise<Task[]> {
    // Prefer backend APIs.
    if (params?.project_id) {
      const response = await apiClient.get<BackendApiResponse<BackendTaskResponse[]>>(
        `/api/tasks/project/${params.project_id}`,
      );
      if (!response.data?.success) {
        throw new Error(response.data?.message || response.data?.errors?.[0] || 'Failed to load tasks');
      }
      return (response.data?.data || []).map(mapBackendTaskToTask);
    }

    if (params?.assigned_to) {
      const response = await apiClient.get<BackendApiResponse<BackendTaskResponse[]>>(
        `/api/tasks/assignee/${params.assigned_to}`,
      );
      if (!response.data?.success) {
        throw new Error(response.data?.message || response.data?.errors?.[0] || 'Failed to load tasks');
      }
      return (response.data?.data || []).map(mapBackendTaskToTask);
    }

    return this.getMyTasks(params);
  },

  async getMyTasks(params?: { status?: string; priority?: string }): Promise<Task[]> {
    const response = await apiClient.get<BackendApiResponse<BackendTaskResponse[]>>('/api/tasks/me');
    if (!response.data?.success) {
      throw new Error(response.data?.message || response.data?.errors?.[0] || 'Failed to load my tasks');
    }
    const list = (response.data?.data || []).map(mapBackendTaskToTask);
    const status = params?.status ? String(params.status).toUpperCase() : '';
    const priority = params?.priority ? String(params.priority).toUpperCase() : '';
    return list.filter((t) => {
      if (status && String(t.status).toUpperCase() !== status) return false;
      if (priority && String(t.priority).toUpperCase() !== priority) return false;
      return true;
    });
  },

  async getTask(id: string): Promise<Task> {
    const response = await apiClient.get<BackendApiResponse<BackendTaskResponse>>(`/api/tasks/${id}`);
    if (!response.data?.success || !response.data?.data) {
      throw new Error(response.data?.message || response.data?.errors?.[0] || 'Failed to load task');
    }
    return mapBackendTaskToTask(response.data.data);
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
