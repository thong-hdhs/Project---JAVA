import apiClient from './apiClient';

type BackendApiResponse<T> = {
  success: boolean;
  message?: string;
  data?: T;
  errors?: string[];
  timestamp?: string;
  statusCode?: number;
};

export type MentorInvitationStatus = 'PENDING' | 'ACCEPTED' | 'REJECTED' | string;

export type MentorInvitationRequest = {
  projectId: string;
  mentorId: string;
  invitationMessage?: string;
  proposedFeePercentage?: number;
};

export type BackendMentorInvitationResponse = {
  id: string;
  projectId?: string;
  mentorId?: string;
  invitedBy?: string;
  invitationMessage?: string;
  proposedFeePercentage?: number | string;
  status?: MentorInvitationStatus;
  respondedAt?: string;
  createdAt?: string;
};

export const mentorInvitationService = {
  async createInvitation(payload: MentorInvitationRequest): Promise<BackendMentorInvitationResponse> {
    const response = await apiClient.post<BackendApiResponse<BackendMentorInvitationResponse>>(
      '/api/mentor-invitations/',
      payload,
    );

    if (!response.data?.success || !response.data?.data) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to create invitation';
      throw new Error(msg);
    }

    return response.data.data;
  },

  async listByProject(projectId: string): Promise<BackendMentorInvitationResponse[]> {
    const response = await apiClient.get<BackendApiResponse<BackendMentorInvitationResponse[]>>(
      `/api/mentor-invitations/by-project/${projectId}`,
    );

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Failed to load invitations';
      throw new Error(msg);
    }

    return response.data?.data || [];
  },
};
