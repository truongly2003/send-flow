import { Calendar, Users, CheckCircle, Eye, Send } from "lucide-react";
const CampaignDetail = () => {
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
            <p className="text-2xl font-bold">
              {selectedCampaign.sentCount.toLocaleString()}
            </p>
          </div>

          <div className="bg-gray-800/50 rounded-lg p-4">
            <div className="flex items-center gap-2 mb-2">
              <Users className="text-purple-400" size={20} />
              <span className="text-sm text-gray-400">Người nhận</span>
            </div>
            <p className="text-2xl font-bold">
              {selectedCampaign.recipientCount.toLocaleString()}
            </p>
          </div>

          <div className="bg-gray-800/50 rounded-lg p-4">
            <div className="flex items-center gap-2 mb-2">
              <CheckCircle className="text-green-400" size={20} />
              <span className="text-sm text-gray-400">Tỷ lệ thành công</span>
            </div>
            <p className="text-2xl font-bold text-green-400">
              {selectedCampaign.successRate}%
            </p>
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
  </div>;
};
export default CampaignDetail;
