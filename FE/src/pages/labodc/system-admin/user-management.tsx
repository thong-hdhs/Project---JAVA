import React, { useEffect, useState } from "react";
import axios from "axios";
import Card from "@/components/ui/Card";
import Button from "@/components/ui/Button";

const API_BASE = "http://localhost:8082/api/v1/users";

const ROLE_OPTIONS = [
  "SYSTEM_ADMIN",
  "LAB_ADMIN",
  "COMPANY",
  "MENTOR",
  "TALENT",
  "USER",
] as const;

interface User {
  id: string;
  fullName: string;
  email: string;
  username?: string;
  phone?: string;
  roles: string[];
  isActive: boolean;
}

const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);

  // ===== modal state =====
  const [showAdd, setShowAdd] = useState(false);
  const [showView, setShowView] = useState(false);
  const [showEdit, setShowEdit] = useState(false);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  // ===== ADD FORM =====
  const [form, setForm] = useState({
    fullName: "",
    username: "",
    email: "",
    password: "",
    roles: [] as string[],
    isActive: true,
  });

  // ===== EDIT FORM (NO PASSWORD) =====
  const [editForm, setEditForm] = useState({
    email: "",
    fullName: "",
    username: "",
    phone: "",
    isActive: true,
    roles: [] as string[],
  });

  // ======================
  // FETCH USERS
  // ======================
  const fetchUsers = async () => {
    try {
      const res = await axios.get(`${API_BASE}/`);
      setUsers(res.data.data);
    } catch (err) {
      console.error("FETCH USERS ERROR:", err);
      alert("Lỗi hệ thống");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  // ======================
  // ADD USER
  // ======================
  const handleAddUser = async () => {
    try {
      await axios.post(`${API_BASE}/`, form);
      setShowAdd(false);
      resetForm();
      fetchUsers();
    } catch (err) {
      console.error("ADD USER ERROR:", err);
      alert("Tạo user thất bại");
    }
  };

  const resetForm = () => {
    setForm({
      fullName: "",
      username: "",
      email: "",
      password: "",
      roles: [],
      isActive: true,
    });
  };

  // ======================
  // VIEW USER
  // ======================
  const handleViewUser = async (id: string) => {
    try {
      const res = await axios.get(`${API_BASE}/${id}`);
      setSelectedUser(res.data.data);
      setShowView(true);
    } catch (err) {
      console.error("VIEW USER ERROR:", err);
      alert("Không lấy được user");
    }
  };

  // ======================
  // EDIT USER
  // ======================
  const handleEditUser = (user: User) => {
    setSelectedUser(user);
    setEditForm({
      email: user.email,
      fullName: user.fullName,
      username: user.username || "",
      phone: user.phone || "",
      isActive: user.isActive,
      roles: user.roles,
    });
    setShowEdit(true);
  };

  const handleUpdateUser = async () => {
    if (!selectedUser) return;

    try {
      await axios.put(`${API_BASE}/${selectedUser.id}`, {
        email: editForm.email,
        fullName: editForm.fullName,
        username: editForm.username,
        phone: editForm.phone,
        isActive: editForm.isActive,
        roles: editForm.roles,
      });

      setShowEdit(false);
      setSelectedUser(null);
      fetchUsers();
      alert("User updated successfully");
    } catch (err) {
      console.error("UPDATE USER ERROR:", err);
      alert("Failed to update user");
    }
  };

  // ======================
  // DISABLE USER
  // ======================
  const handleDeleteUser = async (id: string) => {
    if (!window.confirm("Are you sure you want to disable this user?")) return;

    try {
      await axios.put(`${API_BASE}/${id}/disable`);
      fetchUsers();
    } catch (err) {
      console.error("DISABLE USER ERROR:", err);
      alert("Failed to disable user");
    }
  };

  if (loading) return <div>Loading users...</div>;

  return (
    <>
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold text-gray-900">User Management</h1>

          <div className="flex gap-2">
            <Button text="Invite User" className="bg-primary-500 text-white" />
            <Button
              text="Add User"
              className="bg-green-500 text-white"
              onClick={() => setShowAdd(true)}
            />
          </div>
        </div>

        <Card>
          <table className="min-w-full text-sm">
            <thead>
              <tr className="text-left text-gray-600">
                <th className="py-2">Name</th>
                <th className="py-2">Email</th>
                <th className="py-2">Roles</th>
                <th className="py-2">Status</th>
                <th className="py-2">Actions</th>
              </tr>
            </thead>
            <tbody>
              {users.map((u) => (
                <tr key={u.id} className="border-t">
                  <td className="py-3">{u.fullName}</td>
                  <td className="py-3">{u.email}</td>
                  <td className="py-3">{u.roles.join(", ")}</td>
                  <td className="py-3">{u.isActive ? "Active" : "Inactive"}</td>
                  <td className="py-3">
                    <div className="flex gap-3">
                      <button
                        className="text-blue-600 hover:underline"
                        onClick={() => handleViewUser(u.id)}
                      >
                        View
                      </button>
                      <button
                        className="text-green-600 hover:underline"
                        onClick={() => handleEditUser(u)}
                      >
                        Update
                      </button>
                      <button
                        className="text-red-600 hover:underline"
                        onClick={() => handleDeleteUser(u.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      </div>

      {/* ===== ADD USER MODAL ===== */}
      {showAdd && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center">
          <div className="bg-white p-6 w-[460px] rounded space-y-3">
            <h2 className="font-semibold text-lg">Add User</h2>

            <input
              className="border p-2 w-full"
              placeholder="Full name"
              value={form.fullName}
              onChange={(e) => setForm({ ...form, fullName: e.target.value })}
            />

            <input
              className="border p-2 w-full"
              placeholder="Username"
              value={form.username}
              onChange={(e) => setForm({ ...form, username: e.target.value })}
            />

            <input
              className="border p-2 w-full"
              placeholder="Email"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
            />

            <input
              type="password"
              className="border p-2 w-full"
              placeholder="Password"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
            />

            <select
              className="border p-2 w-full"
              multiple
              value={form.roles}
              onChange={(e) =>
                setForm({
                  ...form,
                  roles: Array.from(e.target.selectedOptions, (o) => o.value),
                })
              }
            >
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
              <option value="LAB_ADMIN">LAB_ADMIN</option>
              <option value="MENTOR">MENTOR</option>
              <option value="TALENT">TALENT</option>
            </select>

            <div className="flex justify-end gap-2 pt-3">
              <button
                onClick={() => setShowAdd(false)}
                className="border px-4 py-1 rounded"
              >
                Cancel
              </button>
              <button
                onClick={handleAddUser}
                className="bg-green-500 text-white px-4 py-1 rounded"
              >
                Create
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ===== VIEW USER MODAL ===== */}
      {showView && selectedUser && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center">
          <div className="bg-white p-6 w-[420px] rounded space-y-3">
            <h2 className="font-semibold text-lg">User Detail</h2>

            <p>
              <b>Name:</b> {selectedUser.fullName}
            </p>
            <p>
              <b>Email:</b> {selectedUser.email}
            </p>
            <p>
              <b>Roles:</b> {selectedUser.roles.join(", ")}
            </p>
            <p>
              <b>Status:</b> {selectedUser.isActive ? "Active" : "Inactive"}
            </p>

            <div className="flex justify-end pt-3">
              <button
                onClick={() => setShowView(false)}
                className="border px-4 py-1 rounded"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ===== EDIT USER MODAL ===== */}
      {showEdit && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center">
          <div className="bg-white w-[500px] rounded-lg p-6 space-y-4">
            <h2 className="text-lg font-semibold">Update User</h2>

            <input
              className="w-full border p-2 rounded"
              placeholder="Email"
              value={editForm.email}
              onChange={(e) =>
                setEditForm({ ...editForm, email: e.target.value })
              }
            />

            <input
              className="w-full border p-2 rounded"
              placeholder="Full name"
              value={editForm.fullName}
              onChange={(e) =>
                setEditForm({ ...editForm, fullName: e.target.value })
              }
            />

            <input
              className="w-full border p-2 rounded"
              placeholder="Username"
              value={editForm.username}
              onChange={(e) =>
                setEditForm({ ...editForm, username: e.target.value })
              }
            />

            <input
              className="w-full border p-2 rounded"
              placeholder="Phone"
              value={editForm.phone}
              onChange={(e) =>
                setEditForm({ ...editForm, phone: e.target.value })
              }
            />

            <select
              className="w-full border p-2 rounded"
              value={editForm.isActive ? "true" : "false"}
              onChange={(e) =>
                setEditForm({
                  ...editForm,
                  isActive: e.target.value === "true",
                })
              }
            >
              <option value="true">Active</option>
              <option value="false">Inactive</option>
            </select>

            <div className="flex justify-end gap-2 pt-4">
              <button
                className="px-4 py-2 border rounded"
                onClick={() => setShowEdit(false)}
              >
                Cancel
              </button>

              <button
                className="px-4 py-2 bg-primary-500 text-white rounded"
                onClick={handleUpdateUser}
              >
                Update
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default UserManagement;
