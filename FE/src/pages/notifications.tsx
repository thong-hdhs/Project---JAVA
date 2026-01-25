import React from "react";
import Card from "@/components/ui/Card";

const NotificationsPage: React.FC = () => {
	return (
		<div className="space-y-6">
			<Card title="Notifications" subtitle="Recent updates and alerts">
				<div className="text-slate-600 dark:text-slate-300">
					No notifications yet.
				</div>
			</Card>
		</div>
	);
};

export default NotificationsPage;
