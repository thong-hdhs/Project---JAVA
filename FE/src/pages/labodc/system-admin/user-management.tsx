import React from 'react';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';

const UserManagement: React.FC = () => {
	const users = [
		{ id: 1, name: 'Alice', role: 'Lab Admin' },
	];

	return (
		<div className="space-y-6">
			<div className="flex items-center justify-between">
				<h1 className="text-2xl font-bold text-gray-900">User Management</h1>
				<Button text="Invite User" className="bg-primary-500 text-white" />
			</div>
			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600"><th className="py-2">Name</th><th className="py-2">Role</th><th className="py-2">Actions</th></tr>
						</thead>
						<tbody>
							{users.map(u => (
								<tr key={u.id} className="border-t"><td className="py-3">{u.name}</td><td className="py-3">{u.role}</td><td className="py-3">-</td></tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default UserManagement;
