import React from 'react';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';

const ChangeRequests: React.FC = () => {
	const items = [
		{ id: 1, title: 'Change request A', project: 'Project Alpha', status: 'Open' },
	];

	return (
		<div className="space-y-6">
			<div className="flex items-center justify-between">
				<h1 className="text-2xl font-bold text-gray-900">Change Requests</h1>
				<Button text="Create Request" className="bg-primary-500 text-white" />
			</div>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Title</th><th className="py-2">Project</th><th className="py-2">Status</th></tr>
						</thead>
						<tbody>
							{items.map(i => (
								<tr key={i.id} className="border-t"><td className="py-3">{i.title}</td><td className="py-3">{i.project}</td><td className="py-3">{i.status}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default ChangeRequests;
