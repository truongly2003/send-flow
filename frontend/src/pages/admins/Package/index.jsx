import  { useState, useEffect } from 'react';
import { Plus, Edit, Trash2, Save, X, Package as PackageIcon } from 'lucide-react';

function Package() {
  const [packages, setPackages] = useState([]);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [selectedPackage, setSelectedPackage] = useState(null);
  const [formData, setFormData] = useState({
    name: '',
    price: '',
    description: '',
    limits: {}
  });
  const [limitsJson, setLimitsJson] = useState('');
  const [jsonError, setJsonError] = useState('');

  useEffect(() => {
    const mockPackages = [
      { 
        id: 1, 
        name: 'Basic', 
        price: 99000,
        description: 'Gói cơ bản cho doanh nghiệp nhỏ',
        limits: { 
          emails_per_month: 5000, 
          campaigns: 10, 
          contacts: 1000,
          email_templates: 5,
          custom_domains: 0,
          api_access: false,
          priority_support: false
        },
        subscribers: 45
      },
      { 
        id: 2, 
        name: 'Premium', 
        price: 299000,
        description: 'Gói phổ biến cho doanh nghiệp vừa',
        limits: { 
          emails_per_month: 25000, 
          campaigns: 50, 
          contacts: 10000,
          email_templates: 20,
          custom_domains: 2,
          api_access: true,
          priority_support: false
        },
        subscribers: 128
      },
      { 
        id: 3, 
        name: 'Enterprise', 
        price: 999000,
        description: 'Gói dành cho doanh nghiệp lớn',
        limits: { 
          emails_per_month: 100000, 
          campaigns: -1,
          contacts: 50000,
          email_templates: -1,
          custom_domains: -1,
          api_access: true,
          priority_support: true
        },
        subscribers: 23
      },
    ];
    setPackages(mockPackages);
  }, []);

  const resetForm = () => {
    setFormData({
      name: '',
      price: '',
      description: '',
      limits: {}
    });
    setLimitsJson('');
    setJsonError('');
  };

  const handleCreatePackage = () => {
    resetForm();
    setShowCreateModal(true);
  };

  const handleEditPackage = (pkg) => {
    setSelectedPackage(pkg);
    setFormData({
      name: pkg.name,
      price: pkg.price,
      description: pkg.description,
      limits: { ...pkg.limits }
    });
    setLimitsJson(JSON.stringify(pkg.limits, null, 2));
    setShowEditModal(true);
  };

  const handleDeletePackage = (pkg) => {
    if (confirm(`Bạn có chắc muốn xóa package "${pkg.name}"?\nHiện có ${pkg.subscribers} người đăng ký.`)) {
      setPackages(packages.filter(p => p.id !== pkg.id));
    }
  };

  const validateAndParseJson = (json) => {
    try {
      const parsed = JSON.parse(json);
      setJsonError('');
      return parsed;
    } catch (error) {
      setJsonError('Invalid JSON format: ' + error.message);
      return null;
    }
  };

  const handleLimitsJsonChange = (value) => {
    setLimitsJson(value);
    const parsed = validateAndParseJson(value);
    if (parsed) {
      setFormData({ ...formData, limits: parsed });
    }
  };

  const handleSaveCreate = () => {
    if (!formData.name || !formData.price) {
      alert('Vui lòng điền đầy đủ thông tin');
      return;
    }

    const newPackage = {
      id: Math.max(...packages.map(p => p.id)) + 1,
      ...formData,
      subscribers: 0
    };

    setPackages([...packages, newPackage]);
    setShowCreateModal(false);
    resetForm();
  };

  const handleSaveEdit = () => {
    if (!formData.name || !formData.price) {
      alert('Vui lòng điền đầy đủ thông tin');
      return;
    }

    setPackages(packages.map(p => 
      p.id === selectedPackage.id ? { ...p, ...formData } : p
    ));
    setShowEditModal(false);
    resetForm();
  };

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex justify-between items-center mb-6">
          <div>
            <h1 className="text-3xl font-bold mb-2">Package Management</h1>
            <p className="text-gray-400">Tạo và quản lý các gói dịch vụ</p>
          </div>
          <button 
            onClick={handleCreatePackage}
            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg flex items-center gap-2 transition-colors font-medium"
          >
            <Plus size={20} />
            Create Package
          </button>
        </div>

        {/* Packages Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {packages.map(pkg => (
            <div key={pkg.id} className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors">
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h3 className="text-2xl font-bold text-white mb-1">{pkg.name}</h3>
                  <p className="text-gray-400 text-sm">{pkg.description}</p>
                </div>
                <PackageIcon className="text-blue-400" size={32} />
              </div>
              
              <div className="mb-6">
                <p className="text-4xl font-bold text-blue-400 mb-1">{pkg.price.toLocaleString()}</p>
                <p className="text-gray-400 text-sm">VNĐ / tháng</p>
              </div>

              <div className="space-y-3 mb-6">
                <div className="flex justify-between items-center text-sm border-b border-gray-800 pb-2">
                  <span className="text-gray-400">Emails/month:</span>
                  <span className="text-white font-semibold">
                    {pkg.limits.emails_per_month === -1 ? 'Unlimited' : pkg.limits.emails_per_month.toLocaleString()}
                  </span>
                </div>
                <div className="flex justify-between items-center text-sm border-b border-gray-800 pb-2">
                  <span className="text-gray-400">Campaigns:</span>
                  <span className="text-white font-semibold">
                    {pkg.limits.campaigns === -1 ? 'Unlimited' : pkg.limits.campaigns}
                  </span>
                </div>
                <div className="flex justify-between items-center text-sm border-b border-gray-800 pb-2">
                  <span className="text-gray-400">Contacts:</span>
                  <span className="text-white font-semibold">
                    {pkg.limits.contacts === -1 ? 'Unlimited' : pkg.limits.contacts.toLocaleString()}
                  </span>
                </div>
                <div className="flex justify-between items-center text-sm border-b border-gray-800 pb-2">
                  <span className="text-gray-400">Templates:</span>
                  <span className="text-white font-semibold">
                    {pkg.limits.email_templates === -1 ? 'Unlimited' : pkg.limits.email_templates}
                  </span>
                </div>
                <div className="flex justify-between items-center text-sm border-b border-gray-800 pb-2">
                  <span className="text-gray-400">Custom Domains:</span>
                  <span className="text-white font-semibold">
                    {pkg.limits.custom_domains === -1 ? 'Unlimited' : pkg.limits.custom_domains || 'None'}
                  </span>
                </div>
                
                <div className="pt-2 space-y-2">
                  <div className="flex items-center gap-2">
                    <div className={`w-2 h-2 rounded-full ${pkg.limits.api_access ? 'bg-green-400' : 'bg-gray-600'}`}></div>
                    <span className={`text-sm ${pkg.limits.api_access ? 'text-gray-300' : 'text-gray-500'}`}>API Access</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <div className={`w-2 h-2 rounded-full ${pkg.limits.priority_support ? 'bg-green-400' : 'bg-gray-600'}`}></div>
                    <span className={`text-sm ${pkg.limits.priority_support ? 'text-gray-300' : 'text-gray-500'}`}>Priority Support</span>
                  </div>
                </div>
              </div>

              <div className="flex items-center justify-between mb-4 p-3 bg-gray-800/50 rounded-lg">
                <span className="text-gray-400 text-sm">Active Subscribers:</span>
                <span className="text-white font-bold text-lg">{pkg.subscribers}</span>
              </div>

              <div className="flex gap-2">
                <button
                  onClick={() => handleEditPackage(pkg)}
                  className="flex-1 bg-gray-800 hover:bg-gray-700 text-white py-2 rounded-lg flex items-center justify-center gap-2 transition-colors"
                >
                  <Edit size={16} />
                  Edit
                </button>
                <button
                  onClick={() => handleDeletePackage(pkg)}
                  className="flex-1 bg-red-600 hover:bg-red-700 text-white py-2 rounded-lg flex items-center justify-center gap-2 transition-colors"
                >
                  <Trash2 size={16} />
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* Create Modal */}
        {showCreateModal && (
          <div className="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50 p-4">
            <div className="bg-gray-900 border border-gray-800 rounded-xl max-w-3xl w-full max-h-[90vh] overflow-y-auto">
              <div className="flex justify-between items-center p-6 border-b border-gray-800 sticky top-0 bg-gray-900 z-10">
                <h2 className="text-2xl font-bold text-white">Create New Package</h2>
                <button onClick={() => { setShowCreateModal(false); resetForm(); }} className="text-gray-400 hover:text-white">
                  <X size={24} />
                </button>
              </div>
              
              <div className="p-6">
                <div className="grid grid-cols-2 gap-6 mb-6">
                  <div>
                    <label className="text-gray-400 text-sm block mb-2">Package Name *</label>
                    <input
                      type="text"
                      value={formData.name}
                      onChange={(e) => setFormData({...formData, name: e.target.value})}
                      className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500"
                      placeholder="e.g., Premium"
                    />
                  </div>
                  <div>
                    <label className="text-gray-400 text-sm block mb-2">Price (VNĐ) *</label>
                    <input
                      type="number"
                      value={formData.price}
                      onChange={(e) => setFormData({...formData, price: parseInt(e.target.value) || ''})}
                      className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500"
                      placeholder="299000"
                    />
                  </div>
                </div>

                <div className="mb-6">
                  <label className="text-gray-400 text-sm block mb-2">Description</label>
                  <textarea
                    value={formData.description}
                    onChange={(e) => setFormData({...formData, description: e.target.value})}
                    className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500 h-20 resize-none"
                    placeholder="Gói phổ biến cho doanh nghiệp vừa..."
                  />
                </div>

                <div className="mb-6">
                  <label className="text-gray-400 text-sm block mb-2">Limits (JSON) *</label>
                  <p className="text-xs text-gray-500 mb-2">
                    💡 Nhập -1 cho unlimited. Ví dụ: {`{"emails_per_month": 25000, "campaigns": 50, "contacts": 10000}`}
                  </p>
                  <textarea
                    value={limitsJson}
                    onChange={(e) => handleLimitsJsonChange(e.target.value)}
                    className={`w-full bg-gray-800 border ${jsonError ? 'border-red-500' : 'border-gray-700'} text-gray-300 px-4 py-3 rounded-lg focus:outline-none focus:border-blue-500 font-mono text-sm h-40 resize-none`}
                    placeholder='{"emails_per_month": 25000, "campaigns": 50, "contacts": 10000, "email_templates": 20, "custom_domains": 2, "api_access": true, "priority_support": false}'
                  />
                  {jsonError && <p className="text-red-400 text-sm mt-2">{jsonError}</p>}
                </div>
              </div>

              <div className="flex gap-3 p-6 border-t border-gray-800">
                <button
                  onClick={handleSaveCreate}
                  disabled={!!jsonError}
                  className="flex-1 bg-blue-600 hover:bg-blue-700 disabled:bg-gray-600 disabled:cursor-not-allowed text-white px-6 py-3 rounded-lg font-medium flex items-center justify-center gap-2 transition-colors"
                >
                  <Save size={20} />
                  Create Package
                </button>
                <button
                  onClick={() => { setShowCreateModal(false); resetForm(); }}
                  className="flex-1 bg-gray-800 hover:bg-gray-700 text-white px-6 py-3 rounded-lg font-medium transition-colors"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}

        {/* Edit Modal */}
        {showEditModal && selectedPackage && (
          <div className="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50 p-4">
            <div className="bg-gray-900 border border-gray-800 rounded-xl max-w-3xl w-full max-h-[90vh] overflow-y-auto">
              <div className="flex justify-between items-center p-6 border-b border-gray-800 sticky top-0 bg-gray-900 z-10">
                <h2 className="text-2xl font-bold text-white">Edit Package: {selectedPackage.name}</h2>
                <button onClick={() => { setShowEditModal(false); resetForm(); }} className="text-gray-400 hover:text-white">
                  <X size={24} />
                </button>
              </div>
              
              <div className="p-6">
                <div className="grid grid-cols-2 gap-6 mb-6">
                  <div>
                    <label className="text-gray-400 text-sm block mb-2">Package Name *</label>
                    <input
                      type="text"
                      value={formData.name}
                      onChange={(e) => setFormData({...formData, name: e.target.value})}
                      className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500"
                    />
                  </div>
                  <div>
                    <label className="text-gray-400 text-sm block mb-2">Price (VNĐ) *</label>
                    <input
                      type="number"
                      value={formData.price}
                      onChange={(e) => setFormData({...formData, price: parseInt(e.target.value) || ''})}
                      className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500"
                    />
                  </div>
                </div>

                <div className="mb-6">
                  <label className="text-gray-400 text-sm block mb-2">Description</label>
                  <textarea
                    value={formData.description}
                    onChange={(e) => setFormData({...formData, description: e.target.value})}
                    className="w-full bg-gray-800 border border-gray-700 text-white px-4 py-2 rounded-lg focus:outline-none focus:border-blue-500 h-20 resize-none"
                  />
                </div>

                <div className="mb-6">
                  <label className="text-gray-400 text-sm block mb-2">Limits (JSON) *</label>
                  <textarea
                    value={limitsJson}
                    onChange={(e) => handleLimitsJsonChange(e.target.value)}
                    className={`w-full bg-gray-800 border ${jsonError ? 'border-red-500' : 'border-gray-700'} text-gray-300 px-4 py-3 rounded-lg focus:outline-none focus:border-blue-500 font-mono text-sm h-40 resize-none`}
                  />
                  {jsonError && <p className="text-red-400 text-sm mt-2">{jsonError}</p>}
                </div>
              </div>

              <div className="flex gap-3 p-6 border-t border-gray-800">
                <button
                  onClick={handleSaveEdit}
                  disabled={!!jsonError}
                  className="flex-1 bg-blue-600 hover:bg-blue-700 disabled:bg-gray-600 disabled:cursor-not-allowed text-white px-6 py-3 rounded-lg font-medium flex items-center justify-center gap-2 transition-colors"
                >
                  <Save size={20} />
                  Update Package
                </button>
                <button
                  onClick={() => { setShowEditModal(false); resetForm(); }}
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

export default Package;