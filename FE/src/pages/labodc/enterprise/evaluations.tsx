import React from 'react';
import Card from '@/components/ui/Card';

const Evaluations: React.FC = () => {
	const items = [
		{ id: 1, name: 'Evaluation 1', score: 4.2 },
	];

	return (
		<div className="space-y-6">
			<h1 className="text-2xl font-bold text-gray-900">Evaluations</h1>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Name</th><th className="py-2">Score</th></tr>
						</thead>
						<tbody>
							{items.map(i => (
								<tr key={i.id} className="border-t"><td className="py-3">{i.name}</td><td className="py-3">{i.score}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default Evaluations;
