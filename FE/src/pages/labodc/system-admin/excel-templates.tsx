import React, { useEffect, useState } from "react";
import axios from "axios";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

interface ExcelTemplate {
  id: string;
  templateName: string;
  description?: string;
  fileUrl: string;
  templateType: string;
  version?: string;
  isActive?: boolean;
  downloadCount: number;
  createdBy?: string;
  createdAt: string;
  updatedAt?: string;
}

const ExcelTemplates: React.FC = () => {
  // ========================
  // STATE
  // ========================
  const [templates, setTemplates] = useState<ExcelTemplate[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [showCreate, setShowCreate] = useState(false);
  const [form, setForm] = useState({
    templateName: "",
    description: "",
    fileUrl: "",
    templateType: "TASK_BREAKDOWN",
    version: "",
  });

  // ========================
  // FETCH TEMPLATES
  // ========================
  useEffect(() => {
    const fetchTemplates = async () => {
      try {
        const res = await axios.get(
          "http://localhost:8082/api/excel-templates/active",
        );

        const data = Array.isArray(res.data) ? res.data : [res.data];

        setTemplates(data);
      } catch (err) {
        console.error("Get templates error:", err);
        setError("Failed to load templates");
      } finally {
        setLoading(false);
      }
    };

    fetchTemplates();
  }, []);

  // ========================
  // CREATE TEMPLATE
  // ========================
  const handleCreateTemplate = async () => {
    try {
      await axios.post("http://localhost:8082/api/excel-templates", form);

      setShowCreate(false);
      setForm({
        templateName: "",
        description: "",
        fileUrl: "",
        templateType: "TASK_BREAKDOWN",
        version: "",
      });

      const res = await axios.get(
        "http://localhost:8082/api/excel-templates/active",
      );
      setTemplates(Array.isArray(res.data) ? res.data : [res.data]);
    } catch (err) {
      console.error("Create template error:", err);
      alert("Tạo template thất bại");
    }
  };

  // ========================
  // DOWNLOAD (REDIRECT)
  // ========================
  const handleDownload = (id: string) => {
    window.open(
      `http://localhost:8082/api/excel-templates/${id}/download`,
      "_blank",
    );
  };

  if (loading) return <div className="text-gray-500">Loading templates...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <>
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold text-gray-900">Excel Templates</h1>

          <Button
            text="Create Template"
            className="bg-primary-500 text-white"
            onClick={() => setShowCreate(true)}
          />
        </div>

        <Card>
          <div className="overflow-x-auto">
            <table className="min-w-full text-sm">
              <thead>
                <tr className="text-left text-gray-600 border-b">
                  <th className="py-2">Name</th>
                  <th className="py-2">Description</th>
                  <th className="py-2">Type</th>
                  <th className="py-2">Version</th>
                  <th className="py-2">Downloads</th>
                  <th className="py-2">Actions</th>
                </tr>
              </thead>

              <tbody>
                {templates.length === 0 && (
                  <tr>
                    <td colSpan={6} className="py-4 text-center text-gray-500">
                      Không có template nào
                    </td>
                  </tr>
                )}

                {templates.map((t) => (
                  <tr key={t.id} className="border-t hover:bg-gray-50">
                    <td className="py-3 font-medium">{t.templateName}</td>
                    <td className="py-3">{t.description || "-"}</td>
                    <td className="py-3">{t.templateType}</td>
                    <td className="py-3">{t.version || "-"}</td>
                    <td className="py-3">{t.downloadCount}</td>
                    <td className="py-3">
                      <button
                        className="text-primary-600 hover:underline"
                        onClick={() => handleDownload(t.id)}
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

      {/* ========================
			    CREATE MODAL
			======================== */}
      {showCreate && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white w-[500px] rounded-lg p-6 space-y-4">
            <h2 className="text-lg font-semibold">Create Excel Template</h2>

            <input
              className="w-full border p-2 rounded"
              placeholder="Template name"
              value={form.templateName}
              onChange={(e) =>
                setForm({
                  ...form,
                  templateName: e.target.value,
                })
              }
            />

            <textarea
              className="w-full border p-2 rounded"
              placeholder="Description"
              value={form.description}
              onChange={(e) =>
                setForm({
                  ...form,
                  description: e.target.value,
                })
              }
            />

            <input
              className="w-full border p-2 rounded"
              placeholder="File URL"
              value={form.fileUrl}
              onChange={(e) =>
                setForm({
                  ...form,
                  fileUrl: e.target.value,
                })
              }
            />

            <select
              className="w-full border p-2 rounded"
              value={form.templateType}
              onChange={(e) =>
                setForm({
                  ...form,
                  templateType: e.target.value,
                })
              }
            >
              <option value="TASK_BREAKDOWN">TASK_BREAKDOWN</option>
              <option value="MENTOR_IMPORT">MENTOR_IMPORT</option>
              <option value="STUDENT_IMPORT">STUDENT_IMPORT</option>
            </select>

            <input
              className="w-full border p-2 rounded"
              placeholder="Version (v1.0, v2.0...)"
              value={form.version}
              onChange={(e) =>
                setForm({
                  ...form,
                  version: e.target.value,
                })
              }
            />

            <div className="flex justify-end gap-2 pt-4">
              <button
                className="px-4 py-2 border rounded"
                onClick={() => setShowCreate(false)}
              >
                Cancel
              </button>

              <button
                className="px-4 py-2 bg-primary-500 text-white rounded"
                onClick={handleCreateTemplate}
              >
                Create
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ExcelTemplates;
