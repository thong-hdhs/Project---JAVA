import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';

import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';
import StatusBadge from '@/components/ui/StatusBadge';

import { companyService } from '@/services/company.service';
import { projectService } from '@/services/project.service';
import { reportService, type BackendReportResponse, type ReportType } from '@/services/report.service';
import type { Project } from '@/types';

const REPORT_TYPES: ReportType[] = ['WEEKLY', 'MONTHLY', 'PHASE', 'FINAL', 'INCIDENT'];

const EnterpriseReports: React.FC = () => {
  const { user } = useSelector((state: any) => state.auth);
  const [tab, setTab] = useState<'view' | 'send'>('view');

  const [projects, setProjects] = useState<Project[]>([]);
  const [selectedProjectId, setSelectedProjectId] = useState<string>('');

  const [reports, setReports] = useState<BackendReportResponse[]>([]);
  const [loading, setLoading] = useState(false);

  const selectedProject = useMemo(
    () => projects.find((p) => p.id === selectedProjectId),
    [projects, selectedProjectId],
  );

  const [form, setForm] = useState({
    reportType: 'MONTHLY' as ReportType,
    title: '',
    content: '',
    reportPeriodStart: '',
    reportPeriodEnd: '',
    attachmentUrl: '',
  });
  const [pickedFileName, setPickedFileName] = useState<string>('');

  const loadMyProjects = async () => {
    try {
      setLoading(true);
      const myCompany = await companyService.getMyCompany();
      const all = await projectService.listAllProjectsFromBackend();
      const mine = all.filter((p) => String(p.company_id) === String(myCompany.id));
      setProjects(mine);
      if (!selectedProjectId && mine.length) setSelectedProjectId(mine[0].id);
    } catch (e: any) {
      toast.error(e?.message || 'Failed to load projects');
    } finally {
      setLoading(false);
    }
  };

  const loadReports = async (projectId: string) => {
    try {
      setLoading(true);
      const list = await reportService.getReportsByProject(projectId);
      setReports(list);
    } catch (e: any) {
      toast.error(e?.message || 'Failed to load reports');
      setReports([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadMyProjects();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (selectedProjectId) {
      loadReports(selectedProjectId);
    }
  }, [selectedProjectId]);

  const onCreateDraft = async () => {
    if (!selectedProject) {
      toast.error('Please select a project');
      return;
    }

    if (!selectedProject.mentor_id) {
      toast.error('This project has no mentor assigned yet');
      return;
    }

    if (!form.title.trim()) {
      toast.error('Please enter a title');
      return;
    }

    try {
      setLoading(true);
      await reportService.createReport(String(selectedProject.mentor_id), {
        projectId: selectedProject.id,
        reportType: form.reportType,
        title: form.title.trim(),
        content: form.content?.trim() || undefined,
        reportPeriodStart: form.reportPeriodStart || undefined,
        reportPeriodEnd: form.reportPeriodEnd || undefined,
        attachmentUrl: form.attachmentUrl?.trim() || undefined,
      });
      toast.success('Report created (DRAFT)');
      setTab('view');
      await loadReports(selectedProject.id);
    } catch (e: any) {
      toast.error(e?.message || 'Create report failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-start justify-between gap-3">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Reports</h1>
          <p className="mt-1 text-sm text-gray-600">View reports and send a report for your projects.</p>
        </div>
        <div className="text-sm text-gray-500">Logged in as: {user?.role}</div>
      </div>

      <div className="flex gap-2">
        <Button text="View reports" className={tab === 'view' ? '' : 'btn-outline'} onClick={() => setTab('view')} />
        <Button text="Send report" className={tab === 'send' ? '' : 'btn-outline'} onClick={() => setTab('send')} />
      </div>

      <Card title="Project">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Select project</label>
            <select
              className="form-control w-full"
              value={selectedProjectId}
              onChange={(e) => setSelectedProjectId(e.target.value)}
            >
              {projects.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.project_name}
                </option>
              ))}
            </select>
          </div>
          <div className="md:col-span-2 text-sm text-gray-600">
            <div>Mentor ID: {selectedProject?.mentor_id || '—'}</div>
            <div>Status: <StatusBadge status={String(selectedProject?.status || '—')} /></div>
          </div>
        </div>
      </Card>

      {tab === 'view' ? (
        <Card title="Reports">
          {loading ? (
            <div className="text-sm text-gray-600">Loading…</div>
          ) : reports.length === 0 ? (
            <div className="text-sm text-gray-600">No reports found for this project.</div>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full text-sm">
                <thead>
                  <tr className="text-left text-gray-600">
                    <th className="py-2 pr-3">Title</th>
                    <th className="py-2 pr-3">Type</th>
                    <th className="py-2 pr-3">Status</th>
                    <th className="py-2 pr-3">Created</th>
                    <th className="py-2 pr-3">Submitted</th>
                    <th className="py-2 pr-3">Attachment</th>
                  </tr>
                </thead>
                <tbody>
                  {reports.map((r) => (
                    <tr key={r.id} className="border-t">
                      <td className="py-2 pr-3 font-medium text-gray-900">{r.title}</td>
                      <td className="py-2 pr-3">{String(r.reportType || '')}</td>
                      <td className="py-2 pr-3"><StatusBadge status={String(r.status || '')} /></td>
                      <td className="py-2 pr-3">{r.createdAt ? String(r.createdAt).slice(0, 10) : '—'}</td>
                      <td className="py-2 pr-3">{r.submittedDate || '—'}</td>
                      <td className="py-2 pr-3">
                        {r.attachmentUrl ? (
                          <a className="text-blue-600 hover:underline" href={r.attachmentUrl} target="_blank" rel="noreferrer">Link</a>
                        ) : (
                          '—'
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </Card>
      ) : (
        <Card title="Send report">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Report type</label>
              <select
                className="form-control w-full"
                value={form.reportType}
                onChange={(e) => setForm((s) => ({ ...s, reportType: e.target.value as ReportType }))}
              >
                {REPORT_TYPES.map((t) => (
                  <option key={t} value={t}>{t}</option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
              <input
                className="form-control w-full"
                value={form.title}
                onChange={(e) => setForm((s) => ({ ...s, title: e.target.value }))}
              />
            </div>

            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-gray-700 mb-1">Content</label>
              <textarea
                className="form-control w-full"
                rows={5}
                value={form.content}
                onChange={(e) => setForm((s) => ({ ...s, content: e.target.value }))}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Period start (YYYY-MM-DD)</label>
              <input
                className="form-control w-full"
                value={form.reportPeriodStart}
                onChange={(e) => setForm((s) => ({ ...s, reportPeriodStart: e.target.value }))}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Period end (YYYY-MM-DD)</label>
              <input
                className="form-control w-full"
                value={form.reportPeriodEnd}
                onChange={(e) => setForm((s) => ({ ...s, reportPeriodEnd: e.target.value }))}
              />
            </div>

            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-gray-700 mb-1">Attachment</label>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                <div>
                  <input
                    type="file"
                    className="form-control w-full"
                    onChange={(e) => setPickedFileName(e.target.files?.[0]?.name || '')}
                  />
                  <div className="mt-1 text-xs text-gray-500">Selected: {pickedFileName || '—'} (upload not supported by BE; use URL)</div>
                </div>
                <div>
                  <input
                    className="form-control w-full"
                    value={form.attachmentUrl}
                    onChange={(e) => setForm((s) => ({ ...s, attachmentUrl: e.target.value }))}
                    placeholder="https://..."
                  />
                </div>
              </div>
            </div>

            <div className="md:col-span-2 flex items-center gap-2">
              <Button text={loading ? 'Sending…' : 'Create (DRAFT)'} onClick={onCreateDraft} disabled={loading || !selectedProject?.mentor_id} />
            </div>
          </div>
        </Card>
      )}
    </div>
  );
};

export default EnterpriseReports;
