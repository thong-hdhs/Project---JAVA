import React, { useEffect, useState } from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import MetricCard from "@/components/ui/MetricCard";

const PaymentsOverview: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [summary, setSummary] = useState({ total: 0, pending: 0, approved: 0 });
  const [payments, setPayments] = useState<any[]>([]);

  useEffect(() => {
    // mock load
    setLoading(true);
    setTimeout(() => {
      setSummary({ total: 124500, pending: 8, approved: 116 });
      setPayments([
        {
          id: "P-1001",
          project: "Project Alpha",
          company: "Acme Co",
          amount: 12000,
          status: "Approved",
          date: "2025-12-01",
        },
        {
          id: "P-1002",
          project: "Project Beta",
          company: "Beta Ltd",
          amount: 5000,
          status: "Pending",
          date: "2026-01-02",
        },
      ]);
      setLoading(false);
    }, 400);
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Payments Overview</h1>
        <div className="flex space-x-2">
          <Button text="Export" className="btn-outline-dark" />
          <Button text="New Payment" className="bg-primary-500 text-white" />
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <MetricCard
          title="Total Payments"
          value={`$${summary.total.toLocaleString()}`}
        />
        <MetricCard title="Approved" value={summary.approved.toString()} />
        <MetricCard title="Pending" value={summary.pending.toString()} />
      </div>

      <Card title="Recent Payments">
        <div className="overflow-x-auto">
          <table className="min-w-full text-sm">
            <thead>
              <tr className="text-left text-gray-600">
                <th className="py-2">ID</th>
                <th className="py-2">Project</th>
                <th className="py-2">Company</th>
                <th className="py-2">Amount</th>
                <th className="py-2">Status</th>
                <th className="py-2">Date</th>
              </tr>
            </thead>
            <tbody>
              {payments.map((p) => (
                <tr key={p.id} className="border-t">
                  <td className="py-3">{p.id}</td>
                  <td className="py-3">{p.project}</td>
                  <td className="py-3">{p.company}</td>
                  <td className="py-3">${p.amount.toLocaleString()}</td>
                  <td className="py-3">{p.status}</td>
                  <td className="py-3">{p.date}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};

export default PaymentsOverview;
