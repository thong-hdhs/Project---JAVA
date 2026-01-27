import React, { useEffect, useMemo, useState } from "react";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import { projectService, type BackendProjectCreateRequest } from "@/services/project.service";
import { companyService } from "@/services/company.service";

const decodeJwtPayload = (token: string): any => {
  const parts = token.split(".");
  if (parts.length < 2) return null;
  const base64Url = parts[1];
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const padded = base64 + "===".slice((base64.length + 3) % 4);
  try {
    const json = decodeURIComponent(
      atob(padded)
        .split("")
        .map((c) => "%" + c.charCodeAt(0).toString(16).padStart(2, "0"))
        .join(""),
    );
    return JSON.parse(json);
  } catch {
    return null;
  }
};

const getAuthToken = (): string | null => {
  return localStorage.getItem("token") || localStorage.getItem("access_token");
};

const normalizeRole = (r: string) => r.replace(/^ROLE_/, "").toUpperCase();

const getRolesFromToken = (token: string): string[] => {
  const payload = decodeJwtPayload(token);
  const raw = payload?.roles || payload?.authorities || payload?.role;
  const roles = Array.isArray(raw) ? raw : raw ? [raw] : [];
  return roles.map(String).map(normalizeRole);
};

const CreateProject: React.FC = () => {
  const { user } = useSelector((state: any) => state.auth);
  const [submitting, setSubmitting] = useState(false);
  const [companyId, setCompanyId] = useState<string>("");
  const [companyIdLoading, setCompanyIdLoading] = useState(false);
  const [form, setForm] = useState({
    projectName: "",
    description: "",
    requirements: "",
    requiredSkills: "",
    budget: "",
    durationMonths: "",
    maxTeamSize: "",
    startDate: "",
    endDate: "",
  });

  useEffect(() => {
    let cancelled = false;

    const load = async () => {
      const token = getAuthToken();
      if (!token || !user?.id) return;

      setCompanyIdLoading(true);
      try {
        const myCompany = await companyService.getMyCompany();
        if (!cancelled) setCompanyId(String(myCompany.id || ""));
      } catch (err: any) {
        if ((import.meta as any).env?.DEV) {
          console.debug("[CreateProject] getMyCompany failed", err);
        }
        if (!cancelled) setCompanyId("");
      } finally {
        if (!cancelled) setCompanyIdLoading(false);
      }
    };

    load();
    return () => {
      cancelled = true;
    };
  }, [user?.id]);

  const canSubmit = useMemo(() => {
    return Boolean(companyId.trim() && form.projectName.trim());
  }, [companyId, form.projectName]);

  const handleChange = (e: any) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e: any) => {
    e.preventDefault();

    const token = getAuthToken();
    if (!token) {
      toast.error("Bạn cần đăng nhập để tạo dự án.");
      return;
    }

    const roles = getRolesFromToken(token);
    if (!roles.includes("COMPANY")) {
      toast.error("Tài khoản không có role COMPANY.");
      return;
    }

    if (!companyId.trim()) {
      toast.error("Không tìm thấy Company ID. Vui lòng đăng ký công ty và được duyệt trước.");
      return;
    }
    if (!form.projectName.trim()) {
      toast.error("Vui lòng nhập Project Name.");
      return;
    }

    const payload: BackendProjectCreateRequest = {
      companyId: companyId.trim(),
      projectName: form.projectName.trim(),
      description: form.description.trim() || undefined,
      requirements: form.requirements.trim() || undefined,
      requiredSkills: form.requiredSkills.trim() || undefined,
      budget: form.budget ? Number(form.budget) : undefined,
      durationMonths: form.durationMonths ? Number(form.durationMonths) : undefined,
      maxTeamSize: form.maxTeamSize ? Number(form.maxTeamSize) : undefined,
      startDate: form.startDate || undefined,
      endDate: form.endDate || undefined,
    };

    try {
      setSubmitting(true);
      const created = await projectService.createProjectForAppraisal(payload);
      await projectService.submitProjectForAppraisal(created.id);
      toast.success("Đã gửi dự án để Lab Admin thẩm định.");

      setForm((prev) => ({
        ...prev,
        projectName: "",
        description: "",
        requirements: "",
        requiredSkills: "",
        budget: "",
        durationMonths: "",
        maxTeamSize: "",
        startDate: "",
        endDate: "",
      }));
    } catch (err: any) {
      toast.error(err?.message || "Gửi dự án thất bại");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">Create Project</h1>
      <Card>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="text-sm text-gray-700">
            <div className="font-medium text-gray-900">Company ID</div>
            <div className="mt-1">
              {companyIdLoading ? (
                <span className="text-gray-500">Loading...</span>
              ) : companyId ? (
                <span className="font-mono">{companyId}</span>
              ) : (
                <span className="text-red-600">Not found</span>
              )}
            </div>
            <div className="text-xs text-gray-500 mt-1">
                Company ID được lấy từ BE theo user hiện tại (API: /api/v1/companies/me).
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Project Name
            </label>
            <input
              name="projectName"
              value={form.projectName}
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

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Requirements
            </label>
            <textarea
              name="requirements"
              value={form.requirements}
              onChange={handleChange}
              className="mt-1 block w-full border rounded px-3 py-2"
              rows={3}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Required Skills (comma-separated)
            </label>
            <input
              name="requiredSkills"
              value={form.requiredSkills}
              onChange={handleChange}
              className="mt-1 block w-full border rounded px-3 py-2"
              placeholder="React, TypeScript, Spring Boot"
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
                type="number"
                min={0}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Duration (months)
              </label>
              <input
                name="durationMonths"
                value={form.durationMonths}
                onChange={handleChange}
                className="mt-1 block w-full border rounded px-3 py-2"
                type="number"
                min={0}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">
                Max Team Size
              </label>
              <input
                name="maxTeamSize"
                value={form.maxTeamSize}
                onChange={handleChange}
                className="mt-1 block w-full border rounded px-3 py-2"
                type="number"
                min={1}
              />
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
            <div>
              <label className="block text-sm font-medium text-gray-700">Start Date</label>
              <input
                type="date"
                name="startDate"
                value={form.startDate}
                onChange={handleChange}
                className="mt-1 block w-full border rounded px-3 py-2"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">End Date</label>
              <input
                type="date"
                name="endDate"
                value={form.endDate}
                onChange={handleChange}
                className="mt-1 block w-full border rounded px-3 py-2"
              />
            </div>
          </div>

          <div className="flex justify-end">
            <Button
              type="submit"
              text={submitting ? "Submitting..." : "Create & Submit"}
              className="bg-primary-500 text-white"
              disabled={submitting || !canSubmit}
            />
          </div>
        </form>
      </Card>
    </div>
  );
};

export default CreateProject;
