import React from 'react';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';

const ValidateProjects: React.FC = () => {
	const projects = [
		{ id: 1, name: 'Project Alpha', company: 'Company A', status: 'Pending' },
	];

	return (
		<div className="space-y-6">
			<div className="flex items-center justify-between">
				<h1 className="text-2xl font-bold text-gray-900">Validate Projects</h1>
			</div>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Project</th><th className="py-2">Company</th><th className="py-2">Status</th><th className="py-2">Actions</th></tr>
						</thead>
						<tbody>
							{projects.map(p => (
								<tr key={p.id} className="border-t"><td className="py-3">{p.name}</td><td className="py-3">{p.company}</td><td className="py-3">{p.status}</td><td className="py-3"><Button text="Approve" className="mr-2"/><Button text="Reject" className="bg-red-500 text-white"/></td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default ValidateProjects;
