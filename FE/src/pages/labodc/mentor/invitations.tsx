import React, { useEffect, useState } from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

const MentorInvitations: React.FC = () => {
  const [invitations, setInvitations] = useState<any[]>([]);

  useEffect(() => {
    setInvitations([
      { id: 1, project: "Project Alpha", from: "Acme", date: "2025-12-01" },
      {
        id: 2,
        project: "Project Delta",
        from: "Delta Inc",
        date: "2026-01-08",
      },
    ]);
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">
          Project Invitations
        </h1>
      </div>

      <Card>
        <ul className="space-y-3">
          {invitations.map((inv) => (
            <li
              key={inv.id}
              className="flex items-center justify-between border p-3 rounded"
            >
              <div>
                <div className="font-medium">{inv.project}</div>
                <div className="text-sm text-gray-500">
                  From: {inv.from} • {inv.date}
                </div>
              </div>
              <div className="flex space-x-2">
                <Button text="Accept" className="bg-primary-500 text-white" />
                <Button text="Decline" className="btn-outline-dark" />
              </div>
            </li>
          ))}
        </ul>
      </Card>
    </div>
  );
};

export default MentorInvitations;
