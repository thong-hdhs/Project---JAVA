import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';
import StatusBadge from '@/components/ui/StatusBadge';
import DataTable from '@/components/ui/DataTable';
import { projectService } from '@/services/project.service';
import { Project } from '@/types';

const MyProjects: React.FC = () => {
  const [projects, setProjects] = useState<Project[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadMyProjects();
  }, []);

  const loadMyProjects = async () => {
    try {
      setLoading(true);
      // In a real app, you'd filter projects where the user is a team member
      const response = await projectService.getProjects();
      // Mock filtering - in real app this would be done on backend
      const myProjects = response.data.filter(project =>
        project.status === 'IN_PROGRESS' || project.status === 'COMPLETED'
      );
      setProjects(myProjects);
    } catch (error) {
      console.error('Error loading projects:', error);
    } finally {
      setLoading(false);
    }
  };

  const columns = [
    {
      key: 'project_name',
      header: 'Project Name',
      render: (value: string, item: Project) => (
        <div>
          <div className="font-medium text-gray-900">{value}</div>
          <div className="text-sm text-gray-500 line-clamp-1">{item.description}</div>
        </div>
      ),
    },
    {
      key: 'status',
      header: 'Status',
      render: (value: string) => <StatusBadge status={value} />,
    },
    {
      key: 'budget',
      header: 'Budget',
      render: (value: number) => `$${value.toLocaleString()}`,
    },
    {
      key: 'duration_months',
      header: 'Duration',
      render: (value: number) => `${value} months`,
    },
    {
      key: 'actions',
      header: 'Actions',
      render: (value: any, item: Project) => (
        <div className="flex space-x-2">
          <Link to={`/candidate/project/${item.id}`}>
            <Button text="View" className="btn-outline-dark btn-sm" />
          </Link>
        </div>
      ),
    },
  ];

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">My Projects</h1>
          <p className="text-gray-600 mt-1">Projects you're currently working on</p>
        </div>
        <Link to="/candidate/browse-projects">
          <Button text="Browse More Projects" className="bg-primary-500 text-white" />
        </Link>
      </div>

      {/* Projects Table */}
      <Card>
        <DataTable
          data={projects}
          columns={columns}
          loading={loading}
          emptyMessage="You haven't joined any projects yet."
        />
      </Card>

      {/* Project Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card className="text-center">
          <div className="text-2xl font-bold text-primary-600">{projects.length}</div>
          <div className="text-gray-600">Total Projects</div>
        </Card>

        <Card className="text-center">
          <div className="text-2xl font-bold text-green-600">
            {projects.filter(p => p.status === 'COMPLETED').length}
          </div>
          <div className="text-gray-600">Completed</div>
        </Card>

        <Card className="text-center">
          <div className="text-2xl font-bold text-blue-600">
            {projects.filter(p => p.status === 'IN_PROGRESS').length}
          </div>
          <div className="text-gray-600">In Progress</div>
        </Card>
      </div>
    </div>
  );
};

export default MyProjects;
