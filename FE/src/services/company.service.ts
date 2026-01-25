import apiClient from './apiClient';
import type { Company, CompanyOnboardingRequest } from '../types';

const isAxiosNotFoundOrNetwork = (error: any): boolean => {
  const status = error?.response?.status;
  return status === 404 || status === 502 || status === 503 || status === 504 || !error?.response;
};

const now = () => new Date();

const mockCompany: Company = {
  id: 'demo-company-entity',
  company_name: 'TechCorp (Demo)',
  tax_code: 'DEMO-TAX-001',
  address: '123 Demo Street, Ho Chi Minh City',
  industry: 'Technology',
  website: 'https://example.com',
  company_size: 50,
  description: 'Demo company for UI testing.',
  contact_email: 'company@techcorp.com',
  status: 'APPROVED',
  created_by: 'demo-company',
  created_at: now(),
  updated_at: now(),
};

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
    try {
      const response = await apiClient.get(`/companies/${id}`);
      return response.data;
    } catch (error: any) {
      if (isAxiosNotFoundOrNetwork(error)) {
        if (id === mockCompany.id) return mockCompany;
      }
      throw error;
    }
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
