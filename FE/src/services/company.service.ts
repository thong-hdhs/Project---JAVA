import apiClient from './apiClient';
import type { Company, CompanyOnboardingRequest } from '../types';

export const companyService = {
  async getCompanies(params?: {
    status?: string;
    industry?: string;
    page?: number;
    limit?: number;
  }): Promise<{ data: Company[]; total: number }> {
    const response = await apiClient.get('/companies', { params });
    return response.data;
  },

  async getCompany(id: string): Promise<Company> {
    const response = await apiClient.get(`/companies/${id}`);
    return response.data;
  },

  async createCompanyOnboarding(data: CompanyOnboardingRequest): Promise<Company> {
    const response = await apiClient.post('/companies', data);
    return response.data;
  },

  async updateCompany(id: string, data: Partial<Company>): Promise<Company> {
    const response = await apiClient.patch(`/companies/${id}`, data);
    return response.data;
  },

  async validateCompany(id: string, data: {
    status: 'APPROVED' | 'REJECTED';
    rejection_reason?: string;
    validated_by: string;
  }): Promise<Company> {
    const response = await apiClient.patch(`/companies/${id}/validate`, data);
    return response.data;
  },

  async getMyCompany(): Promise<Company> {
    const response = await apiClient.get('/companies/my-company');
    return response.data;
  }
};
