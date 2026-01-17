import React from 'react';
import Card from '@/components/ui/Card';

const FundDistributionsAdmin: React.FC = () => {
	const distributions = [
		{ id: 1, project: 'Project Alpha', amount: 10000, date: '2025-10-01' },
	];

	return (
		<div className="space-y-6">
			<h1 className="text-2xl font-bold text-gray-900">Fund Distributions</h1>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Project</th><th className="py-2">Amount</th><th className="py-2">Date</th></tr>
						</thead>
						<tbody>
							{distributions.map(d => (
								<tr key={d.id} className="border-t"><td className="py-3">{d.project}</td><td className="py-3">${d.amount}</td><td className="py-3">{d.date}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default FundDistributionsAdmin;
