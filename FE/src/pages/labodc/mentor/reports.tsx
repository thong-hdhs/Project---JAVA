import React from "react";
import Card from "@/components/ui/Card";

type ReportProgress = {
  completedTasks: number;
  totalTasks: number;
  completionPercent: number;
};

type MentorReport = {
  id: number;
  title: string;
  date: string;
  projectName: string;
  projectDescription: string;
  progress: ReportProgress;
};

function slugifyFilenamePart(value: string): string {
  return value
    .trim()
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, "-")
    .replace(/^-+|-+$/g, "")
    .slice(0, 80);
}

function toCsv(
  rows: Array<Record<string, string | number | boolean | null | undefined>>
): string {
  if (rows.length === 0) return "";
  const headers = Object.keys(rows[0]);
  const escape = (value: unknown) => {
    const str = value === null || value === undefined ? "" : String(value);
    return `"${str.replace(/"/g, '""')}"`;
  };
  const lines = [headers.map(escape).join(",")];
  for (const row of rows) {
    lines.push(headers.map((h) => escape(row[h])).join(","));
  }
  return lines.join("\n");
}

function downloadTextFile(
  filename: string,
  content: string,
  mimeType: string
): void {
  const blob = new Blob([content], { type: mimeType });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = filename;
  document.body.appendChild(a);
  a.click();
  a.remove();
  URL.revokeObjectURL(url);
}

const MentorReports: React.FC = () => {
  const reports: MentorReport[] = [
    {
      id: 1,
      title: "Weekly Progress - Project Alpha",
      date: "2026-01-05",
      projectName: "Project Alpha",
      projectDescription:
        "Weekly progress summary: milestones, completed tasks, and next week plan.",
      progress: { completedTasks: 12, totalTasks: 20, completionPercent: 60 },
    },
    {
      id: 2,
      title: "Final Report - Project Beta",
      date: "2025-12-20",
      projectName: "Project Beta",
      projectDescription:
        "Final project wrap-up: outcomes, deliverables, and retrospective notes.",
      progress: { completedTasks: 48, totalTasks: 50, completionPercent: 96 },
    },
  ];

  const exportProgressCsv = (report: MentorReport) => {
    const csv = toCsv([
      {
        projectName: report.projectName,
        reportTitle: report.title,
        reportDate: report.date,
        completedTasks: report.progress.completedTasks,
        totalTasks: report.progress.totalTasks,
        completionPercent: report.progress.completionPercent,
      },
    ]);
    const filename = `${slugifyFilenamePart(report.projectName)}-progress-${report.date}.csv`;
    downloadTextFile(filename, csv, "text/csv;charset=utf-8;");
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Project Reports</h1>
        <p className="mt-1 text-sm text-gray-600">
          View reports, see a short project description, and export progress data.
        </p>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {reports.map((r) => (
          <Card key={r.id} title={r.title} subtitle={r.date}>
            <div className="space-y-3">
              <div>
                <p className="text-sm font-medium text-gray-900">
                  {r.projectName}
                </p>
                <p className="text-sm text-gray-600">{r.projectDescription}</p>
              </div>

              <div className="text-sm text-gray-600">
                Progress: {r.progress.completedTasks}/{r.progress.totalTasks} tasks
                ({r.progress.completionPercent}%)
              </div>

              <div className="flex items-center gap-2">
                <button
                  type="button"
                  className="btn btn-outline-primary btn-sm"
                  onClick={() => exportProgressCsv(r)}
                >
                  Export progress
                </button>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default MentorReports;
