import React, { useState } from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import Textinput from "@/components/ui/Textinput";

const CreateProject: React.FC = () => {
  const [form, setForm] = useState({
    name: "",
    description: "",
    budget: "",
    start: "",
    end: "",
  });

  const handleChange = (e: any) =>
    setForm({ ...form, [e.target.name]: e.target.value });
  const handleSubmit = (e: any) => {
    e.preventDefault();
    // TODO: submit to API
    alert("Project created (mock)");
  };

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">Create Project</h1>
      <Card>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Project Name
            </label>
            <input
              name="name"
              value={form.name}
              onChange={handleChange}
              className="mt-1 block w-full border rounded px-3 py-2"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Description
            </label>
            <textarea
              name="description"
              value={form.description}
              onChange={handleChange}
              className="mt-1 block w-full border rounded px-3 py-2"
              rows={4}
            />
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-3">
            <div>
              <label className="block text-sm font-medium text-gray-700">
                Budget
              </label>
              <input
                name="budget"
                value={form.budget}
                onChange={handleChange}
                className="mt-1 block w-full border rounded px-3 py-2"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">
                Start Date
              </label>
              <input
                type="date"
                name="start"
                value={form.start}
                onChange={handleChange}
                className="mt-1 block w-full border rounded px-3 py-2"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">
                End Date
              </label>
              <input
                type="date"
                name="end"
                value={form.end}
                onChange={handleChange}
                className="mt-1 block w-full border rounded px-3 py-2"
              />
            </div>
          </div>
          <div className="flex justify-end">
            <Button text="Create" className="bg-primary-500 text-white" />
          </div>
        </form>
      </Card>
    </div>
  );
};

export default CreateProject;
