import React, { useEffect, useState } from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

const ChangeRequestApprovals: React.FC = () => {
  const [requests, setRequests] = useState<any[]>([]);

  useEffect(() => {
    // mock data
    setRequests([
      {
        id: 1,
        project: "Project Alpha",
        company: "Acme",
        type: "Scope Change",
        status: "Pending",
        submitted: "2025-12-01",
      },
      {
        id: 2,
        project: "Project Beta",
        company: "Beta Ltd",
        type: "Budget Increase",
        status: "Approved",
        submitted: "2026-01-05",
      },
    ]);
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Change Requests</h1>
        <Button text="Create Request" className="bg-primary-500 text-white" />
      </div>

      <Card title="Pending Requests">
        <div className="overflow-x-auto">
          <table className="min-w-full text-sm">
            <thead>
              <tr className="text-left text-gray-600">
                <th className="py-2">#</th>
                <th className="py-2">Project</th>
                <th className="py-2">Company</th>
                <th className="py-2">Type</th>
                <th className="py-2">Status</th>
                <th className="py-2">Submitted</th>
              </tr>
            </thead>
            <tbody>
              {requests.map((r) => (
                <tr key={r.id} className="border-t">
                  <td className="py-3">{r.id}</td>
                  <td className="py-3">{r.project}</td>
                  <td className="py-3">{r.company}</td>
                  <td className="py-3">{r.type}</td>
                  <td className="py-3">{r.status}</td>
                  <td className="py-3">{r.submitted}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};

export default ChangeRequestApprovals;
