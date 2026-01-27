import React from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

const MentorProjectWorkspace: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Project Workspace</h1>
        <div className="flex space-x-2">
          <Button text="Add Task" className="bg-primary-500 text-white" />
          <Button text="Export" className="btn-outline-dark" />
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <Card title="Overview">
          <p className="text-sm text-gray-600">
            Summary, milestones, and quick links.
          </p>
        </Card>
        <Card title="Tasks" className="lg:col-span-2">
          <div className="space-y-3">
            <div className="flex items-center justify-between border p-3 rounded">
              <div>
                <div className="font-medium">Design landing page</div>
                <div className="text-sm text-gray-500">
                  Assigned to: Alice • Due: 2026-02-01
                </div>
              </div>
              <div className="text-sm">In Progress</div>
            </div>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default MentorProjectWorkspace;
