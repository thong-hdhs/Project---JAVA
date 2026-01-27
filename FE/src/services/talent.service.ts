import apiClient from './apiClient';

type BackendApiResponse<T> = {
  success: boolean;
  message?: string;
  data?: T;
  errors?: string[];
  timestamp?: string;
  statusCode?: number;
};

export type BackendTalentStatus = 'ACTIVE' | 'INACTIVE' | 'SUSPENDED' | string;

export type BackendTalentResponse = {
  id: string;
  userId?: string;
  studentCode?: string;
  major?: string;
  year?: number;
  skills?: string;
  certifications?: string;
  portfolioUrl?: string;
  githubUrl?: string;
  linkedinUrl?: string;
  gpa?: number | string;
  status?: BackendTalentStatus;
  createdAt?: string;
  updatedAt?: string;
};

export const talentService = {
  async listAllTalents(): Promise<BackendTalentResponse[]> {
    const response = await apiClient.get<BackendApiResponse<BackendTalentResponse[]>>('/api/v1/talents/');

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to load students';
      throw new Error(msg);
    }

    return response.data?.data || [];
  },

  async listTalentsByStatus(status: string): Promise<BackendTalentResponse[]> {
    const response = await apiClient.get<BackendApiResponse<BackendTalentResponse[]>>(`/api/v1/talents/status/${status}`);

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to load students';
      throw new Error(msg);
    }

    return response.data?.data || [];
  },
};
