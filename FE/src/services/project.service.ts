import apiClient from './apiClient';
import type {
  Project, ProjectCreateRequest, ProjectApplication, ProjectApplicationRequest,
  ProjectTeam, ProjectChangeRequest
} from '../types';

type StoredAuthUser = { role?: string; id?: string; email?: string } | null;

const getStoredUser = (): StoredAuthUser => {
  try {
    const raw = localStorage.getItem('user');
    return raw ? (JSON.parse(raw) as StoredAuthUser) : null;
  } catch {
    return null;
  }
};

const isAxiosNotFoundOrNetwork = (error: any): boolean => {
  const status = error?.response?.status;
  return status === 404 || status === 502 || status === 503 || status === 504 || !error?.response;
};

const now = () => new Date();

const mockCompanyId = 'demo-company-entity';

const mockProjects: Project[] = [
  {
    id: 'demo-project-pending',
    project_name: 'Pending Approval Project (Demo)',
    description: 'Demo project waiting for Lab Admin approval/rejection.',
    requirements: 'React + Spring Boot basics. This is mock data for UI testing.',
    budget: 25000,
    duration_months: 3,
    max_team_size: 5,
    required_skills: ['React', 'TypeScript', 'Spring Boot'],
    status: 'PENDING',
    validation_status: 'PENDING',
    payment_status: 'NOT_REQUIRED',
    company_id: mockCompanyId,
    mentor_id: undefined,
    main_mentor_id: undefined,
    created_by: 'demo-company',
    created_at: now(),
    updated_at: now(),
  },
  {
    id: 'demo-project-approved',
    project_name: 'Approved Project (Demo) - QR Payment Test',
    description: 'Approved demo project so Company can click Pay and see the QR modal.',
    requirements: 'Use the Pay button to open QR code modal.',
    budget: 50000,
    duration_months: 6,
    max_team_size: 6,
    required_skills: ['React', 'Node.js'],
    status: 'APPROVED',
    validation_status: 'APPROVED',
    payment_status: 'PENDING',
    company_id: mockCompanyId,
    mentor_id: undefined,
    main_mentor_id: undefined,
    created_by: 'demo-company',
    created_at: now(),
    updated_at: now(),
  },
];

const getMockProjectsForUser = (params?: { status?: string }) => {
  const user = getStoredUser();
  const role = (user?.role || '').toUpperCase();

  let data = [...mockProjects];

  // Lab Admin validation screen asks for status=PENDING; keep only pending in that case.
  if (params?.status) {
    data = data.filter((p) => p.status === params.status);
  }

  // Company screen should mainly see its approved project to test payment.
  if (role === 'COMPANY') {
    data = data.filter((p) => p.validation_status === 'APPROVED');
  }

  // Other roles: return everything (or whatever filter applied).
  return { data, total: data.length };
};

export const projectService = {
  // Projects
  async getProjects(params?: {
    status?: string;
    company_id?: string;
    mentor_id?: string;
    page?: number;
    limit?: number;
  }): Promise<{ data: Project[]; total: number }> {
    try {
      const response = await apiClient.get('/projects', { params });
      return response.data;
    } catch (error: any) {
      if (isAxiosNotFoundOrNetwork(error)) {
        return getMockProjectsForUser({ status: params?.status });
      }
      throw error;
    }
  },

  async getProject(id: string): Promise<Project> {
    try {
      const response = await apiClient.get(`/projects/${id}`);
      return response.data;
    } catch (error: any) {
      if (isAxiosNotFoundOrNetwork(error)) {
        const found = mockProjects.find((p) => p.id === id);
        if (found) return found;
      }
      throw error;
    }
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
    try {
      const response = await apiClient.patch(`/projects/${id}/validate`, {
        validation_status: validationStatus,
        rejection_reason: rejectionReason,
      });
      return response.data;
    } catch (error: any) {
      if (isAxiosNotFoundOrNetwork(error)) {
        const found = mockProjects.find((p) => p.id === id) || mockProjects[0];
        return {
          ...found,
          validation_status: validationStatus,
          status: validationStatus === 'APPROVED' ? 'APPROVED' : 'REJECTED',
          rejection_reason: validationStatus === 'REJECTED' ? rejectionReason : undefined,
          updated_at: now(),
        };
      }
      throw error;
    }
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
