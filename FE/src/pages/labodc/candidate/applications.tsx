import React, { useState, useEffect } from 'react';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';
import StatusBadge from '@/components/ui/StatusBadge';
import DataTable from '@/components/ui/DataTable';

const CandidateApplications: React.FC = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadApplications();
  }, []);

  const loadApplications = async () => {
    try {
      setLoading(true);
      // Mock data - in real app this would come from API
      setApplications([
        {
          id: '1',
          project_name: 'E-commerce Platform',
          company: 'TechCorp Inc.',
          status: 'PENDING',
          applied_at: new Date().toLocaleDateString(),
          cover_letter: 'I am very interested in this project...'
        },
        {
          id: '2',
          project_name: 'Mobile App Development',
          company: 'StartupXYZ',
          status: 'APPROVED',
          applied_at: new Date().toLocaleDateString(),
          cover_letter: 'I have experience in mobile development...'
        }
      ]);
    } catch (error) {
      console.error('Error loading applications:', error);
    } finally {
      setLoading(false);
    }
  };

  const columns = [
    {
      key: 'project_name',
      header: 'Project',
      render: (value: string, item: any) => (
        <div>
          <div className="font-medium text-gray-900">{value}</div>
          <div className="text-sm text-gray-500">{item.company}</div>
        </div>
      ),
    },
    {
      key: 'status',
      header: 'Status',
      render: (value: string) => <StatusBadge status={value} />,
    },
    {
      key: 'applied_at',
      header: 'Applied Date',
    },
    {
      key: 'actions',
      header: 'Actions',
      render: (value: any, item: any) => (
        <div className="flex space-x-2">
          <Button text="View Details" className="btn-outline-dark btn-sm" />
          {item.status === 'PENDING' && (
            <Button text="Withdraw" className="btn-outline-danger btn-sm" />
          )}
        </div>
      ),
    },
  ];

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">My Applications</h1>
          <p className="text-gray-600 mt-1">Track your project applications</p>
        </div>
      </div>

      {/* Applications Table */}
      <Card>
        <DataTable
          data={applications}
          columns={columns}
          loading={loading}
          emptyMessage="You haven't applied for any projects yet."
        />
      </Card>

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card className="text-center">
          <div className="text-2xl font-bold text-gray-600">{applications.length}</div>
          <div className="text-gray-600">Total Applications</div>
        </Card>

        <Card className="text-center">
          <div className="text-2xl font-bold text-yellow-600">
            {applications.filter((app: any) => app.status === 'PENDING').length}
          </div>
          <div className="text-gray-600">Pending Review</div>
        </Card>

        <Card className="text-center">
          <div className="text-2xl font-bold text-green-600">
            {applications.filter((app: any) => app.status === 'APPROVED').length}
          </div>
          <div className="text-gray-600">Approved</div>
        </Card>
      </div>
    </div>
  );
};

export default CandidateApplications;
