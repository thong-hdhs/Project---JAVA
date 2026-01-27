import apiClient from './apiClient';
import type {
  Project, ProjectCreateRequest, ProjectApplication, ProjectApplicationRequest,
  ProjectTeam, ProjectChangeRequest
} from '../types';

type BackendApiResponse<T> = {
  success: boolean;
  message?: string;
  data?: T;
  errors?: string[];
  timestamp?: string;
  statusCode?: number;
};

type BackendProjectStatus = 'DRAFT' | 'SUBMITTED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED' | 'ON_HOLD';
type BackendValidationStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export type BackendProjectCreateRequest = {
  companyId: string;
  projectName: string;
  description?: string;
  requirements?: string;
  budget?: number;
  durationMonths?: number;
  startDate?: string; // YYYY-MM-DD
  endDate?: string; // YYYY-MM-DD
  maxTeamSize?: number;
  requiredSkills?: string; // comma-separated
};

export type BackendProjectResponse = {
  id: string;
  companyId?: string;
  mentorId?: string;
  projectName?: string;
  projectCode?: string;
  description?: string;
  requirements?: string;
  budget?: number | string;
  durationMonths?: number;
  startDate?: string;
  endDate?: string;
  actualEndDate?: string;
  status?: BackendProjectStatus | string;
  validationStatus?: BackendValidationStatus | string;
  validatedById?: string;
  validatedAt?: string;
  rejectionReason?: string;
  maxTeamSize?: number;
  requiredSkills?: string;
  createdAt?: string;
  updatedAt?: string;
};

const parseDateOrUndefined = (v: unknown): Date | undefined => {
  if (!v) return undefined;
  const d = new Date(String(v));
  return Number.isNaN(d.getTime()) ? undefined : d;
};

const parseNumberOrZero = (v: unknown): number => {
  if (v === null || v === undefined || v === '') return 0;
  const n = typeof v === 'number' ? v : Number(String(v));
  return Number.isFinite(n) ? n : 0;
};

const splitSkills = (skills: unknown): string[] => {
  if (!skills) return [];
  if (Array.isArray(skills)) return skills.map(String).map((s) => s.trim()).filter(Boolean);
  return String(skills)
    .split(',')
    .map((s) => s.trim())
    .filter(Boolean);
};

