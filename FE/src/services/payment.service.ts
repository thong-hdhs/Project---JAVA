import apiClient from './apiClient';
import type {
  Payment, PaymentCreateRequest, FundAllocation, FundDistribution,
  MentorPayment, LabFundAdvance
} from '../types';

type BackendApiResponse<T> = {
  success: boolean;
  message?: string;
  data?: T;
  errors?: string[];
  timestamp?: string;
  statusCode?: number;
};

export type BackendPaymentCreateRequest = {
  projectId?: string;
  companyId?: string;
  amount: number | string;
  paymentType?: string;
  dueDate?: string;
  notes?: string;
  usePayOS?: boolean;
};

export type BackendPaymentResponse = {
  id: string;
  projectName?: string;
  projectCode?: string;
  companyName?: string;
  amount?: number | string;
  paymentType?: string;
  status?: string;
  transactionId?: string;
  paymentDate?: string;
  dueDate?: string;
  notes?: string;
  createdAt?: string;
  updatedAt?: string;
};

const getRuntimeBaseUrl = (): string => {
  const base = (apiClient.defaults as any)?.baseURL;
  return typeof base === 'string' ? base : '';
};

export const getQrPathFromNotes = (notes?: string): string | null => {
  const raw = String(notes || '');
  // Notes format from backend: "QR generated: /qr/<file>.png"
  const match = raw.match(/\/qr\/[\w\-./]+/);
  return match?.[0] || null;
};

export const getQrDisplayUrlFromPayment = (payment?: BackendPaymentResponse): string | null => {
  const qrPath = getQrPathFromNotes(payment?.notes);
  if (!qrPath) return null;
  const base = getRuntimeBaseUrl();
  if (!base) return qrPath;
  return `${base.replace(/\/$/, '')}${qrPath.startsWith('/') ? '' : '/'}${qrPath.replace(/^\//, '')}`;
};

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
  },

  // ====== BE Payments (Spring) ======
  async createPaymentForProject(data: BackendPaymentCreateRequest): Promise<BackendPaymentResponse> {
    const response = await apiClient.post<BackendApiResponse<BackendPaymentResponse>>('/api/v1/payments/', {
      ...data,
      // backend expects BigDecimal; string is safest
      amount: typeof data.amount === 'number' ? String(data.amount) : data.amount,
      usePayOS: data.usePayOS ?? true,
    });

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Create payment failed';
      throw new Error(msg);
    }

    if (!response.data?.data) {
      throw new Error('Create payment failed: empty response');
    }

    return response.data.data;
  },

  async getPaymentQrUrl(paymentId: string): Promise<string> {
    const response = await apiClient.get<BackendApiResponse<string>>(`/api/v1/payments/${paymentId}/qr`);

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Get QR failed';
      throw new Error(msg);
    }

    if (!response.data?.data) {
      throw new Error('Get QR failed: empty response');
    }

    return response.data.data;
  },

  async listPaymentsByCompany(companyId: string): Promise<BackendPaymentResponse[]> {
    const response = await apiClient.get<BackendApiResponse<BackendPaymentResponse[]>>(
      `/api/v1/payments/company/${companyId}`,
    );

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Load payments failed';
      throw new Error(msg);
    }

    return response.data?.data || [];
  },

  async listPaymentsByProject(projectId: string): Promise<BackendPaymentResponse[]> {
    const response = await apiClient.get<BackendApiResponse<BackendPaymentResponse[]>>(
      `/api/v1/payments/project/${projectId}`,
    );

    if (!response.data?.success) {
      const msg = response.data?.message || response.data?.errors?.[0] || 'Load payments failed';
      throw new Error(msg);
    }

    return response.data?.data || [];
  },
};
