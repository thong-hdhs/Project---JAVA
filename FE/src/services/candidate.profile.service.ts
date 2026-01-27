import apiClient from "./apiClient";

/**
 * Lấy hồ sơ candidate (talent)
 */
export const getCandidateProfile = (candidateId: string) =>
  apiClient.get(`/api/v1/talents/${candidateId}`);

/**
 * Cập nhật hồ sơ candidate
 */
export const updateCandidateProfile = (
  candidateId: string,
  data: {
    studentCode: string;
    major?: string;
    year?: number;
    skills?: string;
    certifications?: string;
    portfolioUrl?: string;
    githubUrl?: string;
    linkedinUrl?: string;
  },
) => apiClient.put(`/api/v1/talents/${candidateId}`, data);

/**
 * Tạo hồ sơ candidate
 */
export const createCandidateProfile = (data: {
  studentCode: string;
  major?: string;
  year?: number;
  skills?: string;
  certifications?: string;
  portfolioUrl?: string;
  githubUrl?: string;
  linkedinUrl?: string;
}) => apiClient.post(`/api/v1/talents/`, data);