const mapBackendProjectToProject = (p: BackendProjectResponse): Project => {
  const validationStatus = String(p.validationStatus || '').toUpperCase();
  const backendStatus = String(p.status || '').toUpperCase();

  // UI has historically used status like PENDING/APPROVED/REJECTED.
  // BE uses SUBMITTED + validationStatus. Map to preserve existing UI expectations.
  let uiStatus: any = backendStatus;
  if (backendStatus === 'SUBMITTED') uiStatus = 'PENDING';
  if (validationStatus === 'APPROVED') uiStatus = 'APPROVED';
  if (validationStatus === 'REJECTED') uiStatus = 'REJECTED';

  const createdAt = parseDateOrUndefined(p.createdAt) || now();
  const updatedAt = parseDateOrUndefined(p.updatedAt) || now();

  return {
    id: p.id,
    project_name: p.projectName || '',
    description: p.description || '',
    requirements: p.requirements || '',
    budget: parseNumberOrZero(p.budget),
    duration_months: Number(p.durationMonths || 0),
    start_date: parseDateOrUndefined(p.startDate),
    end_date: parseDateOrUndefined(p.endDate),
    max_team_size: Number(p.maxTeamSize || 0),
    required_skills: splitSkills(p.requiredSkills),
    status: uiStatus,
    validation_status: (validationStatus as any) || 'PENDING',
    payment_status: 'NOT_REQUIRED',
    rejection_reason: p.rejectionReason || undefined,
    validated_by: p.validatedById || undefined,
    validated_at: parseDateOrUndefined(p.validatedAt),
    company_id: p.companyId || '',
    mentor_id: p.mentorId || undefined,
    main_mentor_id: undefined,
    created_by: p.companyId || 'company',
    created_at: createdAt,
    updated_at: updatedAt,
  };
};

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

  // ====== Company: create + submit project for appraisal (BE: /api/projects) ======
  async listAllProjectsFromBackend(): Promise<Project[]> {
    try {
      const response = await apiClient.get<BackendApiResponse<BackendProjectResponse[]>>('/api/projects/');

      if (!response.data?.success) {
        const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to load projects';
        throw new Error(msg);
      }

      return (response.data?.data || []).map(mapBackendProjectToProject);
    } catch (error: any) {
      const backendData = error?.response?.data;
      const apiMsg = backendData?.message || backendData?.error;
      const apiErrors = backendData?.errors;
      const msg = apiMsg || (Array.isArray(apiErrors) ? apiErrors[0] : null) || error?.message || 'Failed to load projects';
      throw new Error(msg);
    }
  },

  async createProjectForAppraisal(data: BackendProjectCreateRequest): Promise<Project> {
    try {
      const response = await apiClient.post<BackendApiResponse<BackendProjectResponse>>('/api/projects/', data);

      if (!response.data?.success || !response.data?.data) {
        const msg = response.data?.message || response.data?.errors?.[0] || 'Create project failed';
        throw new Error(msg);
      }

      return mapBackendProjectToProject(response.data.data);
    } catch (error: any) {
      const backendData = error?.response?.data;
      const apiMsg = backendData?.message || backendData?.error;
      const apiErrors = backendData?.errors;
      const msg = apiMsg || (Array.isArray(apiErrors) ? apiErrors[0] : null) || error?.message || 'Create project failed';
      throw new Error(msg);
    }
  },

  async submitProjectForAppraisal(projectId: string): Promise<Project> {
    try {
      const response = await apiClient.put<BackendApiResponse<BackendProjectResponse>>(`/api/projects/${projectId}/submit`);

      if (!response.data?.success || !response.data?.data) {
        const msg = response.data?.message || response.data?.errors?.[0] || 'Submit project failed';
        throw new Error(msg);
      }

      return mapBackendProjectToProject(response.data.data);
    } catch (error: any) {
      const backendData = error?.response?.data;
      const apiMsg = backendData?.message || backendData?.error;
      const apiErrors = backendData?.errors;
      const msg = apiMsg || (Array.isArray(apiErrors) ? apiErrors[0] : null) || error?.message || 'Submit project failed';
      throw new Error(msg);
    }
  },

  // ====== Lab Admin: list pending + approve/reject (BE: /api/v1/lab-admins) ======
  async listPendingProjectsForValidation(): Promise<Project[]> {
    try {
      const response = await apiClient.get<BackendApiResponse<BackendProjectResponse[]>>('/api/v1/lab-admins/pending-projects');

      if (!response.data?.success) {
        const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to load pending projects';
        throw new Error(msg);
      }

      return (response.data?.data || []).map(mapBackendProjectToProject);
    } catch (error: any) {
      const backendData = error?.response?.data;
      const apiMsg = backendData?.message || backendData?.error;
      const apiErrors = backendData?.errors;
      const msg = apiMsg || (Array.isArray(apiErrors) ? apiErrors[0] : null) || error?.message || 'Failed to load pending projects';
      throw new Error(msg);
    }
  },

  async approvePendingProject(projectId: string, labAdminId: string): Promise<void> {
    try {
      const response = await apiClient.post<BackendApiResponse<string>>(
        `/api/v1/lab-admins/validate-project/${projectId}`,
        null,
        { params: { labAdminId } },
      );

      if (!response.data?.success) {
        const msg = response.data?.message || response.data?.errors?.[0] || 'Approve project failed';
        throw new Error(msg);
      }
    } catch (error: any) {
      const backendData = error?.response?.data;
      const apiMsg = backendData?.message || backendData?.error;
      const apiErrors = backendData?.errors;
      const msg = apiMsg || (Array.isArray(apiErrors) ? apiErrors[0] : null) || error?.message || 'Approve project failed';
      throw new Error(msg);
    }
  },

  async rejectPendingProject(projectId: string, reason: string, labAdminId: string): Promise<void> {
    try {
      const response = await apiClient.post<BackendApiResponse<string>>(
        `/api/v1/lab-admins/reject-project/${projectId}`,
        null,
        { params: { reason, labAdminId } },
      );

      if (!response.data?.success) {
        const msg = response.data?.message || response.data?.errors?.[0] || 'Reject project failed';
        throw new Error(msg);
      }
    } catch (error: any) {
      const backendData = error?.response?.data;
      const apiMsg = backendData?.message || backendData?.error;
      const apiErrors = backendData?.errors;
      const msg = apiMsg || (Array.isArray(apiErrors) ? apiErrors[0] : null) || error?.message || 'Reject project failed';
      throw new Error(msg);
    }
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
