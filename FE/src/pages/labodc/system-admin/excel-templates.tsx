import React from 'react';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';

const ExcelTemplates: React.FC = () => {
	const templates = [
		{ id: 1, name: 'Project Export', description: 'Export projects to Excel' },
	];

	return (
		<div className="space-y-6">
			<div className="flex items-center justify-between">
				<h1 className="text-2xl font-bold text-gray-900">Excel Templates</h1>
				<Button text="Create Template" className="bg-primary-500 text-white" />
			</div>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Name</th><th className="py-2">Description</th></tr>
						</thead>
						<tbody>
							{templates.map(t => (
								<tr key={t.id} className="border-t"><td className="py-3">{t.name}</td><td className="py-3">{t.description}</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default ExcelTemplates;
