import React, { useEffect, useState } from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

const FundDistributions: React.FC = () => {
  const [items, setItems] = useState<any[]>([]);

  useEffect(() => {
    setItems([
      {
        id: "D-001",
        project: "Project Alpha",
        amount: 5000,
        recipient: "Team A",
        date: "2026-01-02",
        status: "Completed",
      },
      {
        id: "D-002",
        project: "Project Beta",
        amount: 2000,
        recipient: "Team B",
        date: "2026-01-10",
        status: "Pending",
      },
    ]);
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Fund Distributions</h1>
        <Button text="New Distribution" className="bg-primary-500 text-white" />
      </div>

      <Card title="Recent Distributions">
        <div className="overflow-x-auto">
          <table className="min-w-full text-sm">
            <thead>
              <tr className="text-left text-gray-600">
                <th className="py-2">ID</th>
                <th className="py-2">Project</th>
                <th className="py-2">Recipient</th>
                <th className="py-2">Amount</th>
                <th className="py-2">Date</th>
                <th className="py-2">Status</th>
              </tr>
            </thead>
            <tbody>
              {items.map((i) => (
                <tr key={i.id} className="border-t">
                  <td className="py-3">{i.id}</td>
                  <td className="py-3">{i.project}</td>
                  <td className="py-3">{i.recipient}</td>
                  <td className="py-3">${i.amount.toLocaleString()}</td>
                  <td className="py-3">{i.date}</td>
                  <td className="py-3">{i.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};

export default FundDistributions;
