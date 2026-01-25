import React from 'react';
import Card from '@/components/ui/Card';

const Payments: React.FC = () => {
	const payments = [
		{ id: 1, method: 'Bank Transfer', amount: 1200, date: '2025-12-01' },
	];

	return (
		<div className="space-y-6">
			<h1 className="text-2xl font-bold text-gray-900">Payments</h1>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Method</th><th className="py-2">Amount</th><th className="py-2">Date</th></tr>
						</thead>
						<tbody>
							{payments.map(p => (
								<tr key={p.id} className="border-t"><td className="py-3">{p.method}</td><td className="py-3">${p.amount}</td><td className="py-3">{p.date}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default Payments;
