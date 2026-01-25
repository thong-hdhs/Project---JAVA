import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import Card from '@/components/ui/Card';
import MetricCard from '@/components/ui/MetricCard';
import Button from '@/components/ui/Button';
import StatusBadge from '@/components/ui/StatusBadge';
import { Link } from 'react-router-dom';
import { projectService } from '@/services/project.service';
import { taskService } from '@/services/task.service';
import { Project, Task } from '@/types';
const CandidateDashboard: React.FC = () => {
  const { user } = useSelector((state: any) => state.auth);
  const [stats, setStats] = useState({
    activeProjects: 0,
    pendingApplications: 0,
    completedTasks: 0,
    availableProjects: 0,
  });
  const [recentProjects, setRecentProjects] = useState<Project[]>([]);
  const [myTasks, setMyTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      setLoading(true);

      // Load user's projects (where they are team members)
      const projectsResponse = await projectService.getProjects();
      const userProjects = projectsResponse.data.filter(project =>
        project.status === 'IN_PROGRESS' || project.status === 'COMPLETED'
      );

      // Load available projects for browsing
      const availableProjects = projectsResponse.data.filter(project =>
        project.status === 'APPROVED'
      );

      // Load user's tasks
      const tasksResponse = await taskService.getTasks({
        assigned_to: user.id
      });

      // Calculate stats
      const activeProjects = userProjects.filter(p => p.status === 'IN_PROGRESS').length;
      const pendingApplications = 0; // Would need to load applications
      const completedTasks = tasksResponse.filter(task => task.status === 'COMPLETED').length;

      setStats({
        activeProjects,
        pendingApplications,
        completedTasks,
        availableProjects: availableProjects.length,
      });

      setRecentProjects(userProjects.slice(0, 3));
      setMyTasks(tasksResponse.slice(0, 5));

    } catch (error) {
      console.error('Error loading dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {Array.from({ length: 4 }).map((_, i) => (
            <Card key={i} className="animate-pulse">
              <div className="h-20 bg-gray-200 rounded"></div>
            </Card>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Welcome Section */}
      <div className="bg-white rounded-lg border border-gray-200 p-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">
              Welcome back, {user?.full_name}!
            </h1>
            <p className="text-gray-600 mt-1">
              Here's an overview of your projects and tasks
            </p>
          </div>
          <div className="flex space-x-3">
            <Link to="/candidate/browse-projects">
              <Button text="Browse Projects" className="bg-primary-500 text-white" />
            </Link>
            <Link to="/candidate/profile">
              <Button text="Update Profile" className="bg-white border border-gray-300 text-gray-700" />
            </Link>
          </div>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <MetricCard
          title="Active Projects"
          value={stats.activeProjects.toString()}
          icon={<span className="text-primary-600">📁</span>}
        />

        <MetricCard
          title="Available Projects"
          value={stats.availableProjects.toString()}
          icon={<span className="text-green-600">👥</span>}
        />

        <MetricCard
          title="My Tasks"
          value={myTasks.length.toString()}
          icon={<span className="text-yellow-600">⏰</span>}
        />

        <MetricCard
          title="Completed Tasks"
          value={stats.completedTasks.toString()}
          icon={<span className="text-blue-600">✅</span>}
        />
      </div>

      {/* Recent Projects & Tasks */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* My Projects */}
        <Card title="My Projects" headerslot={
          <Link to="/candidate/my-projects">
            <Button text="View All" className="btn-outline-dark btn-sm" />
          </Link>
        }>
          <div className="space-y-4">
            {recentProjects.length === 0 ? (
              <div className="text-center py-8 text-gray-500">
                No active projects yet. Browse available projects to get started!
              </div>
            ) : (
              recentProjects.map((project) => (
                <div key={project.id} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div>
                    <h4 className="font-medium text-gray-900">{project.project_name}</h4>
                    <p className="text-sm text-gray-600 line-clamp-1">{project.description}</p>
                    <div className="flex items-center mt-2">
                      <StatusBadge status={project.status} />
                    </div>
                  </div>
                  <Link to={`/candidate/project/${project.id}`}>
                    <Button text="View" className="btn-outline-dark btn-sm" />
                  </Link>
                </div>
              ))
            )}
          </div>
        </Card>

        {/* Recent Tasks */}
        <Card title="My Tasks" headerslot={
          <Link to="/candidate/tasks">
            <Button text="View All" className="btn-outline-dark btn-sm" />
          </Link>
        }>
          <div className="space-y-4">
            {myTasks.length === 0 ? (
              <div className="text-center py-8 text-gray-500">
                No tasks assigned yet.
              </div>
            ) : (
              myTasks.map((task) => (
                <div key={task.id} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div>
                    <h4 className="font-medium text-gray-900">{task.title}</h4>
                    <p className="text-sm text-gray-600 line-clamp-1">{task.description}</p>
                    <div className="flex items-center mt-2 space-x-2">
                      <StatusBadge status={task.status} />
                      <span className="text-xs text-gray-500">Priority: {task.priority}</span>
                    </div>
                  </div>
                  <Link to={`/candidate/task/${task.id}`}>
                    <Button text="View" className="btn-outline-dark btn-sm" />
                  </Link>
                </div>
              ))
            )}
          </div>
        </Card>
      </div>

      {/* Quick Actions */}
      <Card title="Quick Actions">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          <Link to="/candidate/company-verification" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-purple-600 mx-auto mb-2 block">🏢</span>
            <span className="text-sm font-medium text-gray-900">Verify Company</span>
          </Link>

          <Link to="/candidate/browse-projects" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-primary-600 mx-auto mb-2 block">🔍</span>
            <span className="text-sm font-medium text-gray-900">Browse Projects</span>
          </Link>

          <Link to="/candidate/applications" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-green-600 mx-auto mb-2 block">📄</span>
            <span className="text-sm font-medium text-gray-900">My Applications</span>
          </Link>

          <Link to="/candidate/fund-distributions" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-blue-600 mx-auto mb-2 block">💰</span>
            <span className="text-sm font-medium text-gray-900">Fund Distribution</span>
          </Link>

          <Link to="/candidate/profile" className="text-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
            <span className="text-2xl text-yellow-600 mx-auto mb-2 block">👤</span>
            <span className="text-sm font-medium text-gray-900">Update Profile</span>
          </Link>
        </div>
      </Card>
    </div>
  );
};

export default CandidateDashboard;
