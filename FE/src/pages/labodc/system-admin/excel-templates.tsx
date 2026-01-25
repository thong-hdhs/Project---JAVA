import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Card from '@/components/ui/Card';
import Button from '@/components/ui/Button';

interface ExcelTemplate {
	id: string;
	templateName: string;
	description?: string;
	fileUrl: string;
	templateType: string;
	version?: string;
	downloadCount: number;
	createdAt: string;
}

const ExcelTemplates: React.FC = () => {
	const [templates, setTemplates] = useState<ExcelTemplate[]>([]);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		axios
			.get('http://localhost:8082/api/excel-templates/active')
			.then(res => {
				setTemplates(res.data);
			})
			.catch(err => {
				console.error('Get templates error:', err);
			})
			.finally(() => setLoading(false));
	}, []);

	const handleDownload = async (id: string, fileUrl: string) => {
		try {
			await axios.put(
				`http://localhost:8082/api/excel-templates/${id}/download`
			);
			window.open(fileUrl, '_blank');
		} catch (err) {
			console.error('Download error:', err);
		}
	};

	if (loading) {
		return <div>Loading templates...</div>;
	}

	return (
		<div className="space-y-6">
			<div className="flex items-center justify-between">
				<h1 className="text-2xl font-bold text-gray-900">
					Excel Templates
				</h1>
				<Button
					text="Create Template"
					className="bg-primary-500 text-white"
				/>
			</div>

			<Card>
				<div className="overflow-x-auto">
					<table className="min-w-full text-sm">
						<thead>
							<tr className="text-left text-gray-600">
								<th className="py-2">Name</th>
								<th className="py-2">Description</th>
								<th className="py-2">Type</th>
								<th className="py-2">Downloads</th>
								<th className="py-2">Actions</th>
							</tr>
						</thead>
						<tbody>
							{templates.map(t => (
								<tr key={t.id} className="border-t">
									<td className="py-3">
										{t.templateName}
									</td>
									<td className="py-3">
										{t.description || '-'}
									</td>
									<td className="py-3">
										{t.templateType}
									</td>
									<td className="py-3">
										{t.downloadCount}
									</td>
									<td className="py-3">
										<button
											className="text-primary-600 hover:underline"
											onClick={() =>
												handleDownload(
													t.id,
													t.fileUrl
												)
											}
										>
											Download
										</button>
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

export default ExcelTemplates;
