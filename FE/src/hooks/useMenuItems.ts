import { useSelector } from "react-redux";
import { UserRole } from "../types";

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
            title: "Validate Companies",
            icon: "heroicons-outline:building-office",
            link: "lab-admin/validate-companies",
          },
          {
            title: "Validate Projects",
            icon: "heroicons-outline:document-check",
            link: "lab-admin/validate-projects",
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
            title: "Fund Distributions",
            icon: "heroicons-outline:credit-card",
            link: "lab-admin/fund-distributions",
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
            title: "Change Requests",
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

  const getDashboardLink = (role: UserRole): string => {
    const roleDashboards = {
      SYSTEM_ADMIN: "system-admin/dashboard",
      LAB_ADMIN: "lab-admin/dashboard",
      COMPANY: "enterprise/dashboard",
      MENTOR: "mentor/dashboard",
      TALENT: "candidate/dashboard",
      TALENT_LEADER: "candidate/dashboard",
    };
    return roleDashboards[role] || "candidate/dashboard";
  };

  return getMenuItems();
};
