import React, { useEffect, useState } from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

const TeamVotes: React.FC = () => {
  const [votes, setVotes] = useState<any[]>([]);

  useEffect(() => {
    setVotes([
      {
        id: 1,
        team: "Alpha Team",
        topic: "Feature A priority",
        votesFor: 12,
        votesAgainst: 3,
        status: "Open",
      },
      {
        id: 2,
        team: "Beta Team",
        topic: "Adopt library X",
        votesFor: 8,
        votesAgainst: 7,
        status: "Closed",
      },
    ]);
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Team Votes</h1>
        <Button text="Create Vote" className="bg-primary-500 text-white" />
      </div>

      <Card>
        <div className="overflow-x-auto">
          <table className="min-w-full text-sm">
            <thead>
              <tr className="text-left text-gray-600">
                <th className="py-2">#</th>
                <th className="py-2">Team</th>
                <th className="py-2">Topic</th>
                <th className="py-2">For</th>
                <th className="py-2">Against</th>
                <th className="py-2">Status</th>
                <th className="py-2">Actions</th>
              </tr>
            </thead>
            <tbody>
              {votes.map((v) => (
                <tr key={v.id} className="border-t">
                  <td className="py-3">{v.id}</td>
                  <td className="py-3">{v.team}</td>
                  <td className="py-3">{v.topic}</td>
                  <td className="py-3">{v.votesFor}</td>
                  <td className="py-3">{v.votesAgainst}</td>
                  <td className="py-3">{v.status}</td>
                  <td className="py-3">
                    <Button text="View" className="btn-outline-dark" />
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

export default TeamVotes;
