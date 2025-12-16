import { UserRole } from '@/utils/constants';

export interface UserProfile {
  id: string;
  email: string;
  fullName: string;
  phone: string;
  role: UserRole;
  avatar?: string;
  bio?: string;
  skills?: string[];
  certifications?: string[];
  portfolio?: PortfolioItem[];
  createdAt: string;
  updatedAt: string;
}

export interface PortfolioItem {
  id: string;
  title: string;
  description: string;
  imageUrl: string;
  projectUrl?: string;
  technologies: string[];
  createdAt: string;
}

export interface UpdateProfileRequest {
  fullName?: string;
  phone?: string;
  bio?: string;
  skills?: string[];
  certifications?: string[];
}

export interface TalentProfile extends UserProfile {
  experience?: string;
  education?: string;
  githubUrl?: string;
  linkedinUrl?: string;
  resume?: string;
}

export interface CompanyProfile extends UserProfile {
  companyName: string;
  companySize: string;
  industry: string;
  website?: string;
  address?: string;
  taxCode?: string;
}