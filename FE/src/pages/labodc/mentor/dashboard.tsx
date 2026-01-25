import React, { useState, useEffect } from "react";
import Card from "@/components/ui/Card";
import MetricCard from "@/components/ui/MetricCard";
import Button from "@/components/ui/Button";
import StatusBadge from "@/components/ui/StatusBadge";
import { Link } from "react-router-dom";
// Using emoji icons

const MentorDashboard: React.FC = () => {
  const [stats, setStats] = useState({
    activeProjects: 3,
    pendingInvitations: 2,
    pendingReviews: 5,
    totalEarnings: 15000,
  });

  return (
    <div className="space-y-6">
      {/* Welcome Section */}
      <div className="bg-white rounded-lg border border-gray-200 p-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">
              Mentor Dashboard
            </h1>
            <p className="text-gray-600 mt-1">
              Guide projects and mentor talented students
            </p>
          </div>
          <div className="flex space-x-3">
            <Link to="/profile">
              <Button
                text="Update Profile"
                className="bg-white border border-gray-300 text-gray-700"
              />
            </Link>
          </div>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <MetricCard
          title="Active Projects"
          value={stats.activeProjects.toString()}
          icon={<span className="text-primary-600">👥</span>}
        />

        <MetricCard
          title="Pending Invitations"
          value={stats.pendingInvitations.toString()}
          icon={<span className="text-yellow-600">⏰</span>}
        />

        <MetricCard
          title="Pending Reviews"
          value={stats.pendingReviews.toString()}
          icon={<span className="text-orange-600">📄</span>}
        />

        <MetricCard
          title="Total Earnings"
          value={`$${stats.totalEarnings.toLocaleString()}`}
          icon={<span className="text-green-600">💰</span>}
        />
      </div>

      {/* Alerts */}
      {stats.pendingInvitations > 0 && (
        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
          <div className="flex">
            <div className="flex-shrink-0">
              <svg
                className="h-5 w-5 text-blue-400"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fillRule="evenodd"
                  d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
                  clipRule="evenodd"
                />
              </svg>
            </div>
            <div className="ml-3">
              <h3 className="text-sm font-medium text-blue-800">
                {stats.pendingInvitations} project invitation
                {stats.pendingInvitations > 1 ? "s" : ""} pending
              </h3>
              <div className="mt-2 text-sm text-blue-700">
                <p>You have new project invitations to review.</p>
              </div>
              <div className="mt-4">
                <Link to="/mentor/invitations">
                  <Button
                    text="Review Invitations"
                    className="bg-blue-600 text-white text-xs"
                  />
                </Link>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Recent Projects & Tasks */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* My Projects */}
        <Card
          title="My Projects"
          headerslot={
            <Button text="View All" className="btn-outline-dark btn-sm" />
          }
        >
          <div className="space-y-4">
            <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
              <div>
                <h4 className="font-medium text-gray-900">
                  E-commerce Platform
                </h4>
                <p className="text-sm text-gray-600">Mentoring 4 students</p>
                <div className="flex items-center mt-2">
                  <StatusBadge status="IN_PROGRESS" />
                </div>
              </div>
              <Link to="/mentor/project/1">
                <Button text="View" className="btn-outline-dark btn-sm" />
              </Link>
            </div>

            <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
              <div>
                <h4 className="font-medium text-gray-900">
                  Mobile App Development
                </h4>
                <p className="text-sm text-gray-600">Mentoring 3 students</p>
                <div className="flex items-center mt-2">
                  <StatusBadge status="IN_PROGRESS" />
                </div>
              </div>
              <Link to="/mentor/project/2">
                <Button text="View" className="btn-outline-dark btn-sm" />
              </Link>
            </div>
          </div>
        </Card>

        {/* Pending Reviews */}
        <Card
          title="Pending Reviews"
          headerslot={
            <Link to="/mentor/candidate-reviews">
              <Button text="View All" className="btn-outline-dark btn-sm" />
            </Link>
          }
        >
          <div className="space-y-4">
            <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
              <div>
                <h4 className="font-medium text-gray-900">John Doe</h4>
                <p className="text-sm text-gray-600">E-commerce Platform</p>
                <div className="flex items-center mt-2">
                  <span className="text-xs text-yellow-600 font-medium">
                    Review needed
                  </span>
                </div>
              </div>
              <Button text="Review" className="btn-outline-dark btn-sm" />
            </div>

            <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
              <div>
                <h4 className="font-medium text-gray-900">Jane Smith</h4>
                <p className="text-sm text-gray-600">Mobile App Development</p>
                <div className="flex items-center mt-2">
                  <span className="text-xs text-yellow-600 font-medium">
                    Review needed
                  </span>
                </div>
              </div>
              <Button text="Review" className="btn-outline-dark btn-sm" />
            </div>
          </div>
        </Card>
      </div>

      {/* Quick Actions */}
      <Card title="Quick Actions">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          <Link
            to="/mentor/invitations"
            className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
          >
            <span className="text-2xl text-primary-600 mx-auto mb-2 block">
              📧
            </span>
            <span className="text-sm font-medium text-gray-900">
              Project Invitations
            </span>
          </Link>

          <Link
            to="/mentor/candidate-reviews"
            className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
          >
            <span className="text-2xl text-blue-600 mx-auto mb-2 block">
              📝
            </span>
            <span className="text-sm font-medium text-gray-900">
              Candidate Reviews
            </span>
          </Link>

          <Link
            to="/mentor/reports"
            className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
          >
            <span className="text-2xl text-green-600 mx-auto mb-2 block">
              📊
            </span>
            <span className="text-sm font-medium text-gray-900">
              Project Reports
            </span>
          </Link>

          <Link
            to="/mentor/fund-approvals"
            className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
          >
            <span className="text-2xl text-yellow-600 mx-auto mb-2 block">
              💰
            </span>
            <span className="text-sm font-medium text-gray-900">
              Fund Approvals
            </span>
          </Link>
        </div>
      </Card>
    </div>
  );
};

export default MentorDashboard;
