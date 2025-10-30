import { useState } from "react";
import {
  Mail,
  Send,
  Users,
  Package,
  TrendingUp,
  Calendar,
  Plus,
  CheckCircle,
  Clock,
  BarChart3,
  ArrowUpRight,
} from "lucide-react";

function Dashboard() {
  // Sample data - thay bằng API calls thực tế từ backend
  const [dashboardData] = useState({
    subscription: {
      packageName: "Professional",
      expiryDate: "2025-12-31",
      daysRemaining: 62,
      status: "active", // active, expiring, expired
    },
    quota: {
      total: 10000,
      used: 6450,
      remaining: 3550,
      percentage: 64.5,
    },
    stats: {
      totalEmailsSent: 6450,
      totalCampaigns: 23,
      totalContacts: 1250,
      successRate: 94.2,
    },
    recentCampaigns: [
      { id: 1, name: "Summer Sale 2025", sent: 1200, rate: 95.5, date: "2025-10-28" },
      { id: 2, name: "Product Launch", sent: 850, rate: 92.3, date: "2025-10-25" },
      { id: 3, name: "Newsletter Oct", sent: 980, rate: 96.1, date: "2025-10-20" },
    ],
  });

  const getStatusColor = (status) => {
    switch (status) {
      case "active":
        return "text-green-400 bg-green-400/10";
      case "expiring":
        return "text-yellow-400 bg-yellow-400/10";
      case "expired":
        return "text-red-400 bg-red-400/10";
      default:
        return "text-gray-400 bg-gray-400/10";
    }
  };

  const getQuotaColor = (percentage) => {
    if (percentage >= 80) return "bg-red-500";
    if (percentage >= 60) return "bg-yellow-500";
    return "bg-green-500";
  };

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold mb-2">Dashboard</h1>
          <p className="text-gray-400">Tổng quan hoạt động email marketing của bạn</p>
        </div>

        {/* Subscription Status Card */}
        <div className="bg-gradient-to-br from-blue-900/30 to-purple-900/30 border border-blue-500/20 rounded-xl p-6 mb-6">
          <div className="flex items-start justify-between mb-4">
            <div>
              <div className="flex items-center gap-2 mb-2">
                <Package className="text-blue-400" size={24} />
                <h2 className="text-xl font-semibold">Gói dịch vụ hiện tại</h2>
              </div>
              <p className="text-2xl font-bold text-blue-400">{dashboardData.subscription.packageName}</p>
            </div>
            <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(dashboardData.subscription.status)}`}>
              {dashboardData.subscription.status === "active" ? "Đang hoạt động" : "Hết hạn"}
            </span>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div className="flex items-center gap-2 text-gray-300">
              <Calendar size={18} />
              <span className="text-sm">Hết hạn: {dashboardData.subscription.expiryDate}</span>
            </div>
            <div className="flex items-center gap-2 text-gray-300">
              <Clock size={18} />
              <span className="text-sm">Còn {dashboardData.subscription.daysRemaining} ngày</span>
            </div>
          </div>
        </div>

        {/* Quota Card */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 mb-6">
          <div className="flex items-center justify-between mb-4">
            <div className="flex items-center gap-2">
              <Mail className="text-purple-400" size={24} />
              <h2 className="text-xl font-semibold">Hạn mức email</h2>
            </div>
            <span className="text-2xl font-bold text-purple-400">
              {dashboardData.quota.remaining.toLocaleString()}
            </span>
          </div>
          <div className="space-y-2">
            <div className="flex justify-between text-sm text-gray-400">
              <span>Đã sử dụng: {dashboardData.quota.used.toLocaleString()}</span>
              <span>Tổng: {dashboardData.quota.total.toLocaleString()}</span>
            </div>
            <div className="w-full bg-gray-800 rounded-full h-3 overflow-hidden">
              <div
                className={`h-full ${getQuotaColor(dashboardData.quota.percentage)} transition-all duration-500`}
                style={{ width: `${dashboardData.quota.percentage}%` }}
              />
            </div>
            {dashboardData.quota.percentage >= 80 && (
              <p className="text-xs text-yellow-400">⚠️ Hạn mức sắp hết, vui lòng nâng cấp gói dịch vụ</p>
            )}
          </div>
        </div>

        {/* Quick Actions */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          <button className="bg-blue-600 hover:bg-blue-700 transition-colors rounded-xl p-4 flex items-center justify-between group">
            <div className="flex items-center gap-3">
              <div className="bg-blue-500/20 p-2 rounded-lg">
                <Plus size={24} />
              </div>
              <span className="font-semibold">Tạo chiến dịch mới</span>
            </div>
            <ArrowUpRight className="group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform" size={20} />
          </button>
          
          <button className="bg-gray-800 hover:bg-gray-700 transition-colors rounded-xl p-4 flex items-center justify-between group border border-gray-700">
            <div className="flex items-center gap-3">
              <div className="bg-green-500/20 p-2 rounded-lg">
                <Users className="text-green-400" size={24} />
              </div>
              <span className="font-semibold">Thêm liên hệ</span>
            </div>
            <ArrowUpRight className="group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform" size={20} />
          </button>
          
          <button className="bg-gray-800 hover:bg-gray-700 transition-colors rounded-xl p-4 flex items-center justify-between group border border-gray-700">
            <div className="flex items-center gap-3">
              <div className="bg-purple-500/20 p-2 rounded-lg">
                <BarChart3 className="text-purple-400" size={24} />
              </div>
              <span className="font-semibold">Xem báo cáo</span>
            </div>
            <ArrowUpRight className="group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform" size={20} />
          </button>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <div className="bg-blue-500/10 p-2 rounded-lg">
                <Send className="text-blue-400" size={20} />
              </div>
              <TrendingUp className="text-green-400" size={18} />
            </div>
            <p className="text-gray-400 text-sm mb-1">Tổng email đã gửi</p>
            <p className="text-3xl font-bold">{dashboardData.stats.totalEmailsSent.toLocaleString()}</p>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <div className="bg-purple-500/10 p-2 rounded-lg">
                <Mail className="text-purple-400" size={20} />
              </div>
              <TrendingUp className="text-green-400" size={18} />
            </div>
            <p className="text-gray-400 text-sm mb-1">Tổng chiến dịch</p>
            <p className="text-3xl font-bold">{dashboardData.stats.totalCampaigns}</p>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <div className="bg-green-500/10 p-2 rounded-lg">
                <Users className="text-green-400" size={20} />
              </div>
              <TrendingUp className="text-green-400" size={18} />
            </div>
            <p className="text-gray-400 text-sm mb-1">Tổng liên hệ</p>
            <p className="text-3xl font-bold">{dashboardData.stats.totalContacts.toLocaleString()}</p>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-5">
            <div className="flex items-center justify-between mb-3">
              <div className="bg-yellow-500/10 p-2 rounded-lg">
                <CheckCircle className="text-yellow-400" size={20} />
              </div>
              <TrendingUp className="text-green-400" size={18} />
            </div>
            <p className="text-gray-400 text-sm mb-1">Tỷ lệ thành công</p>
            <p className="text-3xl font-bold">{dashboardData.stats.successRate}%</p>
          </div>
        </div>

        {/* Recent Campaigns */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
          <h2 className="text-xl font-semibold mb-4">Chiến dịch gần đây</h2>
          <div className="space-y-3">
            {dashboardData.recentCampaigns.map((campaign) => (
              <div
                key={campaign.id}
                className="flex items-center justify-between p-4 bg-gray-800/50 rounded-lg hover:bg-gray-800 transition-colors cursor-pointer"
              >
                <div className="flex items-center gap-4">
                  <div className="bg-blue-500/10 p-3 rounded-lg">
                    <Mail className="text-blue-400" size={20} />
                  </div>
                  <div>
                    <p className="font-medium">{campaign.name}</p>
                    <p className="text-sm text-gray-400">{campaign.date}</p>
                  </div>
                </div>
                <div className="flex items-center gap-6 text-sm">
                  <div className="text-right">
                    <p className="text-gray-400">Đã gửi</p>
                    <p className="font-semibold">{campaign.sent.toLocaleString()}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-gray-400">Tỷ lệ</p>
                    <p className="font-semibold text-green-400">{campaign.rate}%</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;