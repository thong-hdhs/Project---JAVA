import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';

interface User {
	id: string;
	fullName: string;
	email: string;
	roles: string[];
	isActive: boolean;
}

const UserManagement: React.FC = () => {
	const [users, setUsers] = useState<User[]>([]);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		axios
			.get('http://localhost:8082/api/v1/users/')
			.then(res => {
				setUsers(res.data.data);
			})
			.catch(err => {
				console.error('Get users error:', err);
			})
			.finally(() => setLoading(false));
	}, []);

	if (loading) {
		return <div>Loading users...</div>;
	}

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
							<tr className="text-left text-gray-600">
								<th className="py-2">Name</th>
								<th className="py-2">Email</th>
								<th className="py-2">Roles</th>
								<th className="py-2">Status</th>
							</tr>
						</thead>
						<tbody>
							{users.map(u => (
								<tr key={u.id} className="border-t">
									<td className="py-3">{u.fullName}</td>
									<td className="py-3">{u.email}</td>
									<td className="py-3">
										{u.roles.join(', ')}
									</td>
									<td className="py-3">
										{u.isActive ? 'Active' : 'Inactive'}
									</td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
			</Card>
		</div>
	);
};

export default UserManagement;
