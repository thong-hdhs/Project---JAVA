import React from 'react';
import Card from '@/components/ui/Card';

const AuditLogs: React.FC = () => {
	const logs = [
		{ id: 1, action: 'User login', user: 'Alice', date: '2026-01-01' },
	];

	return (
		<div className="space-y-6">
			<h1 className="text-2xl font-bold text-gray-900">Audit Logs</h1>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Action</th><th className="py-2">User</th><th className="py-2">Date</th></tr>
						</thead>
						<tbody>
							{logs.map(l => (
								<tr key={l.id} className="border-t"><td className="py-3">{l.action}</td><td className="py-3">{l.user}</td><td className="py-3">{l.date}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default AuditLogs;
