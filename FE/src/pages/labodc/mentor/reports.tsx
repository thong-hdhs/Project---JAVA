import React from "react";
import Card from "@/components/ui/Card";

const MentorReports: React.FC = () => {
  const reports = [
    { id: 1, title: "Weekly Progress - Project Alpha", date: "2026-01-05" },
    { id: 2, title: "Final Report - Project Beta", date: "2025-12-20" },
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">Project Reports</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {reports.map((r) => (
          <Card key={r.id} title={r.title} subtitle={r.date}>
            <p className="text-sm text-gray-600">
              Download or preview the report.
            </p>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default MentorReports;
