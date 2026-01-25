import React, { useMemo, useState } from "react";
import { useSelector } from "react-redux";
import Card from "@/components/ui/Card";
import { verificationService } from "@/services/verification.service";

type InputFieldProps = {
  label: string;
  value: string;
  placeholder?: string;
  type?: string;
  onChange: (value: string) => void;
};

const InputField: React.FC<InputFieldProps> = ({
  label,
  value,
  placeholder,
  type = "text",
  onChange,
}) => {
  return (
    <label className="block">
      <span className="block text-sm font-medium text-slate-700 dark:text-slate-200 mb-1">
        {label}
      </span>
      <input
        type={type}
        value={value}
        placeholder={placeholder}
        onChange={(e) => onChange(e.target.value)}
        className="w-full h-[48px] rounded-md border border-slate-200 dark:border-slate-700 bg-white dark:bg-slate-800 px-3 text-slate-900 dark:text-slate-100"
      />
    </label>
  );
};

const CompanyVerification: React.FC = () => {
  const { user } = useSelector((state: any) => state.auth);
  const [companyName, setCompanyName] = useState("");
  const [taxCode, setTaxCode] = useState("");
  const [address, setAddress] = useState("");
  const [website, setWebsite] = useState("");
  const [note, setNote] = useState("");
  const [refreshKey, setRefreshKey] = useState(0);

  const myRequest = useMemo(() => {
    void refreshKey;
    if (!user?.id) return null;
    return verificationService.getMyCompanyRequest(user.id);
  }, [refreshKey, user?.id]);

  const statusLabel = myRequest?.status || "NONE";

  const submit = () => {
    if (!user?.id) return;
    if (!companyName.trim()) return;

    verificationService.submitCompanyVerificationRequest({
      userId: user.id,
      email: user.email || "",
      fullName: user.full_name || user.name || user.email || "User",
      companyName: companyName.trim(),
      taxCode: taxCode.trim() || undefined,
      address: address.trim() || undefined,
      website: website.trim() || undefined,
      note: note.trim() || undefined,
    });

    setCompanyName("");
    setTaxCode("");
    setAddress("");
    setWebsite("");
    setNote("");
    setRefreshKey((k) => k + 1);
  };

  if (!user) {
    return (
      <div className="space-y-6">
        <Card title="Company Verification">
          <div className="text-gray-600">Please login first.</div>
        </Card>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="bg-white rounded-lg border border-gray-200 p-6">
        <h1 className="text-2xl font-bold text-gray-900">Register as a Company</h1>
        <p className="text-gray-600 mt-1">Submit your company information for Lab Admin approval</p>
      </div>

      <Card title="Current Status">
        <div className="text-sm text-gray-700">
          Status: <span className="font-semibold">{statusLabel}</span>
        </div>
        {myRequest ? (
          <div className="mt-3 text-sm text-gray-700 space-y-1">
            <div>
              Company: <span className="font-semibold">{myRequest.companyName}</span>
            </div>
            <div>Submitted: {new Date(myRequest.createdAt).toLocaleString()}</div>
            {myRequest.decidedAt ? <div>Reviewed: {new Date(myRequest.decidedAt).toLocaleString()}</div> : null}
            {myRequest.note ? <div>Note: {myRequest.note}</div> : null}
          </div>
        ) : (
          <div className="mt-2 text-gray-500 text-sm">No request submitted yet.</div>
        )}
      </Card>

      <Card title="Submit Company Verification">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <InputField
            label="Company Name"
            placeholder="e.g. TechNova Co., Ltd"
            value={companyName}
            onChange={setCompanyName}
          />
          <InputField
            label="Tax Code (optional)"
            placeholder="Tax code"
            value={taxCode}
            onChange={setTaxCode}
          />
          <InputField
            label="Address (optional)"
            placeholder="Address"
            value={address}
            onChange={setAddress}
          />
          <InputField
            label="Website (optional)"
            placeholder="https://..."
            value={website}
            onChange={setWebsite}
          />
          <label className="block md:col-span-2">
            <span className="block text-sm font-medium text-slate-700 dark:text-slate-200 mb-1">
              Note (optional)
            </span>
            <textarea
              value={note}
              placeholder="Any additional info"
              onChange={(e) => setNote(e.target.value)}
              rows={3}
              className="w-full rounded-md border border-slate-200 dark:border-slate-700 bg-white dark:bg-slate-800 px-3 py-2 text-slate-900 dark:text-slate-100"
            />
          </label>
        </div>

        <div className="mt-4 flex items-center gap-2">
          <button
            type="button"
            className={`btn inline-flex justify-center bg-primary-600 text-white ${
              !companyName.trim() || myRequest?.status === "PENDING"
                ? "opacity-40 cursor-not-allowed"
                : ""
            }`}
            disabled={!companyName.trim() || myRequest?.status === "PENDING"}
            onClick={submit}
          >
            {myRequest?.status === "PENDING"
              ? "Request Submitted (Pending)"
              : "Submit Request"}
          </button>
          <div className="text-xs text-gray-500">
            Lab Admin will review your request in “Company Approvals”.
          </div>
        </div>
      </Card>
    </div>
  );
};

export default CompanyVerification;
