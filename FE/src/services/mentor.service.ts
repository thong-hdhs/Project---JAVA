import apiClient from './apiClient';

type BackendApiResponse<T> = {
  success: boolean;
  message?: string;
  data?: T;
  errors?: string[];
  timestamp?: string;
  statusCode?: number;
};

export type BackendMentorStatus = 'ACTIVE' | 'INACTIVE' | 'SUSPENDED' | string;

export type BackendMentorResponse = {
  id: string;
  userId?: string;
  expertise?: string;
  yearsExperience?: number;
  bio?: string;
  rating?: number | string;
  totalProjects?: number;
  status?: BackendMentorStatus;
  createdAt?: string;
  updatedAt?: string;
};

export const mentorService = {
  async listAllMentors(): Promise<BackendMentorResponse[]> {
    const response = await apiClient.get<BackendApiResponse<BackendMentorResponse[]>>('/api/v1/mentors/');

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to load mentors';
      throw new Error(msg);
    }

    return response.data?.data || [];
  },

  async listMentorsByStatus(status: string): Promise<BackendMentorResponse[]> {
    const response = await apiClient.get<BackendApiResponse<BackendMentorResponse[]>>(`/api/v1/mentors/status/${status}`);

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to load mentors';
      throw new Error(msg);
    }

    return response.data?.data || [];
  },
};
