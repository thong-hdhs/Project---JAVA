import apiClient from './apiClient';
import type {
  Project, ProjectCreateRequest, ProjectApplication, ProjectApplicationRequest,
  ProjectTeam, ProjectChangeRequest
} from '../types';

export const projectService = {
  // Projects
  async getProjects(params?: {
    status?: string;
    company_id?: string;
    mentor_id?: string;
    page?: number;
    limit?: number;
  }): Promise<{ data: Project[]; total: number }> {
    const response = await apiClient.get('/projects', { params });
    return response.data;
  },

  async getProject(id: string): Promise<Project> {
    const response = await apiClient.get(`/projects/${id}`);
    return response.data;
  },

  async createProject(data: ProjectCreateRequest): Promise<Project> {
    const response = await apiClient.post('/projects', data);
    return response.data;
  },

  async updateProject(id: string, data: Partial<Project>): Promise<Project> {
    const response = await apiClient.patch(`/projects/${id}`, data);
    return response.data;
  },

  async deleteProject(id: string): Promise<void> {
    await apiClient.delete(`/projects/${id}`);
  },

  // Applications
  async getApplications(params?: {
    project_id?: string;
    talent_id?: string;
    status?: string;
  }): Promise<ProjectApplication[]> {
    const response = await apiClient.get('/project-applications', { params });
    return response.data;
  },

  async applyForProject(data: ProjectApplicationRequest): Promise<ProjectApplication> {
    const response = await apiClient.post('/project-applications', data);
    return response.data;
  },

  async updateApplication(id: string, data: Partial<ProjectApplication>): Promise<ProjectApplication> {
    const response = await apiClient.patch(`/project-applications/${id}`, data);
    return response.data;
  },

  // Teams
  async getProjectTeams(projectId: string): Promise<ProjectTeam[]> {
    const response = await apiClient.get('/project-teams', {
      params: { project_id: projectId }
    });
    return response.data;
  },

  async addTeamMember(data: Omit<ProjectTeam, 'id' | 'joined_at'>): Promise<ProjectTeam> {
    const response = await apiClient.post('/project-teams', data);
    return response.data;
  },

  async removeTeamMember(id: string): Promise<void> {
    await apiClient.delete(`/project-teams/${id}`);
  },

  // Change requests
  async getChangeRequests(projectId: string): Promise<ProjectChangeRequest[]> {
    const response = await apiClient.get('/project-change-requests', {
      params: { project_id: projectId }
    });
    return response.data;
  },

  async createChangeRequest(data: Omit<ProjectChangeRequest, 'id' | 'created_at'>): Promise<ProjectChangeRequest> {
    const response = await apiClient.post('/project-change-requests', data);
    return response.data;
  },

  async updateChangeRequest(id: string, data: Partial<ProjectChangeRequest>): Promise<ProjectChangeRequest> {
    const response = await apiClient.patch(`/project-change-requests/${id}`, data);
    return response.data;
  },

  // Project Validation (Lab Admin)
  async validateProject(
    id: string,
    validationStatus: 'APPROVED' | 'REJECTED',
    rejectionReason?: string
  ): Promise<Project> {
    const response = await apiClient.patch(`/projects/${id}/validate`, {
      validation_status: validationStatus,
      rejection_reason: rejectionReason,
    });
    return response.data;
  },

  async getProjectsForValidation(params?: {
    validation_status?: string;
    page?: number;
    limit?: number;
  }): Promise<{ data: Project[]; total: number }> {
    const response = await apiClient.get('/projects/validation/pending', { params });
    return response.data;
  },

  async updateProjectPaymentStatus(
    id: string,
    paymentStatus: 'PENDING' | 'PAID' | 'FAILED'
  ): Promise<Project> {
    const response = await apiClient.patch(`/projects/${id}/payment-status`, {
      payment_status: paymentStatus,
    });
    return response.data;
  }
};
