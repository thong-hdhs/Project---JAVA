import { ProjectStatus, UserRole } from '@/utils/constants';

export interface Project {
  id: string;
  title: string;
  description: string;
  requirements: string[];
  technologies: string[];
  budget: number;
  duration: number; // tính theo tháng
  status: ProjectStatus;
  companyId: string;
  companyName: string;
  mentorId?: string;
  mentorName?: string;
  teamSize: number;
  currentTeamSize: number;
  startDate?: string;
  endDate?: string;
  createdAt: string;
  updatedAt: string;
}

export interface ProjectDetail extends Project {
  company: {
    id: string;
    name: string;
    avatar?: string;
    industry: string;
  };
  mentor?: {
    id: string;
    name: string;
    avatar?: string;
    expertise: string[];
  };
  team?: TeamMember[];
  tasks?: Task[];
  reports?: Report[];
}

export interface TeamMember {
  id: string;
  userId: string;
  fullName: string;
  avatar?: string;
  role: UserRole;
  isLeader: boolean;
  joinedAt: string;
}

export interface Task {
  id: string;
  title: string;
  description: string;
  assignedTo?: string;
  status: 'TODO' | 'IN_PROGRESS' | 'REVIEW' | 'DONE';
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  dueDate?: string;
  createdAt: string;
}

export interface Report {
  id: string;
  title: string;
  content: string;
  submittedBy: string;
  submittedAt: string;
  attachments?: string[];
}

export interface ApplyProjectRequest {
  projectId: string;
  coverLetter: string;
  resume?: File;
}

export interface CreateProjectRequest {
  title: string;
  description: string;
  requirements: string[];
  technologies: string[];
  budget: number;
  duration: number;
  teamSize: number;
}