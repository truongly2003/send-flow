import  { useState, useEffect } from 'react';
import { Users, Mail, DollarSign, TrendingUp, Activity, CheckCircle, XCircle } from 'lucide-react';

function Dashboard() {
  const [stats, setStats] = useState({
    totalUsers: 0,
    totalCampaigns: 0,
    totalRevenue: 0,
    emailsSent: 0,
    activeSubscriptions: 0,
    successRate: 0
  });

  const [recentTransactions, setRecentTransactions] = useState([]);
  const [campaignStats, setCampaignStats] = useState([]);

  useEffect(() => {
    // Mock data - thay thế bằng API calls thực tế
    setStats({
      totalUsers: 196,
      totalCampaigns: 88,
      totalRevenue: 47650000,
      emailsSent: 234500,
      activeSubscriptions: 178,
      successRate: 98.5
    });

    setRecentTransactions([
      {
        id: 'TXN001',
        userName: 'John Doe',
        package: 'Premium',
        amount: 299000,
        status: 'completed',
        date: '2025-10-28 14:30'
      },
      {
        id: 'TXN002',
        userName: 'Admin User',
        package: 'Enterprise',
        amount: 999000,
        status: 'completed',
        date: '2025-10-27 10:15'
      },
      {
        id: 'TXN003',
        userName: 'Jane Smith',
        package: 'Basic',
        amount: 99000,
        status: 'failed',
        date: '2025-10-29 16:45'
      },
      {
        id: 'TXN004',
        userName: 'Marketing Team',
        package: 'Premium',
        amount: 299000,
        status: 'pending',
        date: '2025-10-30 09:20'
      }
    ]);

    setCampaignStats([
      { month: 'Jun', campaigns: 45, emails: 120000 },
      { month: 'Jul', campaigns: 52, emails: 145000 },
      { month: 'Aug', campaigns: 68, emails: 178000 },
      { month: 'Sep', campaigns: 75, emails: 195000 },
      { month: 'Oct', campaigns: 88, emails: 234500 }
    ]);
  }, []);

  const getStatusColor = (status) => {
    switch(status) {
      case 'completed': return 'text-green-400 bg-green-400/10';
      case 'failed': return 'text-red-400 bg-red-400/10';
      case 'pending': return 'text-yellow-400 bg-yellow-400/10';
      default: return 'text-gray-400 bg-gray-400/10';
    }
  };

  const getStatusIcon = (status) => {
    switch(status) {
      case 'completed': return <CheckCircle size={14} />;
      case 'failed': return <XCircle size={14} />;
      default: return null;
    }
  };

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-6">
          <h1 className="text-3xl font-bold mb-2">Dashboard</h1>
          <p className="text-gray-400">Tổng quan hệ thống gửi email hàng loạt</p>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
          {/* Total Users */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors">
            <div className="flex items-center justify-between mb-4">
              <div className="p-3 bg-blue-500/10 rounded-lg">
                <Users className="text-blue-400" size={24} />
              </div>
              <span className="text-xs text-gray-400">+12% this month</span>
            </div>
            <p className="text-gray-400 text-sm mb-1">Total Users</p>
            <p className="text-3xl font-bold text-white">{stats.totalUsers}</p>
          </div>

          {/* Total Campaigns */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors">
            <div className="flex items-center justify-between mb-4">
              <div className="p-3 bg-purple-500/10 rounded-lg">
                <Mail className="text-purple-400" size={24} />
              </div>
              <span className="text-xs text-gray-400">+8% this month</span>
            </div>
            <p className="text-gray-400 text-sm mb-1">Total Campaigns</p>
            <p className="text-3xl font-bold text-white">{stats.totalCampaigns}</p>
          </div>

          {/* Total Revenue */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors">
            <div className="flex items-center justify-between mb-4">
              <div className="p-3 bg-green-500/10 rounded-lg">
                <DollarSign className="text-green-400" size={24} />
              </div>
              <span className="text-xs text-gray-400">+23% this month</span>
            </div>
            <p className="text-gray-400 text-sm mb-1">Total Revenue</p>
            <p className="text-3xl font-bold text-white">{stats.totalRevenue.toLocaleString()} đ</p>
          </div>

          {/* Emails Sent */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors">
            <div className="flex items-center justify-between mb-4">
              <div className="p-3 bg-cyan-500/10 rounded-lg">
                <Activity className="text-cyan-400" size={24} />
              </div>
              <span className="text-xs text-gray-400">+15% this month</span>
            </div>
            <p className="text-gray-400 text-sm mb-1">Emails Sent</p>
            <p className="text-3xl font-bold text-white">{stats.emailsSent.toLocaleString()}</p>
          </div>

          {/* Active Subscriptions */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors">
            <div className="flex items-center justify-between mb-4">
              <div className="p-3 bg-orange-500/10 rounded-lg">
                <TrendingUp className="text-orange-400" size={24} />
              </div>
              <span className="text-xs text-gray-400">+5% this month</span>
            </div>
            <p className="text-gray-400 text-sm mb-1">Active Subscriptions</p>
            <p className="text-3xl font-bold text-white">{stats.activeSubscriptions}</p>
          </div>

          {/* Success Rate */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors">
            <div className="flex items-center justify-between mb-4">
              <div className="p-3 bg-green-500/10 rounded-lg">
                <CheckCircle className="text-green-400" size={24} />
              </div>
              <span className="text-xs text-gray-400">+2% this month</span>
            </div>
            <p className="text-gray-400 text-sm mb-1">Success Rate</p>
            <p className="text-3xl font-bold text-white">{stats.successRate}%</p>
          </div>
        </div>

        {/* Charts & Tables Row */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
          {/* Campaign Growth Chart */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
            <h2 className="text-xl font-bold text-white mb-4">Campaign Growth</h2>
            <div className="space-y-4">
              {campaignStats.map((stat, index) => (
                <div key={index}>
                  <div className="flex items-center justify-between mb-2">
                    <span className="text-gray-400 text-sm">{stat.month}</span>
                    <div className="flex items-center gap-4">
                      <span className="text-white text-sm font-medium">{stat.campaigns} campaigns</span>
                      <span className="text-gray-400 text-sm">{stat.emails.toLocaleString()} emails</span>
                    </div>
                  </div>
                  <div className="w-full bg-gray-800 rounded-full h-2">
                    <div 
                      className="bg-gradient-to-r from-blue-500 to-purple-500 h-2 rounded-full transition-all duration-300"
                      style={{ width: `${(stat.campaigns / 100) * 100}%` }}
                    ></div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Revenue by Package */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
            <h2 className="text-xl font-bold text-white mb-4">Revenue by Package</h2>
            <div className="space-y-4">
              <div className="flex items-center justify-between p-4 bg-gray-800/50 rounded-lg">
                <div>
                  <p className="text-white font-medium">Enterprise</p>
                  <p className="text-gray-400 text-sm">23 subscribers</p>
                </div>
                <div className="text-right">
                  <p className="text-white font-bold">22,977,000 đ</p>
                  <p className="text-green-400 text-sm">48.2%</p>
                </div>
              </div>
              <div className="flex items-center justify-between p-4 bg-gray-800/50 rounded-lg">
                <div>
                  <p className="text-white font-medium">Premium</p>
                  <p className="text-gray-400 text-sm">128 subscribers</p>
                </div>
                <div className="text-right">
                  <p className="text-white font-bold">38,272,000 đ</p>
                  <p className="text-green-400 text-sm">80.3%</p>
                </div>
              </div>
              <div className="flex items-center justify-between p-4 bg-gray-800/50 rounded-lg">
                <div>
                  <p className="text-white font-medium">Basic</p>
                  <p className="text-gray-400 text-sm">45 subscribers</p>
                </div>
                <div className="text-right">
                  <p className="text-white font-bold">4,455,000 đ</p>
                  <p className="text-green-400 text-sm">9.4%</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Recent Transactions */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl overflow-hidden">
          <div className="p-6 border-b border-gray-800">
            <h2 className="text-xl font-bold text-white">Recent Transactions</h2>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-800/50">
                <tr>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Transaction ID</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">User</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Package</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Amount</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Status</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Date</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-800">
                {recentTransactions.map(transaction => (
                  <tr key={transaction.id} className="hover:bg-gray-800/30 transition-colors">
                    <td className="px-6 py-4">
                      <p className="font-mono text-sm text-blue-400">{transaction.id}</p>
                    </td>
                    <td className="px-6 py-4">
                      <p className="text-white">{transaction.userName}</p>
                    </td>
                    <td className="px-6 py-4">
                      <p className="text-white">{transaction.package}</p>
                    </td>
                    <td className="px-6 py-4">
                      <p className="font-semibold text-white">{transaction.amount.toLocaleString()} đ</p>
                    </td>
                    <td className="px-6 py-4">
                      <span className={`inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-medium ${getStatusColor(transaction.status)}`}>
                        {getStatusIcon(transaction.status)}
                        {transaction.status}
                      </span>
                    </td>
                    <td className="px-6 py-4">
                      <p className="text-sm text-gray-400">{transaction.date}</p>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;