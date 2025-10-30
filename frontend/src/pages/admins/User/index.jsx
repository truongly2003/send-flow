import React, { useState, useEffect } from 'react';
import { Search, Edit, Trash2, Eye, Save, X, Users } from 'lucide-react';

function User() {
  const [users, setUsers] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [filterRole, setFilterRole] = useState('all');
  const [showEditModal, setShowEditModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    const mockUsers = [
      {
        id: 1,
        email: 'john.doe@example.com',
        name: 'John Doe',
        role: 'user',
        subscription: 'Premium',
        subscriptionStatus: 'active',
        createdAt: '2025-01-15',
        lastLogin: '2025-10-29',
        totalCampaigns: 12,
        emailsSent: 15000
      },
      {
        id: 2,
        email: 'admin@company.com',
        name: 'Admin User',
        role: 'admin',
        subscription: 'Enterprise',
        subscriptionStatus: 'active',
        createdAt: '2024-12-01',
        lastLogin: '2025-10-30',
        totalCampaigns: 45,
        emailsSent: 89000
      },
      {
        id: 3,
        email: 'jane.smith@startup.com',
        name: 'Jane Smith',
        role: 'user',
        subscription: 'Basic',
        subscriptionStatus: 'expired',
        createdAt: '2025-02-20',
        lastLogin: '2025-10-25',
        totalCampaigns: 3,
        emailsSent: 1200
      },
      {
        id: 4,
        email: 'marketing@company.vn',
        name: 'Marketing Team',
        role: 'user',
        subscription: 'Premium',
        subscriptionStatus: 'active',
        createdAt: '2025-03-10',
        lastLogin: '2025-10-28',
        totalCampaigns: 28,
        emailsSent: 42000
      }
    ];
    setUsers(mockUsers);
  }, []);

  const handleEdit = (user) => {
    setSelectedUser(user);
    setShowEditModal(true);
  };

  const handleRoleChange = (userId, newRole) => {
    setUsers(users.map(u => u.id === userId ? { ...u, role: newRole } : u));
  };

  const handleDelete = (user) => {
    if (confirm(`Bạn có chắc muốn xóa user "${user.email}"?`)) {
      setUsers(users.filter(u => u.id !== user.id));
    }
  };

  const handleSaveEdit = () => {
    setUsers(users.map(u => u.id === selectedUser.id ? selectedUser : u));
    setShowEditModal(false);
    setSelectedUser(null);
  };

  const filteredUsers = users.filter(user => {
    const matchesRole = filterRole === 'all' || user.role === filterRole;
    const matchesSearch = 
      user.email.toLowerCase().includes(searchQuery.toLowerCase()) ||
      user.name.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesRole && matchesSearch;
  });

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-6">
          <h1 className="text-3xl font-bold mb-2">User Management</h1>
          <p className="text-gray-400">Xem, sửa, xóa user và quản lý roles</p>
        </div>

        {/* Filters */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-4 mb-6">
          <div className="flex items-center gap-4">
            {/* Search */}
            <div className="flex-1 relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Tìm kiếm user theo email hoặc tên..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-10 pr-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Role Filter */}
            <div className="flex items-center gap-2 bg-gray-800 rounded-lg p-1">
              <button
                onClick={() => setFilterRole('all')}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterRole === 'all' ? 'bg-blue-600 text-white' : 'text-gray-400 hover:text-white'
                }`}
              >
                Tất cả
              </button>
              <button
                onClick={() => setFilterRole('admin')}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterRole === 'admin' ? 'bg-blue-600 text-white' : 'text-gray-400 hover:text-white'
                }`}
              >
                Admin
              </button>
              <button
                onClick={() => setFilterRole('user')}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterRole === 'user' ? 'bg-blue-600 text-white' : 'text-gray-400 hover:text-white'
                }`}
              >
                User
              </button>
            </div>
          </div>
        </div>

        {/* Users Table */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-800/50">
                <tr>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">User</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Role</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Subscription</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Stats</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Last Login</th>
                  <th className="px-6 py-4 text-right text-sm font-semibold text-gray-300">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-800">
                {filteredUsers.map(user => (
                  <tr key={user.id} className="hover:bg-gray-800/30 transition-colors">
                    <td className="px-6 py-4">
                      <div>
                        <p className="font-medium text-white">{user.name}</p>
                        <p className="text-sm text-gray-400">{user.email}</p>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <select
                        value={user.role}
                        onChange={(e) => handleRoleChange(user.id, e.target.value)}
                        className={`px-3 py-1.5 rounded-lg text-sm font-medium bg-gray-800 border border-gray-700 focus:outline-none focus:border-blue-500 ${
                          user.role === 'admin' ? 'text-red-400' : 'text-blue-400'
                        }`}
                      >
                        <option value="user">User</option>
                        <option value="admin">Admin</option>
                      </select>
                    </td>
                    <td className="px-6 py-4">
                      <div>
                        <p className="font-medium text-white">{user.subscription}</p>
                        <span className={`text-xs px-2 py-1 rounded-full ${
                          user.subscriptionStatus === 'active' 
                            ? 'bg-green-400/10 text-green-400' 
                            : 'bg-red-400/10 text-red-400'
                        }`}>
                          {user.subscriptionStatus}
                        </span>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <div className="text-sm">
                        <p className="text-gray-400">{user.totalCampaigns} campaigns</p>
                        <p className="text-gray-400">{user.emailsSent.toLocaleString()} emails</p>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <p className="text-sm text-gray-400">{user.lastLogin}</p>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center justify-end gap-2">
                        <button
                          onClick={() => handleEdit(user)}
                          className="p-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors text-blue-400"
                          title="Edit"
                        >
                          <Edit size={16} />
                        </button>
                        <button
                          onClick={() => handleDelete(user)}
                          className="p-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors text-red-400"
                          title="Delete"
                        >
                          <Trash2 size={16} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {filteredUsers.length === 0 && (
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-12 text-center mt-6">
            <Users className="mx-auto mb-4 text-gray-600" size={48} />
            <p className="text-gray-400">Không tìm thấy user nào</p>
          </div>
        )}

        {/* Edit Modal */}
        {showEditModal && selectedUser && (
          <div className="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50 p-4">
            <div className="bg-gray-900 border border-gray-800 rounded-xl max-w-2xl w-full">
              <div className="flex justify-between items-center p-6 border-b border-gray-800">
                <h2 className="text-2xl font-bold text-white">Edit User</h2>
                <button onClick={() => setShowEditModal(false)} className="text-gray-400 hover:text-white">
                  <X size={24} />
                </button>
              </div>
              
              <div className="p-6 space-y-4">
                <div>
                  <label className="text-gray-400 text-sm block mb-2">Name</label>
                  <input
                    type="text"
                    value={selectedUser.name}
                    onChange={(e) => setSelectedUser({...selectedUser, name: e.target.value})}
                    className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500"
                  />
                </div>
                <div>
                  <label className="text-gray-400 text-sm block mb-2">Email</label>
                  <input
                    type="email"
                    value={selectedUser.email}
                    onChange={(e) => setSelectedUser({...selectedUser, email: e.target.value})}
                    className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500"
                  />
                </div>
                <div>
                  <label className="text-gray-400 text-sm block mb-2">Role</label>
                  <select
                    value={selectedUser.role}
                    onChange={(e) => setSelectedUser({...selectedUser, role: e.target.value})}
                    className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500"
                  >
                    <option value="user">User</option>
                    <option value="admin">Admin</option>
                  </select>
                </div>
              </div>

              <div className="flex gap-3 p-6 border-t border-gray-800">
                <button
                  onClick={handleSaveEdit}
                  className="flex-1 bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg font-medium flex items-center justify-center gap-2 transition-colors"
                >
                  <Save size={20} />
                  Save Changes
                </button>
                <button
                  onClick={() => setShowEditModal(false)}
                  className="flex-1 bg-gray-800 hover:bg-gray-700 text-white px-6 py-3 rounded-lg font-medium transition-colors"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default User;