import React, { useEffect, useState } from 'react';
import Card from '@/components/ui/Card';
import MetricCard from '@/components/ui/MetricCard';
import Button from '@/components/ui/Button';

const TransparencyReport: React.FC = () => {
	const [summary, setSummary] = useState({ totalReceived: 0, totalDistributed: 0, pending: 0 });
	const [transactions, setTransactions] = useState<any[]>([]);

	useEffect(() => {
		// mock data
		setSummary({ totalReceived: 250000, totalDistributed: 200000, pending: 3 });
		setTransactions([
			{ id: 'T-001', type: 'Disbursement', amount: 5000, project: 'Alpha', date: '2026-01-02', status: 'Completed' },
			{ id: 'T-002', type: 'Receipt', amount: 20000, project: 'Beta', date: '2025-12-15', status: 'Completed' },
		]);
	}, []);

	return (
		<div className="space-y-6">
			<div className="flex items-center justify-between">
				<h1 className="text-2xl font-bold text-gray-900">Transparency Report</h1>
				<div className="flex space-x-2">
					<Button text="Export CSV" className="btn-outline-dark" />
					<Button text="Refresh" className="bg-primary-500 text-white" />
				</div>
			</div>

			<div className="grid grid-cols-1 md:grid-cols-3 gap-6">
				<MetricCard title="Total Received" value={`$${summary.totalReceived.toLocaleString()}`} />
				<MetricCard title="Total Distributed" value={`$${summary.totalDistributed.toLocaleString()}`} />
				<MetricCard title="Pending Transactions" value={summary.pending.toString()} />
			</div>

			<Card title="Transparency Chart">
				<div className="h-40 flex items-center justify-center text-gray-500">
					{/* Placeholder for chart - integrate chart library later */}
					<div className="w-full h-32 bg-gradient-to-r from-gray-100 to-gray-200 rounded" />
				</div>
			</Card>

			<Card title="Recent Transactions">
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">ID</th><th className="py-2">Type</th><th className="py-2">Project</th><th className="py-2">Amount</th><th className="py-2">Date</th><th className="py-2">Status</th></tr>
						</thead>
						<tbody>
							{transactions.map(t => (
								<tr key={t.id} className="border-t"><td className="py-3">{t.id}</td><td className="py-3">{t.type}</td><td className="py-3">{t.project}</td><td className="py-3">${t.amount.toLocaleString()}</td><td className="py-3">{t.date}</td><td className="py-3">{t.status}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default TransparencyReport;
