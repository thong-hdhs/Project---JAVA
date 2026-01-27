import React, { useCallback, useEffect, useMemo, useState } from 'react';
import Card from '@/components/ui/Card';
import { toast } from 'react-toastify';
import { mentorService, type BackendMentorResponse } from '@/services/mentor.service';

const MentorsManagement: React.FC = () => {
  const [items, setItems] = useState<BackendMentorResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [statusFilter, setStatusFilter] = useState<string>('ALL');

  const load = useCallback(async () => {
    try {
      setLoading(true);
      const list = statusFilter === 'ALL'
        ? await mentorService.listAllMentors()
        : await mentorService.listMentorsByStatus(statusFilter);
      setItems(list || []);
    } catch (e: any) {
      toast.error(e?.message || 'Không thể tải danh sách mentor');
      setItems([]);
    } finally {
      setLoading(false);
    }
  }, [statusFilter]);

  useEffect(() => {
    void load();
  }, [load]);

  const total = useMemo(() => items.length, [items]);

  return (
    <div className="space-y-6">
      <div className="bg-white rounded-lg border border-gray-200 p-6">
        <div className="flex items-center justify-between gap-4">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">Mentors</h1>
            <p className="text-gray-600 mt-1">Lab Admin mentor directory</p>
          </div>

          <div className="flex items-center gap-3">
            <select
              className="border border-gray-300 rounded-lg px-3 py-2 text-sm"
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
            >
              <option value="ALL">All</option>
              <option value="ACTIVE">ACTIVE</option>
              <option value="INACTIVE">INACTIVE</option>
              <option value="SUSPENDED">SUSPENDED</option>
            </select>
            <div className="text-sm text-gray-600">
              Total: <span className="font-semibold text-gray-900">{total}</span>
            </div>
          </div>
        </div>
      </div>

      <Card title="Mentor List">
        {loading ? (
          <div className="text-gray-500">Loading...</div>
        ) : items.length === 0 ? (
          <div className="text-gray-500">No mentors found.</div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full text-sm">
              <thead>
                <tr className="text-left text-gray-600">
                  <th className="py-2">Mentor ID</th>
                  <th className="py-2">User ID</th>
                  <th className="py-2">Expertise</th>
                  <th className="py-2">Experience</th>
                  <th className="py-2">Rating</th>
                  <th className="py-2">Projects</th>
                  <th className="py-2">Status</th>
                </tr>
              </thead>
              <tbody>
                {items.map((m) => (
                  <tr key={m.id} className="border-t">
                    <td className="py-3 font-medium text-gray-900">{m.id}</td>
                    <td className="py-3">{m.userId || '-'}</td>
                    <td className="py-3">{m.expertise || '-'}</td>
                    <td className="py-3">{m.yearsExperience ?? '-'}</td>
                    <td className="py-3">{m.rating ?? '-'}</td>
                    <td className="py-3">{m.totalProjects ?? '-'}</td>
                    <td className="py-3">{m.status || '-'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </Card>
    </div>
  );
};

export default MentorsManagement;
