import React from 'react';
import Card from '@/components/ui/Card';

const FundAllocations: React.FC = () => {
	const allocations = [
		{ id: 1, project: 'Project Alpha', amount: 15000, allocated_to: 'Team A' },
	];

	return (
		<div className="space-y-6">
			<h1 className="text-2xl font-bold text-gray-900">Fund Allocations</h1>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Project</th><th className="py-2">Amount</th><th className="py-2">Allocated To</th></tr>
						</thead>
						<tbody>
							{allocations.map(a => (
								<tr key={a.id} className="border-t"><td className="py-3">{a.project}</td><td className="py-3">${a.amount}</td><td className="py-3">{a.allocated_to}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default FundAllocations;
