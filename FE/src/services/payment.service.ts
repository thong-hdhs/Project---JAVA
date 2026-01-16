import apiClient from './apiClient';
import type {
  Payment, PaymentCreateRequest, FundAllocation, FundDistribution,
  MentorPayment, LabFundAdvance
} from '../types';

export const paymentService = {
  // Payments
  async getPayments(params?: {
    project_id?: string;
    status?: string;
    payment_type?: string;
  }): Promise<Payment[]> {
    const response = await apiClient.get('/payments', { params });
    return response.data;
  },

  async getPayment(id: string): Promise<Payment> {
    const response = await apiClient.get(`/payments/${id}`);
    return response.data;
  },

  async createPayment(data: PaymentCreateRequest): Promise<Payment> {
    const response = await apiClient.post('/payments', data);
    return response.data;
  },

  async updatePayment(id: string, data: Partial<Payment>): Promise<Payment> {
    const response = await apiClient.patch(`/payments/${id}`, data);
    return response.data;
  },

  // Fund Allocations
  async getFundAllocations(params?: { project_id?: string }): Promise<FundAllocation[]> {
    const response = await apiClient.get('/fund-allocations', { params });
    return response.data;
  },

  async createFundAllocation(data: Omit<FundAllocation, 'id' | 'created_at'>): Promise<FundAllocation> {
    const response = await apiClient.post('/fund-allocations', data);
    return response.data;
  },

  // Fund Distributions
  async getFundDistributions(params?: {
    fund_allocation_id?: string;
    recipient_id?: string;
    status?: string;
  }): Promise<FundDistribution[]> {
    const response = await apiClient.get('/fund-distributions', { params });
    return response.data;
  },

  async createFundDistribution(data: Omit<FundDistribution, 'id' | 'created_at'>): Promise<FundDistribution> {
    const response = await apiClient.post('/fund-distributions', data);
    return response.data;
  },

  async updateFundDistribution(id: string, data: Partial<FundDistribution>): Promise<FundDistribution> {
    const response = await apiClient.patch(`/fund-distributions/${id}`, data);
    return response.data;
  },

  // Mentor Payments
  async getMentorPayments(params?: {
    mentor_id?: string;
    project_id?: string;
    status?: string;
  }): Promise<MentorPayment[]> {
    const response = await apiClient.get('/mentor-payments', { params });
    return response.data;
  },

  async createMentorPayment(data: Omit<MentorPayment, 'id' | 'created_at'>): Promise<MentorPayment> {
    const response = await apiClient.post('/mentor-payments', data);
    return response.data;
  },

  async updateMentorPayment(id: string, data: Partial<MentorPayment>): Promise<MentorPayment> {
    const response = await apiClient.patch(`/mentor-payments/${id}`, data);
    return response.data;
  },

  // Lab Fund Advances
  async getLabFundAdvances(params?: {
    project_id?: string;
    status?: string;
  }): Promise<LabFundAdvance[]> {
    const response = await apiClient.get('/lab-fund-advances', { params });
    return response.data;
  },

  async createLabFundAdvance(data: Omit<LabFundAdvance, 'id' | 'created_at'>): Promise<LabFundAdvance> {
    const response = await apiClient.post('/lab-fund-advances', data);
    return response.data;
  },

  async updateLabFundAdvance(id: string, data: Partial<LabFundAdvance>): Promise<LabFundAdvance> {
    const response = await apiClient.patch(`/lab-fund-advances/${id}`, data);
    return response.data;
  }
};
