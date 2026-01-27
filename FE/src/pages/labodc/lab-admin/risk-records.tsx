import React from 'react';
import Card from '@/components/ui/Card';

const RiskRecords: React.FC = () => {
	const records = [
		{ id: 1, project: 'Project Alpha', risk: 'Low', description: 'Minor delays' },
	];

	return (
		<div className="space-y-6">
			<h1 className="text-2xl font-bold text-gray-900">Risk Records</h1>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Project</th><th className="py-2">Risk</th><th className="py-2">Description</th></tr>
						</thead>
						<tbody>
							{records.map(r => (
								<tr key={r.id} className="border-t"><td className="py-3">{r.project}</td><td className="py-3">{r.risk}</td><td className="py-3">{r.description}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default RiskRecords;
