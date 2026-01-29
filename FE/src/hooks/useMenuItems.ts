import { useSelector } from "react-redux";
import { UserRole } from "../types";

export const getDashboardLink = (role: UserRole): string => {
  const roleDashboards: Record<string, string> = {
    SYSTEM_ADMIN: "system-admin/dashboard",
    LAB_ADMIN: "lab-admin/dashboard",
    COMPANY: "enterprise/dashboard",
    MENTOR: "mentor/dashboard",
    TALENT: "candidate/dashboard",
    TALENT_LEADER: "candidate/dashboard",
  };
  return roleDashboards[role] || "candidate/dashboard";
};

export const useMenuItems = () => {
  const { user } = useSelector((state: any) => state.auth);

  const getMenuItems = () => {
    if (!user) return [];

    const role = user.role as UserRole;

    const baseMenuItems = [
      {
        isHeadr: true,
        title: "LabOdc",
      },
      {
        title: "Dashboard",
        icon: "heroicons-outline:home",
        link: getDashboardLink(role),
      },
      {
        title: "Profile",
        icon: "heroicons-outline:user",
        link: "profile",
      },
      {
        title: "Notifications",
        icon: "heroicons-outline:bell",
        link: "notifications",
      },
    ];

    switch (role) {
      case "SYSTEM_ADMIN":
        return [
          ...baseMenuItems,
          {
            title: "Mentor Role Requests",
            icon: "heroicons-outline:user-plus",
            link: "system-admin/mentor-role-requests",
          },
          {
            title: "User Management",
            icon: "heroicons-outline:users",
            link: "system-admin/users",
          },
          {
            title: "Settings",
            icon: "heroicons-outline:cog-6-tooth",
            link: "system-admin/settings",
          },
          {
            title: "Excel Templates",
            icon: "heroicons-outline:document",
            link: "system-admin/excel-templates",
          },
          {
            title: "Email Templates",
            icon: "heroicons-outline:envelope",
            link: "system-admin/email-templates",
          },
          {
            title: "Audit Logs",
            icon: "heroicons-outline:shield-check",
            link: "system-admin/audit-logs",
          },
        ];

      case "LAB_ADMIN":
        return [
          ...baseMenuItems,
          {
            title: "Reports",
            icon: "heroicons-outline:document-text",
            link: "lab-admin/reports",
          },
          {
            title: "Companies",
            icon: "heroicons-outline:building-office-2",
            child: [
              {
                childtitle: "Company Approvals",
                childlink: "lab-admin/company-approvals",
              },
              {
                childtitle: "Approved Companies",
                childlink: "lab-admin/approved-companies",
              },
              {
                childtitle: "Rejected Companies",
                childlink: "lab-admin/rejected-companies",
              },
            ],
          },
          {
            title: "Projects",
            icon: "heroicons-outline:briefcase",
            child: [
              {
                childtitle: "Validate Projects",
                childlink: "lab-admin/validate-projects",
              },
              {
                childtitle: "Approved Projects",
                childlink: "lab-admin/approved-projects",
              },
              {
                childtitle: "Rejected Projects",
                childlink: "lab-admin/rejected-projects",
              },
            ],
          },
          {
            title: "Mentors",
            icon: "heroicons-outline:user-group",
            link: "lab-admin/mentors",
          },
          {
            title: "Talents",
            icon: "heroicons-outline:academic-cap",
            link: "lab-admin/talents",
          },
          {
            title: "Payments Overview",
            icon: "heroicons-outline:currency-dollar",
            link: "lab-admin/payments-overview",
          },
          {
            title: "Fund Allocations",
            icon: "heroicons-outline:chart-bar",
            link: "lab-admin/fund-allocations",
          },
          {
            title: "Lab Fund Advances",
            icon: "heroicons-outline:banknotes",
            link: "lab-admin/lab-fund-advances",
          },
          {
            title: "Transparency Report",
            icon: "heroicons-outline:eye",
            link: "lab-admin/transparency-report",
          },
          {
            title: "Project Change Requests",
            icon: "heroicons-outline:arrow-path",
            link: "lab-admin/change-request-approvals",
          },
          {
            title: "Risk Records",
            icon: "heroicons-outline:exclamation-triangle",
            link: "lab-admin/risk-records",
          },
        ];

      case "COMPANY":
        return [
          ...baseMenuItems,
          {
            title: "Reports",
            icon: "heroicons-outline:document-text",
            link: "enterprise/reports",
          },
          {
            title: "Projects",
            icon: "heroicons-outline:briefcase",
            child: [
              {
                childtitle: "Project List",
                childlink: "enterprise/projects",
              },
              {
                childtitle: "Create Project",
                childlink: "enterprise/projects/create",
              },
            ],
          },
          {
            title: "Payments",
            icon: "heroicons-outline:currency-dollar",
            link: "enterprise/payments",
          },
          {
            title: "Change Requests",
            icon: "heroicons-outline:arrow-path",
            link: "enterprise/change-requests",
          },
          {
            title: "Evaluations",
            icon: "heroicons-outline:star",
            link: "enterprise/evaluations",
          },
        ];

      case "MENTOR":
        return [
          ...baseMenuItems,
          {
            title: "Reports",
            icon: "heroicons-outline:document-text",
            link: "mentor/reports",
          },
          {
            title: "Project Invitations",
            icon: "heroicons-outline:envelope",
            link: "mentor/invitations",
          },
          {
            title: "Candidate Reviews",
            icon: "heroicons-outline:document-magnifying-glass",
            link: "mentor/candidate-reviews",
          },
          {
            title: "My Projects",
            icon: "heroicons-outline:briefcase",
            child: [
              {
                childtitle: "Project Workspace",
                childlink: "mentor/project/1", // This should be dynamic
              },
              {
                childtitle: "Project Reports",
                childlink: "mentor/reports",
              },
            ],
          },
          {
            title: "Fund Approvals",
            icon: "heroicons-outline:currency-dollar",
            link: "mentor/fund-approvals",
          },
        ];

      case "TALENT":
      case "TALENT_LEADER":
        return [
          ...baseMenuItems,
          {
            title: "Reports",
            icon: "heroicons-outline:document-text",
            link: "candidate/reports",
          },
          {
            title: "Company Verification",
            icon: "heroicons-outline:building-office",
            link: "candidate/company-verification",
          },
          {
            title: "Browse Projects",
            icon: "heroicons-outline:magnifying-glass",
            link: "candidate/browse-projects",
          },
          {
            title: "My Applications",
            icon: "heroicons-outline:document-text",
            link: "candidate/applications",
          },
          {
            title: "My Projects",
            icon: "heroicons-outline:briefcase",
            link: "candidate/my-projects",
          },
          {
            title: "Fund Distributions",
            icon: "heroicons-outline:currency-dollar",
            link: "candidate/fund-distributions",
          },
          {
            title: "Team Votes",
            icon: "heroicons-outline:hand-raised",
            link: "candidate/team-votes",
          },
        ];

      default:
        return baseMenuItems;
    }
  };

  return getMenuItems();
};
