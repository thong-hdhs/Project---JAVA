import React, { useEffect, useMemo, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";
import Icon from "@/components/ui/Icon";
import InputGroup from "@/components/ui/InputGroup";
import Textarea from "@/components/ui/Textarea";
import { getDashboardLink } from "@/hooks/useMenuItems";
import type { UserRole } from "@/types";
import { setUser } from "@/store/api/auth/authSlice";
import {
	getCandidateProfile,
	updateCandidateProfile,
	createCandidateProfile,
} from "@/services";

type CandidateProfileForm = {
	studentCode: string;
	major?: string;
	year?: number | "";
	skills?: string;
	certifications?: string;
	portfolioUrl?: string;
	githubUrl?: string;
	linkedinUrl?: string;
};

const decodeJwtPayload = (token?: string | null): any => {
	if (!token) return null;
	const parts = String(token).split(".");
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

const getTalentIdFromToken = (token?: string | null): string | null => {
	const payload = decodeJwtPayload(token) || {};
	return (
		payload.talentId ||
		payload.talent_id ||
		payload.talent ||
		payload.tid ||
		null
	);
};

const ProfilePage: React.FC = () => {
	const dispatch = useDispatch();
	const { user, token } = useSelector((state: any) => state.auth);
	const [avatarUpdating, setAvatarUpdating] = useState(false);
	const fileInputRef = useRef<HTMLInputElement | null>(null);
	const dashboardHref = user?.role ? `/${getDashboardLink(user.role)}` : "/";
	const isTalentRole = ["TALENT", "TALENT_LEADER"].includes(String(user?.role || ""));
	const tokenTalentId = getTalentIdFromToken(token || localStorage.getItem("token"));
	const candidateId =
		tokenTalentId ||
		user?.talent_id ||
		user?.talentId ||
		localStorage.getItem("talentId") ||
		user?.id ||
		"";

	const [candidateProfile, setCandidateProfile] = useState<CandidateProfileForm>({
		studentCode: "",
	});
	const [candidateRecordId, setCandidateRecordId] = useState<string | null>(null);
	const [profileLoading, setProfileLoading] = useState(false);
	const [profileSaving, setProfileSaving] = useState(false);
	const [profileError, setProfileError] = useState<string | null>(null);
	const [profileSuccess, setProfileSuccess] = useState<string | null>(null);

	const roleLabel = useMemo(() => {
		const role = (user?.role || "") as UserRole;
		const labels: Record<string, string> = {
			SYSTEM_ADMIN: "System Admin",
			LAB_ADMIN: "Lab Admin",
			COMPANY: "Enterprise",
			MENTOR: "Mentor",
			TALENT: "Candidate",
			TALENT_LEADER: "Candidate Leader",
		};
		return labels[role] || role || "User";
	}, [user?.role]);

	const displayName = useMemo(() => {
		return user?.full_name || user?.name || user?.email || "User";
	}, [user]);

	const initials = useMemo(() => {
		const raw = String(displayName || "User").trim();
		const parts = raw.split(/\s+/).filter(Boolean);
		const first = parts[0]?.[0] || "U";
		const last = parts.length > 1 ? parts[parts.length - 1]?.[0] : "";
		return (first + last).toUpperCase();
	}, [displayName]);

	const skillList = useMemo(() => {
		const raw = candidateProfile.skills || "";
		return raw
			.split(",")
			.map((s) => s.trim())
			.filter(Boolean);
	}, [candidateProfile.skills]);

	useEffect(() => {
		if (!isTalentRole) return;
		if (!token) {
			setProfileError("Vui lòng đăng nhập để tải hồ sơ cá nhân.");
			return;
		}
		if (!candidateId) {
			setProfileError("Không tìm thấy ID hồ sơ cá nhân.");
			return;
		}

		setProfileLoading(true);
		setProfileError(null);
		getCandidateProfile(String(candidateId))
			.then((res) => {
				const data = res?.data?.data || res?.data || {};
				setCandidateRecordId(data.id || null);
				setCandidateProfile({
					studentCode: data.studentCode || data.student_code || "",
					major: data.major || "",
					year: data.year ?? "",
					skills: data.skills || "",
					certifications: data.certifications || "",
					portfolioUrl: data.portfolioUrl || data.portfolio_url || "",
					githubUrl: data.githubUrl || data.github_url || "",
					linkedinUrl: data.linkedinUrl || data.linkedin_url || "",
				});
			})
			.catch((err) => {
				const status = err?.response?.status;
				if (status === 404) {
					setCandidateRecordId(null);
					setProfileError(null);
					return;
				}
				const apiData = err?.response?.data;
				const message =
					apiData?.errors?.join?.("; ") ||
					apiData?.message ||
					"Không thể tải hồ sơ cá nhân.";
				setProfileError(message);
			})
			.finally(() => setProfileLoading(false));
	}, [candidateId, isTalentRole, token]);

	const quickActions = useMemo(() => {
		const role = (user?.role || "") as UserRole;
		if (!role) return [] as Array<{ label: string; to: string; icon: string }>;

		if (role === "SYSTEM_ADMIN") {
			return [
				{ label: "User Management", to: "/system-admin/users", icon: "heroicons-outline:users" },
				{ label: "Settings", to: "/system-admin/settings", icon: "heroicons-outline:cog-6-tooth" },
				{ label: "Audit Logs", to: "/system-admin/audit-logs", icon: "heroicons-outline:shield-check" },
			];
		}
		if (role === "LAB_ADMIN") {
			return [
				{ label: "Validate Projects", to: "/lab-admin/validate-projects", icon: "heroicons-outline:document-check" },
				{ label: "Payments Overview", to: "/lab-admin/payments-overview", icon: "heroicons-outline:currency-dollar" },
				{ label: "Fund Allocations", to: "/lab-admin/fund-allocations", icon: "heroicons-outline:chart-bar" },
			];
		}
		if (role === "COMPANY") {
			return [
				{ label: "Create Project", to: "/enterprise/projects/create", icon: "heroicons-outline:plus-circle" },
				{ label: "My Projects", to: "/enterprise/projects", icon: "heroicons-outline:briefcase" },
				{ label: "Payments", to: "/enterprise/payments", icon: "heroicons-outline:currency-dollar" },
			];
		}
		if (role === "MENTOR") {
			return [
				{ label: "Invitations", to: "/mentor/invitations", icon: "heroicons-outline:envelope" },
				{ label: "Candidate Reviews", to: "/mentor/candidate-reviews", icon: "heroicons-outline:document-magnifying-glass" },
				{ label: "Reports", to: "/mentor/reports", icon: "heroicons-outline:document-text" },
			];
		}
		return [
			{ label: "Browse Projects", to: "/candidate/browse-projects", icon: "heroicons-outline:magnifying-glass" },
			{ label: "My Applications", to: "/candidate/applications", icon: "heroicons-outline:document-text" },
			{ label: "My Projects", to: "/candidate/my-projects", icon: "heroicons-outline:briefcase" },
		];
	}, [user?.role]);

	const sampleProjects = useMemo(() => {
		const role = (user?.role || "") as UserRole;
		if (role === "COMPANY") {
			return [
				{ name: "Project Alpha", meta: "Web Platform • Approved", date: "2026-02-10" },
				{ name: "Project Beta", meta: "Mobile App • Pending", date: "2026-03-01" },
				{ name: "Project Gamma", meta: "Data Pipeline • Draft", date: "2026-03-15" },
			];
		}
		if (role === "MENTOR") {
			return [
				{ name: "Mentoring: Alpha", meta: "In Progress", date: "2026-02-12" },
				{ name: "Mentoring: Beta", meta: "Pending Review", date: "2026-02-28" },
				{ name: "Mentoring: Gamma", meta: "Planning", date: "2026-03-10" },
			];
		}
		if (role === "LAB_ADMIN" || role === "SYSTEM_ADMIN") {
			return [
				{ name: "Project Delta", meta: "Awaiting Validation", date: "2026-02-18" },
				{ name: "Project Epsilon", meta: "Compliance Check", date: "2026-02-25" },
				{ name: "Project Zeta", meta: "Payments Review", date: "2026-03-05" },
			];
		}
		return [
			{ name: "Application: Alpha", meta: "Submitted", date: "2026-02-11" },
			{ name: "Team: Beta", meta: "In Progress", date: "2026-02-23" },
			{ name: "Project Gamma", meta: "Planned", date: "2026-03-06" },
		];
	}, [user?.role]);

	const handleAvatarUpload = async (file: File) => {
		if (!user) return;
		try {
			setAvatarUpdating(true);
			const dataUrl: string = await new Promise((resolve, reject) => {
				const reader = new FileReader();
				reader.onload = () => resolve(String(reader.result || ""));
				reader.onerror = () => reject(new Error("Failed to read file"));
				reader.readAsDataURL(file);
			});

			dispatch(
				setUser({
					user: {
						...user,
						avatar_url: dataUrl,
					},
					token: token || localStorage.getItem("token") || "",
				})
			);
		} finally {
			setAvatarUpdating(false);
		}
	};

	const handleAvatarInputChange = async (
		e: React.ChangeEvent<HTMLInputElement>
	) => {
		const file = e.target.files?.[0];
		// reset so same file can be selected again
		e.target.value = "";
		if (!file) return;
		await handleAvatarUpload(file);
	};

	const handleProfileChange = (
		e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
	) => {
		const { name, value } = e.target;
		setCandidateProfile((prev) => ({
			...prev,
			[name]: name === "year" ? (value === "" ? "" : Number(value)) : value,
		}));
	};

	const handleProfileSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setProfileSuccess(null);
		setProfileError(null);

		if (!token) {
			setProfileError("Vui lòng đăng nhập để cập nhật hồ sơ.");
			return;
		}
		if (!isTalentRole) {
			setProfileError("Tài khoản không có quyền cập nhật hồ sơ cá nhân.");
			return;
		}
		if (!candidateId && !candidateRecordId) {
			setProfileError("Không tìm thấy ID hồ sơ cá nhân.");
			return;
		}
		if (!candidateProfile.studentCode?.trim()) {
			setProfileError("Mã sinh viên là bắt buộc.");
			return;
		}

		try {
			setProfileSaving(true);
			const payload = {
				studentCode: candidateProfile.studentCode,
				major: candidateProfile.major || undefined,
				year: candidateProfile.year === "" ? undefined : Number(candidateProfile.year),
				skills: candidateProfile.skills || undefined,
				certifications: candidateProfile.certifications || undefined,
				portfolioUrl: candidateProfile.portfolioUrl || undefined,
				githubUrl: candidateProfile.githubUrl || undefined,
				linkedinUrl: candidateProfile.linkedinUrl || undefined,
			};

			if (candidateRecordId) {
				const res = await updateCandidateProfile(String(candidateRecordId), payload);
				const data = res?.data?.data || res?.data || {};
				setCandidateProfile({
					studentCode: data.studentCode || data.student_code || payload.studentCode,
					major: data.major ?? payload.major ?? "",
					year: data.year ?? payload.year ?? "",
					skills: data.skills ?? payload.skills ?? "",
					certifications: data.certifications ?? payload.certifications ?? "",
					portfolioUrl: data.portfolioUrl || data.portfolio_url || payload.portfolioUrl || "",
					githubUrl: data.githubUrl || data.github_url || payload.githubUrl || "",
					linkedinUrl: data.linkedinUrl || data.linkedin_url || payload.linkedinUrl || "",
				});
				setProfileSuccess("Cập nhật hồ sơ thành công.");
			} else {
				const res = await createCandidateProfile(payload);
				const data = res?.data?.data || res?.data || {};
				setCandidateRecordId(data.id || null);
				setCandidateProfile({
					studentCode: data.studentCode || data.student_code || payload.studentCode,
					major: data.major ?? payload.major ?? "",
					year: data.year ?? payload.year ?? "",
					skills: data.skills ?? payload.skills ?? "",
					certifications: data.certifications ?? payload.certifications ?? "",
					portfolioUrl: data.portfolioUrl || data.portfolio_url || payload.portfolioUrl || "",
					githubUrl: data.githubUrl || data.github_url || payload.githubUrl || "",
					linkedinUrl: data.linkedinUrl || data.linkedin_url || payload.linkedinUrl || "",
				});
				setProfileSuccess("Tạo hồ sơ thành công.");
			}
		} catch (err: any) {
			const apiData = err?.response?.data;
			const message =
				apiData?.errors?.join?.("; ") ||
				apiData?.message ||
				"Cập nhật hồ sơ thất bại.";
			setProfileError(message);
		} finally {
			setProfileSaving(false);
		}
	};

	if (!user) {
		return (
			<div className="space-y-6">
				<Card title="My Profile" subtitle="Personal information and account details">
					<div className="text-slate-600 dark:text-slate-300">
						No user information available.
					</div>
				</Card>
			</div>
		);
	}

	return (
		<div className="space-y-6">
			{/* Header section (like screenshot) */}
			<div className="rounded-lg overflow-hidden bg-white dark:bg-slate-800 shadow-base">
				<div className="h-44 bg-gradient-to-r from-slate-900 via-slate-800 to-slate-900" />
				<div className="px-6 pb-6">
					<div className="-mt-14 flex flex-col md:flex-row md:items-end md:justify-between gap-4">
						<div className="flex items-end gap-4">
							<div className="relative">
								<div className="h-28 w-28 rounded-full bg-white dark:bg-slate-800 p-1">
									<div className="h-full w-full rounded-full bg-slate-100 dark:bg-slate-700 overflow-hidden flex items-center justify-center">
										{user.avatar_url ? (
											<img
												src={user.avatar_url}
												alt={displayName}
												className="h-full w-full object-cover"
											/>
										) : (
											<span className="text-2xl font-semibold text-slate-700 dark:text-slate-100">
												{initials}
											</span>
										)}
									</div>
								</div>
								<button
									type="button"
									onClick={() => fileInputRef.current?.click()}
									className="absolute bottom-1 right-1 h-9 w-9 rounded-full bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 shadow flex items-center justify-center"
									title="Change avatar"
									disabled={avatarUpdating}
								>
									<Icon icon={avatarUpdating ? "heroicons-outline:arrow-path" : "heroicons-outline:pencil-square"} className="text-slate-700 dark:text-slate-100" />
								</button>
								<input
									ref={fileInputRef}
									type="file"
									accept="image/*"
									onChange={handleAvatarInputChange}
									style={{ display: "none" }}
									aria-hidden
								/>
							</div>

							<div className="pb-2">
								<div className="text-xl font-semibold text-slate-900 dark:text-slate-100">
									{displayName}
								</div>
								<div className="text-sm text-slate-500 dark:text-slate-300">
									{roleLabel}
								</div>
							</div>
						</div>

						<div className="flex items-center gap-2">
							<Link to={dashboardHref}>
								<Button text="Back to Dashboard" className="btn-outline-primary btn-sm" />
							</Link>
							<Link to="/notifications">
								<Button text="Notifications" className="btn-outline-secondary btn-sm" />
							</Link>
						</div>
					</div>
				</div>
			</div>

			{/* Main content grid: explicitly NO stats strip (image 2) */}
			<div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
				<Card title="Info" bodyClass="p-6">
					<div className="space-y-5 text-sm">
						<div className="flex items-start gap-3">
							<div className="mt-0.5 text-slate-500 dark:text-slate-300">
								<Icon icon="heroicons-outline:envelope" />
							</div>
							<div>
								<div className="text-xs uppercase tracking-wide text-slate-500 dark:text-slate-400">Email</div>
								<div className="text-slate-900 dark:text-slate-100">{user.email || "-"}</div>
							</div>
						</div>
						<div className="flex items-start gap-3">
							<div className="mt-0.5 text-slate-500 dark:text-slate-300">
								<Icon icon="heroicons-outline:phone" />
							</div>
							<div>
								<div className="text-xs uppercase tracking-wide text-slate-500 dark:text-slate-400">Phone</div>
								<div className="text-slate-900 dark:text-slate-100">
									{user.phone || user.phone_number || "-"}
								</div>
							</div>
						</div>
						<div className="flex items-start gap-3">
							<div className="mt-0.5 text-slate-500 dark:text-slate-300">
								<Icon icon="heroicons-outline:map-pin" />
							</div>
							<div>
								<div className="text-xs uppercase tracking-wide text-slate-500 dark:text-slate-400">Location</div>
								<div className="text-slate-900 dark:text-slate-100">Home</div>
							</div>
						</div>

						{quickActions.length > 0 && (
							<div className="pt-2">
								<div className="text-xs uppercase tracking-wide text-slate-500 dark:text-slate-400 mb-2">
									Quick Actions
								</div>
								<div className="space-y-2">
									{quickActions.map((a) => (
										<Link
											key={a.to}
											to={a.to}
											className="flex items-center gap-2 text-slate-700 dark:text-slate-200 hover:text-primary-600"
										>
											<Icon icon={a.icon} className="text-lg" />
											<span className="text-sm">{a.label}</span>
										</Link>
									))}
								</div>
							</div>
						)}
					</div>
				</Card>

				<Card title="Projects" bodyClass="p-0">
					<div className="divide-y divide-slate-200 dark:divide-slate-700">
						{sampleProjects.map((p) => (
							<div key={p.name} className="px-6 py-4 flex items-center justify-between">
								<div>
									<div className="font-medium text-slate-900 dark:text-slate-100">{p.name}</div>
									<div className="text-sm text-slate-500 dark:text-slate-400">{p.meta}</div>
								</div>
								<div className="text-sm text-slate-400">{p.date}</div>
							</div>
						))}
					</div>
				</Card>

				<Card
					title={user.role === "MENTOR" ? "Mentor Evaluations" : "Role Details"}
					bodyClass="p-6"
					className="lg:col-span-2 lg:col-start-2 lg:row-start-1 lg:row-span-2 h-full"
				>
					{user.role === "MENTOR" ? (
						<div className="space-y-4 text-sm">
							<div>
								<div className="font-medium text-slate-900 dark:text-slate-100">John Doe</div>
								<div className="text-slate-500 dark:text-slate-400">4.5/5 — Great attention to detail and timely delivery.</div>
							</div>
							<div>
								<div className="font-medium text-slate-900 dark:text-slate-100">Jane Smith</div>
								<div className="text-slate-500 dark:text-slate-400">4/5 — Solid technical skills; improve communication.</div>
							</div>
						</div>
					) : user.role === "COMPANY" ? (
						<div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm">
							<div>
								<div className="text-slate-500 dark:text-slate-400">Company</div>
								<div className="text-slate-900 dark:text-slate-100 font-medium">TechCorp (sample)</div>
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">Status</div>
								<div className="text-slate-900 dark:text-slate-100 font-medium">Approved</div>
							</div>
							<div className="md:col-span-2">
								<div className="text-slate-500 dark:text-slate-400">Note</div>
								<div className="text-slate-900 dark:text-slate-100">
									Use “Payments” to open QR payment flow for approved projects.
								</div>
							</div>
						</div>
					) : user.role === "LAB_ADMIN" ? (
						<div className="space-y-3 text-sm">
							<div className="text-slate-900 dark:text-slate-100 font-medium">Lab Admin Responsibilities</div>
							<ul className="list-disc pl-5 text-slate-600 dark:text-slate-300 space-y-1">
								<li>Validate projects and manage fund allocations</li>
								<li>Review payments and transparency reports</li>
							</ul>
						</div>
					) : user.role === "SYSTEM_ADMIN" ? (
						<div className="space-y-3 text-sm">
							<div className="text-slate-900 dark:text-slate-100 font-medium">System Admin Responsibilities</div>
							<ul className="list-disc pl-5 text-slate-600 dark:text-slate-300 space-y-1">
								<li>Manage users and templates</li>
								<li>Monitor audit logs and system settings</li>
							</ul>
						</div>
					) : (
						<div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm">
							<div>
								<div className="text-slate-500 dark:text-slate-400">Student status</div>
								<div className="text-slate-900 dark:text-slate-100 font-medium">Active</div>
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">Major</div>
								<div className="text-slate-900 dark:text-slate-100 font-medium">
									{candidateProfile.major || "-"}
								</div>
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">Year</div>
								<div className="text-slate-900 dark:text-slate-100 font-medium">
									{candidateProfile.year || "-"}
								</div>
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">Portfolio</div>
								{candidateProfile.portfolioUrl ? (
									<a
										href={candidateProfile.portfolioUrl}
										target="_blank"
										rel="noreferrer"
										className="text-primary-600"
									>
										{candidateProfile.portfolioUrl}
									</a>
								) : (
									<div className="text-slate-900 dark:text-slate-100 font-medium">-</div>
								)}
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">Skills</div>
								<div className="flex flex-wrap gap-2 mt-1">
									{(skillList.length ? skillList : ["React", "TypeScript", "Teamwork"]).map((s) => (
										<span key={s} className="px-2 py-1 rounded bg-slate-100 dark:bg-slate-700 text-slate-700 dark:text-slate-100 text-xs">
											{s}
										</span>
									))}
								</div>
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">Certifications</div>
								<div className="text-slate-900 dark:text-slate-100 font-medium">
									{candidateProfile.certifications || "-"}
								</div>
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">GitHub</div>
								{candidateProfile.githubUrl ? (
									<a
										href={candidateProfile.githubUrl}
										target="_blank"
										rel="noreferrer"
										className="text-primary-600"
									>
										{candidateProfile.githubUrl}
									</a>
								) : (
									<div className="text-slate-900 dark:text-slate-100 font-medium">-</div>
								)}
							</div>
							<div>
								<div className="text-slate-500 dark:text-slate-400">LinkedIn</div>
								{candidateProfile.linkedinUrl ? (
									<a
										href={candidateProfile.linkedinUrl}
										target="_blank"
										rel="noreferrer"
										className="text-primary-600"
									>
										{candidateProfile.linkedinUrl}
									</a>
								) : (
									<div className="text-slate-900 dark:text-slate-100 font-medium">-</div>
								)}
							</div>
						</div>
					)}
				</Card>
			</div>

			{isTalentRole && (
				<Card
					title="Cập nhật hồ sơ cá nhân"
					subtitle="Kỹ năng, chứng chỉ và portfolio"
					bodyClass="p-6"
				>
					<form className="space-y-4" onSubmit={handleProfileSubmit}>
						<div className="grid grid-cols-1 md:grid-cols-2 gap-4">
							<InputGroup
								label="Mã sinh viên"
								name="studentCode"
								value={candidateProfile.studentCode}
								onChange={handleProfileChange}
								disabled={false}
								placeholder="Mã sinh viên"
							/>
							<InputGroup
								label="Ngành học"
								name="major"
								value={candidateProfile.major || ""}
								onChange={handleProfileChange}
								placeholder="Công nghệ thông tin"
							/>
							<InputGroup
								label="Năm học"
								type="number"
								name="year"
								value={candidateProfile.year ?? ""}
								onChange={handleProfileChange}
								placeholder="2026"
							/>
							<InputGroup
								label="Portfolio URL"
								name="portfolioUrl"
								value={candidateProfile.portfolioUrl || ""}
								onChange={handleProfileChange}
								placeholder="https://portfolio.example.com"
							/>
							<InputGroup
								label="Github URL"
								name="githubUrl"
								value={candidateProfile.githubUrl || ""}
								onChange={handleProfileChange}
								placeholder="https://github.com/username"
							/>
							<InputGroup
								label="LinkedIn URL"
								name="linkedinUrl"
								value={candidateProfile.linkedinUrl || ""}
								onChange={handleProfileChange}
								placeholder="https://linkedin.com/in/username"
							/>
						</div>

						<Textarea
							label="Kỹ năng (phân tách bằng dấu phẩy)"
							name="skills"
							row={3}
							value={candidateProfile.skills || ""}
							onChange={handleProfileChange}
							placeholder="Java, Spring Boot, React"
						/>
						<Textarea
							label="Chứng chỉ"
							name="certifications"
							row={3}
							value={candidateProfile.certifications || ""}
							onChange={handleProfileChange}
							placeholder="AWS, TOEIC..."
						/>

						{profileError && (
							<div className="text-sm text-danger-500">{profileError}</div>
						)}
						{profileSuccess && (
							<div className="text-sm text-success-500">{profileSuccess}</div>
						)}

						<div className="flex items-center gap-3">
							<Button
								type="submit"
								text={profileSaving ? "Đang lưu..." : "Lưu thay đổi"}
								isLoading={profileSaving}
								disabled={profileSaving || profileLoading || !token}
								className="btn-primary"
							/>
							{profileLoading && (
								<span className="text-sm text-slate-500">Đang tải hồ sơ...</span>
							)}
						</div>
					</form>
				</Card>
			)}
		</div>
	);
};

export default ProfilePage;
