import React, { useEffect, useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import InputGroup from "@/components/ui/InputGroup";
import Textarea from "@/components/ui/Textarea";

import { getMyCandidateProfile, updateMyCandidateProfile, type CandidateProfilePayload } from "@/services";

const ProfileUpdatePage: React.FC = () => {
  const navigate = useNavigate();
  const { token, roles: authRoles, user } = useSelector((state: any) => state.auth);

  const tokenRoles = useMemo(() => {
    const raw = Array.isArray(authRoles) ? authRoles : [];
    return raw.map((r: any) => String(r).replace(/^ROLE_/, "").toUpperCase());
  }, [authRoles]);

  const isTalent = tokenRoles.includes("TALENT") || user?.role === "TALENT";

  const [profile, setProfile] = useState<CandidateProfilePayload>({
    studentCode: "",
  });
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  useEffect(() => {
    if (!isTalent) return;
    setLoading(true);
    setError(null);
    getMyCandidateProfile()
      .then((res) => {
        const data = res?.data?.data || res?.data || {};
        setProfile({
          studentCode: data.studentCode || data.student_code || "",
          major: data.major || "",
          year: data.year ?? undefined,
          skills: data.skills || "",
          certifications: data.certifications || "",
          portfolioUrl: data.portfolioUrl || data.portfolio_url || "",
          githubUrl: data.githubUrl || data.github_url || "",
          linkedinUrl: data.linkedinUrl || data.linkedin_url || "",
        });
      })
      .catch((err) => {
        const status = err?.response?.status;
        if (status === 403) {
          setError("Bạn không có quyền cập nhật hồ sơ này.");
          return;
        }
        const apiData = err?.response?.data;
        const message = apiData?.errors?.join?.("; ") || apiData?.message || "Không thể tải hồ sơ cá nhân.";
        setError(message);
      })
      .finally(() => setLoading(false));
  }, [isTalent]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setProfile((prev) => ({
      ...prev,
      [name]: name === "year" ? (value === "" ? undefined : Number(value)) : value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    if (!token) {
      setError("Vui lòng đăng nhập để cập nhật hồ sơ.");
      return;
    }
    if (!isTalent) {
      setError("Tài khoản hiện tại không phải TALENT.");
      return;
    }
    if (!profile.studentCode?.trim()) {
      setError("Mã sinh viên là bắt buộc.");
      return;
    }

    try {
      setSaving(true);
      const payload: CandidateProfilePayload = {
        studentCode: profile.studentCode,
        major: profile.major || undefined,
        year: typeof profile.year === "number" ? profile.year : undefined,
        skills: profile.skills || undefined,
        certifications: profile.certifications || undefined,
        portfolioUrl: profile.portfolioUrl || undefined,
        githubUrl: profile.githubUrl || undefined,
        linkedinUrl: profile.linkedinUrl || undefined,
      };

      const res = await updateMyCandidateProfile(payload);
      const data = res?.data?.data || res?.data || {};
      setProfile({
        studentCode: data.studentCode || data.student_code || payload.studentCode,
        major: data.major ?? payload.major ?? "",
        year: data.year ?? payload.year,
        skills: data.skills ?? payload.skills ?? "",
        certifications: data.certifications ?? payload.certifications ?? "",
        portfolioUrl: data.portfolioUrl || data.portfolio_url || payload.portfolioUrl || "",
        githubUrl: data.githubUrl || data.github_url || payload.githubUrl || "",
        linkedinUrl: data.linkedinUrl || data.linkedin_url || payload.linkedinUrl || "",
      });

      setSuccess("Lưu thay đổi thành công.");
      // Optional: navigate back after a short delay
      setTimeout(() => navigate("/candidate/profile"), 400);
    } catch (err: any) {
      const apiData = err?.response?.data;
      const message = apiData?.errors?.join?.("; ") || apiData?.message || "Cập nhật hồ sơ thất bại.";
      setError(message);
    } finally {
      setSaving(false);
    }
  };

  if (!isTalent) {
    return (
      <Card title="Cập nhật hồ sơ" subtitle="Chỉ áp dụng cho Talent" bodyClass="p-6">
        <div className="text-slate-600 dark:text-slate-300">
          Tài khoản hiện tại không phải TALENT nên không thể cập nhật hồ sơ Talent.
        </div>
        <div className="mt-4">
          <Link to="/candidate/profile">
            <Button text="Quay lại" className="btn-outline-dark" />
          </Link>
        </div>
      </Card>
    );
  }

  return (
    <div className="space-y-6">
      <Card title="Cập nhật hồ sơ cá nhân" subtitle="Kỹ năng, chứng chỉ và portfolio" bodyClass="p-6">
        <form className="space-y-4" onSubmit={handleSubmit}>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <InputGroup
              label="Mã sinh viên"
              name="studentCode"
              value={profile.studentCode}
              onChange={handleChange}
              disabled={false}
              placeholder="Mã sinh viên"
            />
            <InputGroup
              label="Ngành học"
              name="major"
              value={profile.major || ""}
              onChange={handleChange}
              placeholder="Công nghệ thông tin"
            />
            <InputGroup
              label="Năm học"
              type="number"
              name="year"
              value={profile.year ?? ""}
              onChange={handleChange}
              placeholder="2026"
            />
            <InputGroup
              label="Portfolio URL"
              name="portfolioUrl"
              value={profile.portfolioUrl || ""}
              onChange={handleChange}
              placeholder="https://portfolio.example.com"
            />
            <InputGroup
              label="Github URL"
              name="githubUrl"
              value={profile.githubUrl || ""}
              onChange={handleChange}
              placeholder="https://github.com/username"
            />
            <InputGroup
              label="LinkedIn URL"
              name="linkedinUrl"
              value={profile.linkedinUrl || ""}
              onChange={handleChange}
              placeholder="https://linkedin.com/in/username"
            />
          </div>

          <Textarea
            label="Kỹ năng (phân tách bằng dấu phẩy)"
            name="skills"
            row={3}
            value={profile.skills || ""}
            onChange={handleChange}
            placeholder="Java, Spring Boot, React"
          />
          <Textarea
            label="Chứng chỉ"
            name="certifications"
            row={3}
            value={profile.certifications || ""}
            onChange={handleChange}
            placeholder="AWS, TOEIC..."
          />

          {error && <div className="text-sm text-danger-500">{error}</div>}
          {success && <div className="text-sm text-success-500">{success}</div>}

          <div className="flex items-center gap-3">
            <Button
              type="submit"
              text={saving ? "Đang lưu..." : "Lưu thay đổi"}
              isLoading={saving}
              disabled={saving || loading || !token}
              className="btn-primary"
            />
            <Link to="/candidate/profile">
              <Button text="Hủy" className="btn-outline-dark" />
            </Link>
            {loading && <span className="text-sm text-slate-500">Đang tải hồ sơ...</span>}
          </div>
        </form>
      </Card>
    </div>
  );
};

export default ProfileUpdatePage;
