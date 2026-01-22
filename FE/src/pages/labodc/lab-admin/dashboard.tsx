import React, { useState } from 'react';
import Card from '@/components/ui/Card';
import MetricCard from '@/components/ui/MetricCard';
import Button from '@/components/ui/Button';
import { Link } from 'react-router-dom';
// Using emoji icons

const LabAdminDashboard: React.FC = () => {
  const [stats] = useState({
    pendingProjects: 5,
    totalPayments: 250000,
    activeProjects: 12,
  });

  return (
    <div className="space-y-6">
      {/* Welcome Section */}
      <div className="bg-white rounded-lg border border-gray-200 p-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">
              Lab Administration
            </h1>
            <p className="text-gray-600 mt-1">
              Manage companies, projects, and fund distributions
            </p>
          </div>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <MetricCard
          title="Pending Projects"
          value={stats.pendingProjects.toString()}
          icon={<span className="text-orange-600">📄</span>}
        />

        <MetricCard
          title="Active Projects"
          value={stats.activeProjects.toString()}
          icon={<span className="text-blue-600">📊</span>}
        />

        <MetricCard
          title="Total Payments"
          value={`$${(stats.totalPayments / 1000).toFixed(0)}k`}
          icon={<span className="text-green-600">💰</span>}
        />
      </div>

      {/* Alerts */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {stats.pendingProjects > 0 && (
          <Card className="border-l-4 border-yellow-400">
            <div className="flex">
              <div className="flex-shrink-0">
                <svg className="h-5 w-5 text-yellow-400" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
                </svg>
              </div>
              <div className="ml-3">
                <h3 className="text-sm font-medium text-gray-800">
                  Validation Required
                </h3>
                <div className="mt-2 text-sm text-gray-700">
                  <p>{stats.pendingProjects} projects awaiting validation.</p>
                </div>
                <div className="mt-4">
                  <div className="-mx-2 -my-1.5 flex">
                    <Link to="/lab-admin/validate-projects" className="ml-3">
                      <Button text="Review Projects" className="bg-yellow-600 text-white text-xs" />
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </Card>
        )}

        <Card className="border-l-4 border-purple-400">
          <div className="flex">
            <div className="flex-shrink-0">
              <span className="text-purple-500">🏢</span>
            </div>
            <div className="ml-3">
              <h3 className="text-sm font-medium text-gray-800">Company Verification</h3>
              <div className="mt-2 text-sm text-gray-700">
                <p>Review and approve company registration requests.</p>
              </div>
              <div className="mt-4">
                <Link to="/lab-admin/company-approvals">
                  <Button text="Review Companies" className="bg-purple-600 text-white text-xs" />
                </Link>
              </div>
            </div>
          </div>
        </Card>

        <Card className="border-l-4 border-blue-400">
          <div className="flex">
            <div className="flex-shrink-0">
              <span className="text-blue-400">💰</span>
            </div>
            <div className="ml-3">
              <h3 className="text-sm font-medium text-gray-800">
                Fund Distribution
              </h3>
              <div className="mt-2 text-sm text-gray-700">
                <p>Manage fund allocations and distributions for completed projects.</p>
              </div>
              <div className="mt-4">
                <Link to="/lab-admin/fund-allocations">
                  <Button text="Manage Funds" className="bg-blue-600 text-white text-xs" />
                </Link>
              </div>
            </div>
          </div>
        </Card>
      </div>

      {/* Quick Actions */}
      <Card title="Quick Actions">
        <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4">
          <Link to="/lab-admin/company-approvals" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-purple-600 mx-auto mb-2 block">🏢</span>
            <span className="text-sm font-medium text-gray-900">Company Approvals</span>
          </Link>

          <Link to="/lab-admin/validate-projects" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-blue-600 mx-auto mb-2 block">📄</span>
            <span className="text-sm font-medium text-gray-900">Validate Projects</span>
          </Link>

          <Link to="/lab-admin/payments-overview" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-green-600 mx-auto mb-2 block">💰</span>
            <span className="text-sm font-medium text-gray-900">Payments</span>
          </Link>

          <Link to="/lab-admin/fund-allocations" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-yellow-600 mx-auto mb-2 block">📊</span>
            <span className="text-sm font-medium text-gray-900">Fund Allocations</span>
          </Link>

          <Link to="/lab-admin/lab-fund-advances" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-red-600 mx-auto mb-2 block">💸</span>
            <span className="text-sm font-medium text-gray-900">Fund Advances</span>
          </Link>

          <Link to="/lab-admin/transparency-report" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-purple-600 mx-auto mb-2 block">👁️</span>
            <span className="text-sm font-medium text-gray-900">Transparency</span>
          </Link>
        </div>
      </Card>
    </div>
  );
};

export default LabAdminDashboard;
