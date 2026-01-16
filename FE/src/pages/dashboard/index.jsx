import React from "react";
import Card from "@/components/ui/Card";
import Icon from "@/components/ui/Icon";
import HomeBredCurbs from "./HomeBredCurbs";

const Dashboard = () => {
  return (
    <div>
      <HomeBredCurbs title="LabOdc Dashboard" />

      {/* Welcome Section */}
      <div className="bg-gradient-to-r from-blue-600 to-blue-700 rounded-lg p-6 text-white mb-6">
        <h1 className="text-2xl font-bold mb-2">Welcome to LabOdc</h1>
        <p className="text-blue-100">
          Enterprise-Student Collaboration Platform at University of Technology and Humanities
        </p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-12 gap-5 mb-6">
        <div className="2xl:col-span-3 lg:col-span-4 col-span-12">
          <Card bodyClass="p-6">
            <div className="flex items-center space-x-4">
              <div className="flex-0">
                <div className="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center">
                  <Icon icon="mdi:account-group" className="w-6 h-6 text-blue-600" />
                </div>
              </div>
              <div className="flex-1">
                <div className="text-xl font-semibold text-slate-900 dark:text-slate-300 mb-1">
                  25
                </div>
                <div className="text-sm text-slate-600 dark:text-slate-300">
                  Active Projects
                </div>
        </div>
            </div>
          </Card>
        </div>
        <div className="2xl:col-span-3 lg:col-span-4 col-span-12">
          <Card bodyClass="p-6">
            <div className="flex items-center space-x-4">
              <div className="flex-0">
                <div className="w-12 h-12 bg-green-100 dark:bg-green-900 rounded-lg flex items-center justify-center">
                  <Icon icon="mdi:account-school" className="w-6 h-6 text-green-600" />
                </div>
              </div>
              <div className="flex-1">
                <div className="text-xl font-semibold text-slate-900 dark:text-slate-300 mb-1">
                  200+
                </div>
                <div className="text-sm text-slate-600 dark:text-slate-300">
                  Students
        </div>
      </div>
            </div>
          </Card>
        </div>
        <div className="2xl:col-span-3 lg:col-span-4 col-span-12">
          <Card bodyClass="p-6">
            <div className="flex items-center space-x-4">
              <div className="flex-0">
                <div className="w-12 h-12 bg-purple-100 dark:bg-purple-900 rounded-lg flex items-center justify-center">
                  <Icon icon="mdi:office-building" className="w-6 h-6 text-purple-600" />
                </div>
              </div>
              <div className="flex-1">
                <div className="text-xl font-semibold text-slate-900 dark:text-slate-300 mb-1">
                  15
                </div>
                <div className="text-sm text-slate-600 dark:text-slate-300">
                  Partner Companies
                </div>
        </div>
        </div>
          </Card>
        </div>
        <div className="2xl:col-span-3 lg:col-span-4 col-span-12">
          <Card bodyClass="p-6">
            <div className="flex items-center space-x-4">
              <div className="flex-0">
                <div className="w-12 h-12 bg-orange-100 dark:bg-orange-900 rounded-lg flex items-center justify-center">
                  <Icon icon="mdi:trending-up" className="w-6 h-6 text-orange-600" />
                </div>
              </div>
              <div className="flex-1">
                <div className="text-xl font-semibold text-slate-900 dark:text-slate-300 mb-1">
                  95%
                </div>
                <div className="text-sm text-slate-600 dark:text-slate-300">
                  Success Rate
                </div>
              </div>
            </div>
          </Card>
        </div>
      </div>

      {/* Info Cards */}
      <div className="grid grid-cols-12 gap-5">
        <div className="lg:col-span-6 col-span-12">
          <Card title="About LabOdc">
            <div className="space-y-4">
              <p className="text-slate-600 dark:text-slate-300">
                LabOdc is a non-profit platform that connects enterprises and university students for real-world IT project collaborations.
              </p>
              <div className="flex items-center space-x-4">
                <div className="flex-0">
                  <div className="w-8 h-8 bg-blue-100 dark:bg-blue-900 rounded-full flex items-center justify-center">
                    <Icon icon="mdi:school" className="w-4 h-4 text-blue-600" />
                  </div>
                </div>
                <div className="flex-1">
                  <div className="text-sm font-medium text-slate-900 dark:text-slate-300">
                    University of Technology and Humanities
                  </div>
                </div>
              </div>
            </div>
          </Card>
                </div>

        <div className="lg:col-span-6 col-span-12">
          <Card title="Funding Model">
            <div className="space-y-4">
              <div className="flex items-center justify-between p-3 bg-blue-50 dark:bg-blue-900/20 rounded-lg">
                <span className="text-sm font-medium">Lab Fund</span>
                <span className="text-lg font-bold text-blue-600">70%</span>
              </div>
              <div className="flex items-center justify-between p-3 bg-green-50 dark:bg-green-900/20 rounded-lg">
                <span className="text-sm font-medium">Enterprise Fund</span>
                <span className="text-lg font-bold text-green-600">20%</span>
                </div>
              <div className="flex items-center justify-between p-3 bg-purple-50 dark:bg-purple-900/20 rounded-lg">
                <span className="text-sm font-medium">Talent Fund</span>
                <span className="text-lg font-bold text-purple-600">10%</span>
              </div>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
