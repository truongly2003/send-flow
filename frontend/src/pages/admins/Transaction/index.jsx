import  { useState, useEffect } from 'react';
import { Search, DollarSign, CheckCircle, XCircle, Clock, RefreshCw } from 'lucide-react';

function Transaction() {
  const [transactions, setTransactions] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [filterStatus, setFilterStatus] = useState('all');

  useEffect(() => {
    const mockTransactions = [
      {
        id: 'TXN001',
        userId: 1,
        userName: 'John Doe',
        userEmail: 'john.doe@example.com',
        package: 'Premium',
        amount: 299000,
        paymentStatus: 'completed',
        paymentMethod: 'VNPay',
        transactionDate: '2025-10-28 14:30:00',
        description: 'Premium subscription - Monthly'
      },
      {
        id: 'TXN002',
        userId: 2,
        userName: 'Admin User',
        userEmail: 'admin@company.com',
        package: 'Enterprise',
        amount: 999000,
        paymentStatus: 'completed',
        paymentMethod: 'Bank Transfer',
        transactionDate: '2025-10-27 10:15:00',
        description: 'Enterprise subscription - Monthly'
      },
      {
        id: 'TXN003',
        userId: 3,
        userName: 'Jane Smith',
        userEmail: 'jane.smith@startup.com',
        package: 'Basic',
        amount: 99000,
        paymentStatus: 'failed',
        paymentMethod: 'VNPay',
        transactionDate: '2025-10-29 16:45:00',
        description: 'Basic subscription - Monthly',
        failureReason: 'Insufficient balance'
      },
      {
        id: 'TXN004',
        userId: 4,
        userName: 'Marketing Team',
        userEmail: 'marketing@company.vn',
        package: 'Premium',
        amount: 299000,
        paymentStatus: 'pending',
        paymentMethod: 'Bank Transfer',
        transactionDate: '2025-10-30 09:20:00',
        description: 'Premium subscription - Monthly'
      },
      {
        id: 'TXN005',
        userId: 1,
        userName: 'John Doe',
        userEmail: 'john.doe@example.com',
        package: 'Premium',
        amount: 299000,
        paymentStatus: 'failed',
        paymentMethod: 'Credit Card',
        transactionDate: '2025-10-26 11:00:00',
        description: 'Premium subscription - Monthly',
        failureReason: 'Card declined'
      }
    ];
    setTransactions(mockTransactions);
  }, []);

  const handleRefund = (transaction) => {
    if (confirm(`Bạn có chắc muốn refund giao dịch ${transaction.id}?\nSố tiền: ${transaction.amount.toLocaleString()} VNĐ`)) {
      setTransactions(transactions.map(t => 
        t.id === transaction.id 
          ? { ...t, paymentStatus: 'refunded', refundDate: new Date().toISOString().split('T')[0] }
          : t
      ));
      alert('Đã gửi yêu cầu refund thành công!');
    }
  };

  const filteredTransactions = transactions.filter(transaction => {
    const matchesStatus = filterStatus === 'all' || transaction.paymentStatus === filterStatus;
    const matchesSearch = 
      transaction.id.toLowerCase().includes(searchQuery.toLowerCase()) ||
      transaction.userName.toLowerCase().includes(searchQuery.toLowerCase()) ||
      transaction.userEmail.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  const getStatusColor = (status) => {
    switch(status) {
      case 'completed': return 'text-green-400 bg-green-400/10';
      case 'failed': return 'text-red-400 bg-red-400/10';
      case 'pending': return 'text-yellow-400 bg-yellow-400/10';
      case 'refunded': return 'text-blue-400 bg-blue-400/10';
      default: return 'text-gray-400 bg-gray-400/10';
    }
  };

  const getStatusIcon = (status) => {
    switch(status) {
      case 'completed': return <CheckCircle size={16} />;
      case 'failed': return <XCircle size={16} />;
      case 'pending': return <Clock size={16} />;
      case 'refunded': return <RefreshCw size={16} />;
      default: return null;
    }
  };

  const totalRevenue = transactions
    .filter(t => t.paymentStatus === 'completed')
    .reduce((sum, t) => sum + t.amount, 0);

  const totalFailed = transactions.filter(t => t.paymentStatus === 'failed').length;
  const totalPending = transactions.filter(t => t.paymentStatus === 'pending').length;

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-6">
          <h1 className="text-3xl font-bold mb-2">Transactions</h1>
          <p className="text-gray-400">Quản lý giao dịch và xử lý refund</p>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-6">
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
            <div className="flex items-center gap-3 mb-2">
              <DollarSign className="text-green-400" size={24} />
              <span className="text-gray-400 text-sm">Total Revenue</span>
            </div>
            <p className="text-2xl font-bold text-white">{totalRevenue.toLocaleString()} đ</p>
          </div>
          
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
            <div className="flex items-center gap-3 mb-2">
              <CheckCircle className="text-green-400" size={24} />
              <span className="text-gray-400 text-sm">Completed</span>
            </div>
            <p className="text-2xl font-bold text-white">
              {transactions.filter(t => t.paymentStatus === 'completed').length}
            </p>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
            <div className="flex items-center gap-3 mb-2">
              <XCircle className="text-red-400" size={24} />
              <span className="text-gray-400 text-sm">Failed</span>
            </div>
            <p className="text-2xl font-bold text-white">{totalFailed}</p>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
            <div className="flex items-center gap-3 mb-2">
              <Clock className="text-yellow-400" size={24} />
              <span className="text-gray-400 text-sm">Pending</span>
            </div>
            <p className="text-2xl font-bold text-white">{totalPending}</p>
          </div>
        </div>

        {/* Filters */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-4 mb-6">
          <div className="flex items-center gap-4">
            {/* Search */}
            <div className="flex-1 relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Tìm kiếm theo ID, user name hoặc email..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-10 pr-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Status Filter */}
            <div className="flex items-center gap-2 bg-gray-800 rounded-lg p-1">
              <button
                onClick={() => setFilterStatus('all')}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterStatus === 'all' ? 'bg-blue-600 text-white' : 'text-gray-400 hover:text-white'
                }`}
              >
                Tất cả
              </button>
              <button
                onClick={() => setFilterStatus('completed')}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterStatus === 'completed' ? 'bg-green-600 text-white' : 'text-gray-400 hover:text-white'
                }`}
              >
                Completed
              </button>
              <button
                onClick={() => setFilterStatus('failed')}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterStatus === 'failed' ? 'bg-red-600 text-white' : 'text-gray-400 hover:text-white'
                }`}
              >
                Failed
              </button>
              <button
                onClick={() => setFilterStatus('pending')}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterStatus === 'pending' ? 'bg-yellow-600 text-white' : 'text-gray-400 hover:text-white'
                }`}
              >
                Pending
              </button>
            </div>
          </div>
        </div>

        {/* Transactions Table */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-800/50">
                <tr>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Transaction ID</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">User</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Package</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Amount</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Payment Method</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Status</th>
                  <th className="px-6 py-4 text-left text-sm font-semibold text-gray-300">Date</th>
                  <th className="px-6 py-4 text-right text-sm font-semibold text-gray-300">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-800">
                {filteredTransactions.map(transaction => (
                  <tr key={transaction.id} className="hover:bg-gray-800/30 transition-colors">
                    <td className="px-6 py-4">
                      <p className="font-mono text-sm text-blue-400">{transaction.id}</p>
                    </td>
                    <td className="px-6 py-4">
                      <div>
                        <p className="font-medium text-white">{transaction.userName}</p>
                        <p className="text-sm text-gray-400">{transaction.userEmail}</p>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <p className="text-white">{transaction.package}</p>
                    </td>
                    <td className="px-6 py-4">
                      <p className="font-semibold text-white">{transaction.amount.toLocaleString()} đ</p>
                    </td>
                    <td className="px-6 py-4">
                      <p className="text-gray-400">{transaction.paymentMethod}</p>
                    </td>
                    <td className="px-6 py-4">
                      <div>
                        <span className={`inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-medium ${getStatusColor(transaction.paymentStatus)}`}>
                          {getStatusIcon(transaction.paymentStatus)}
                          {transaction.paymentStatus}
                        </span>
                        {transaction.failureReason && (
                          <p className="text-xs text-red-400 mt-1">{transaction.failureReason}</p>
                        )}
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <p className="text-sm text-gray-400">{transaction.transactionDate}</p>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center justify-end gap-2">
                        {transaction.paymentStatus === 'failed' && (
                          <button
                            onClick={() => handleRefund(transaction)}
                            className="px-3 py-1.5 bg-red-600 hover:bg-red-700 rounded-lg transition-colors text-sm font-medium flex items-center gap-2"
                          >
                            <RefreshCw size={14} />
                            Refund
                          </button>
                        )}
                        {transaction.paymentStatus === 'completed' && (
                          <span className="text-xs text-gray-500">No actions</span>
                        )}
                        {transaction.paymentStatus === 'pending' && (
                          <span className="text-xs text-yellow-400">Waiting...</span>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {filteredTransactions.length === 0 && (
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-12 text-center mt-6">
            <DollarSign className="mx-auto mb-4 text-gray-600" size={48} />
            <p className="text-gray-400">Không tìm thấy giao dịch nào</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default Transaction;