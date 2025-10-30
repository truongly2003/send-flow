import { useState } from "react";
import {
  Plus,
  Search,
  Filter,
  Calendar,
  Mail,
  Users,
  CheckCircle,
  Clock,
  XCircle,
  Eye,
  Send,
  FileText,
} from "lucide-react";

function Campaign() {
  const [activeTab, setActiveTab] = useState("list"); // list, create, detail
  const [filterStatus, setFilterStatus] = useState("all");
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedCampaign, setSelectedCampaign] = useState(null);

  // Sample data - thay bằng API
  const [campaigns] = useState([
    {
      id: 1,
      name: "Summer Sale 2025",
      template: "Promo Template 1",
      status: "completed",
      scheduleTime: "2025-10-28 09:00",
      sentCount: 1180,
      recipientCount: 1200,
      successRate: 98.3,
      contactList: "VIP Customers",
    },
    {
      id: 2,
      name: "Product Launch",
      template: "Announcement Template",
      status: "scheduled",
      scheduleTime: "2025-11-05 14:00",
      sentCount: 0,
      recipientCount: 850,
      successRate: 0,
      contactList: "All Subscribers",
    },
    {
      id: 3,
      name: "Newsletter Oct",
      template: "Newsletter Template",
      status: "sending",
      scheduleTime: "2025-10-30 10:00",
      sentCount: 650,
      recipientCount: 980,
      successRate: 66.3,
      contactList: "Newsletter List",
    },
  ]);

  const [templates] = useState([
    { id: 1, name: "Promo Template 1", type: "email" },
    { id: 2, name: "Announcement Template", type: "zalo" },
    { id: 3, name: "Newsletter Template", type: "email" },
  ]);

  const [contactLists] = useState([
    { id: 1, name: "VIP Customers", count: 1200 },
    { id: 2, name: "All Subscribers", count: 850 },
    { id: 3, name: "Newsletter List", count: 980 },
  ]);

  const [newCampaign, setNewCampaign] = useState({
    name: "",
    templateId: "",
    messageContent: "",
    scheduleTime: "",
    contactListId: "",
  });

  const getStatusBadge = (status) => {
    const styles = {
      completed: "bg-green-400/10 text-green-400 border-green-400/20",
      scheduled: "bg-blue-400/10 text-blue-400 border-blue-400/20",
      sending: "bg-yellow-400/10 text-yellow-400 border-yellow-400/20",
      failed: "bg-red-400/10 text-red-400 border-red-400/20",
    };
    const icons = {
      completed: CheckCircle,
      scheduled: Clock,
      sending: Send,
      failed: XCircle,
    };
    const labels = {
      completed: "Hoàn thành",
      scheduled: "Đã lên lịch",
      sending: "Đang gửi",
      failed: "Thất bại",
    };
    const Icon = icons[status];
    return (
      <span className={`px-3 py-1 rounded-full text-xs font-medium border flex items-center gap-1 w-fit ${styles[status]}`}>
        <Icon size={14} />
        {labels[status]}
      </span>
    );
  };

  const filteredCampaigns = campaigns.filter((campaign) => {
    const matchesStatus = filterStatus === "all" || campaign.status === filterStatus;
    const matchesSearch = campaign.name.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  const handleCreateCampaign = () => {
    console.log("Creating campaign:", newCampaign);
    // API call here
    setActiveTab("list");
  };

  const handleViewDetail = (campaign) => {
    setSelectedCampaign(campaign);
    setActiveTab("detail");
  };

  // Create Campaign Form
  if (activeTab === "create") {
    return (
      <div className="min-h-screen bg-gray-950 text-white p-6">
        <div className="max-w-4xl mx-auto">
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-3xl font-bold mb-2">Tạo chiến dịch mới</h1>
              <p className="text-gray-400">Thiết lập chiến dịch email marketing</p>
            </div>
            <button
              onClick={() => setActiveTab("list")}
              className="px-4 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
            >
              Hủy
            </button>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 space-y-6">
            {/* Campaign Name */}
            <div>
              <label className="block text-sm font-medium mb-2">Tên chiến dịch</label>
              <input
                type="text"
                value={newCampaign.name}
                onChange={(e) => setNewCampaign({ ...newCampaign, name: e.target.value })}
                placeholder="Ví dụ: Summer Sale 2025"
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Template Selection */}
            <div>
              <label className="block text-sm font-medium mb-2">Chọn template</label>
              <select
                value={newCampaign.templateId}
                onChange={(e) => setNewCampaign({ ...newCampaign, templateId: e.target.value })}
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              >
                <option value="">-- Chọn template --</option>
                {templates.map((template) => (
                  <option key={template.id} value={template.id}>
                    {template.name} ({template.type})
                  </option>
                ))}
              </select>
            </div>

            {/* Message Content */}
            <div>
              <label className="block text-sm font-medium mb-2">Nội dung tin nhắn</label>
              <textarea
                value={newCampaign.messageContent}
                onChange={(e) => setNewCampaign({ ...newCampaign, messageContent: e.target.value })}
                placeholder="Nhập nội dung email..."
                rows={6}
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500 resize-none"
              />
            </div>

            {/* Contact List */}
            <div>
              <label className="block text-sm font-medium mb-2">Danh sách liên hệ</label>
              <select
                value={newCampaign.contactListId}
                onChange={(e) => setNewCampaign({ ...newCampaign, contactListId: e.target.value })}
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              >
                <option value="">-- Chọn danh sách --</option>
                {contactLists.map((list) => (
                  <option key={list.id} value={list.id}>
                    {list.name} ({list.count} liên hệ)
                  </option>
                ))}
              </select>
            </div>

            {/* Schedule Time */}
            <div>
              <label className="block text-sm font-medium mb-2">Thời gian lên lịch</label>
              <input
                type="datetime-local"
                value={newCampaign.scheduleTime}
                onChange={(e) => setNewCampaign({ ...newCampaign, scheduleTime: e.target.value })}
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Submit Button */}
            <button
              onClick={handleCreateCampaign}
              className="w-full py-3 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors flex items-center justify-center gap-2"
            >
              <Calendar size={20} />
              Lên lịch gửi
            </button>
          </div>
        </div>
      </div>
    );
  }

  // Campaign Detail
  if (activeTab === "detail" && selectedCampaign) {
    return (
      <div className="min-h-screen bg-gray-950 text-white p-6">
        <div className="max-w-6xl mx-auto">
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-3xl font-bold mb-2">{selectedCampaign.name}</h1>
              <p className="text-gray-400">Chi tiết chiến dịch</p>
            </div>
            <button
              onClick={() => setActiveTab("list")}
              className="px-4 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
            >
              Quay lại
            </button>
          </div>

          {/* Status Overview */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 mb-6">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center gap-4">
                {getStatusBadge(selectedCampaign.status)}
                <div className="text-sm text-gray-400">
                  <Calendar className="inline mr-1" size={16} />
                  {selectedCampaign.scheduleTime}
                </div>
              </div>
              <button className="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg transition-colors flex items-center gap-2">
                <Eye size={18} />
                Xem Send Log
              </button>
            </div>

            <div className="grid grid-cols-3 gap-4">
              <div className="bg-gray-800/50 rounded-lg p-4">
                <div className="flex items-center gap-2 mb-2">
                  <Send className="text-blue-400" size={20} />
                  <span className="text-sm text-gray-400">Đã gửi</span>
                </div>
                <p className="text-2xl font-bold">{selectedCampaign.sentCount.toLocaleString()}</p>
              </div>

              <div className="bg-gray-800/50 rounded-lg p-4">
                <div className="flex items-center gap-2 mb-2">
                  <Users className="text-purple-400" size={20} />
                  <span className="text-sm text-gray-400">Người nhận</span>
                </div>
                <p className="text-2xl font-bold">{selectedCampaign.recipientCount.toLocaleString()}</p>
              </div>

              <div className="bg-gray-800/50 rounded-lg p-4">
                <div className="flex items-center gap-2 mb-2">
                  <CheckCircle className="text-green-400" size={20} />
                  <span className="text-sm text-gray-400">Tỷ lệ thành công</span>
                </div>
                <p className="text-2xl font-bold text-green-400">{selectedCampaign.successRate}%</p>
              </div>
            </div>
          </div>

          {/* Chart Placeholder */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 mb-6">
            <h2 className="text-xl font-semibold mb-4">Biểu đồ thống kê</h2>
            <div className="h-64 bg-gray-800/30 rounded-lg flex items-center justify-center">
              <p className="text-gray-500">Chart: Sent Count vs Recipient Count</p>
            </div>
          </div>

          {/* Campaign Info */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
            <h2 className="text-xl font-semibold mb-4">Thông tin chiến dịch</h2>
            <div className="space-y-3 text-sm">
              <div className="flex justify-between py-2 border-b border-gray-800">
                <span className="text-gray-400">Template:</span>
                <span className="font-medium">{selectedCampaign.template}</span>
              </div>
              <div className="flex justify-between py-2 border-b border-gray-800">
                <span className="text-gray-400">Danh sách liên hệ:</span>
                <span className="font-medium">{selectedCampaign.contactList}</span>
              </div>
              <div className="flex justify-between py-2">
                <span className="text-gray-400">Thời gian lên lịch:</span>
                <span className="font-medium">{selectedCampaign.scheduleTime}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  // Campaign List
  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <div>
            <h1 className="text-3xl font-bold mb-2">Chiến dịch</h1>
            <p className="text-gray-400">Quản lý các chiến dịch email marketing</p>
          </div>
          <button
            onClick={() => setActiveTab("create")}
            className="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors flex items-center gap-2"
          >
            <Plus size={20} />
            Tạo chiến dịch mới
          </button>
        </div>

        {/* Filters */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-4 mb-6">
          <div className="flex items-center gap-4">
            {/* Search */}
            <div className="flex-1 relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Tìm kiếm chiến dịch..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-10 pr-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Status Filter */}
            <div className="flex items-center gap-2">
              <Filter size={20} className="text-gray-400" />
              <select
                value={filterStatus}
                onChange={(e) => setFilterStatus(e.target.value)}
                className="px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              >
                <option value="all">Tất cả</option>
                <option value="completed">Hoàn thành</option>
                <option value="scheduled">Đã lên lịch</option>
                <option value="sending">Đang gửi</option>
                <option value="failed">Thất bại</option>
              </select>
            </div>
          </div>
        </div>

        {/* Campaign List */}
        <div className="space-y-4">
          {filteredCampaigns.map((campaign) => (
            <div
              key={campaign.id}
              className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors"
            >
              <div className="flex items-start justify-between mb-4">
                <div className="flex-1">
                  <h3 className="text-xl font-semibold mb-2">{campaign.name}</h3>
                  <div className="flex items-center gap-4 text-sm text-gray-400">
                    <span className="flex items-center gap-1">
                      <FileText size={16} />
                      {campaign.template}
                    </span>
                    <span className="flex items-center gap-1">
                      <Users size={16} />
                      {campaign.contactList}
                    </span>
                    <span className="flex items-center gap-1">
                      <Calendar size={16} />
                      {campaign.scheduleTime}
                    </span>
                  </div>
                </div>
                <div className="flex items-center gap-3">
                  {getStatusBadge(campaign.status)}
                  <button
                    onClick={() => handleViewDetail(campaign)}
                    className="px-4 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors flex items-center gap-2"
                  >
                    <Eye size={18} />
                    Chi tiết
                  </button>
                </div>
              </div>

              {/* Stats */}
              <div className="grid grid-cols-3 gap-4 pt-4 border-t border-gray-800">
                <div>
                  <p className="text-sm text-gray-400 mb-1">Đã gửi</p>
                  <p className="text-lg font-semibold">{campaign.sentCount.toLocaleString()}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-400 mb-1">Người nhận</p>
                  <p className="text-lg font-semibold">{campaign.recipientCount.toLocaleString()}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-400 mb-1">Tỷ lệ thành công</p>
                  <p className="text-lg font-semibold text-green-400">{campaign.successRate}%</p>
                </div>
              </div>
            </div>
          ))}
        </div>

        {filteredCampaigns.length === 0 && (
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-12 text-center">
            <Mail className="mx-auto mb-4 text-gray-600" size={48} />
            <p className="text-gray-400">Không tìm thấy chiến dịch nào</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default Campaign;