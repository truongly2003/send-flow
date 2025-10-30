import { useState } from "react";
import {
  Bell,
  Check,
  Trash2,
  Filter,
  Mail,
  Package,
  AlertCircle,
  CheckCircle,
  XCircle,
  Info,
  Calendar,
  Settings,
  MoreVertical,
} from "lucide-react";

function Notification() {
  const [filter, setFilter] = useState("all"); // all, unread, read
  const [notifications, setNotifications] = useState([
    {
      id: 1,
      type: "success",
      title: "Chiến dịch gửi thành công",
      message: "Chiến dịch 'Summer Sale 2025' đã gửi thành công đến 1,180 người nhận.",
      timestamp: "2025-10-30 09:30",
      isRead: false,
      icon: CheckCircle,
      color: "green",
    },
    {
      id: 2,
      type: "warning",
      title: "Hạn mức sắp hết",
      message: "Bạn đã sử dụng 85% hạn mức email. Vui lòng nâng cấp gói để tiếp tục.",
      timestamp: "2025-10-29 14:20",
      isRead: false,
      icon: AlertCircle,
      color: "yellow",
    },
    {
      id: 3,
      type: "info",
      title: "Gói dịch vụ sắp hết hạn",
      message: "Gói Professional của bạn sẽ hết hạn vào 31/12/2025. Gia hạn ngay để không bị gián đoạn.",
      timestamp: "2025-10-29 10:15",
      isRead: true,
      icon: Calendar,
      color: "blue",
    },
    {
      id: 4,
      type: "error",
      title: "Chiến dịch gửi thất bại",
      message: "Chiến dịch 'Product Launch' gặp lỗi. Vui lòng kiểm tra template và thử lại.",
      timestamp: "2025-10-28 16:45",
      isRead: true,
      icon: XCircle,
      color: "red",
    },
    {
      id: 5,
      type: "success",
      title: "Thanh toán thành công",
      message: "Thanh toán 699,000đ cho gói Professional đã được xác nhận.",
      timestamp: "2025-10-28 11:20",
      isRead: true,
      icon: Package,
      color: "green",
    },
    {
      id: 6,
      type: "info",
      title: "Template mới được tạo",
      message: "Template 'Welcome Email' đã được tạo thành công.",
      timestamp: "2025-10-27 15:30",
      isRead: true,
      icon: Mail,
      color: "blue",
    },
    {
      id: 7,
      type: "warning",
      title: "Liên hệ không hợp lệ",
      message: "25 địa chỉ email trong danh sách 'Newsletter' không hợp lệ. Vui lòng kiểm tra lại.",
      timestamp: "2025-10-27 09:00",
      isRead: true,
      icon: AlertCircle,
      color: "yellow",
    },
  ]);

  const [showMenu, setShowMenu] = useState(null);

  const getColorClasses = (color) => {
    const colors = {
      green: {
        bg: "bg-green-400/10",
        text: "text-green-400",
        border: "border-green-400/20",
      },
      yellow: {
        bg: "bg-yellow-400/10",
        text: "text-yellow-400",
        border: "border-yellow-400/20",
      },
      blue: {
        bg: "bg-blue-400/10",
        text: "text-blue-400",
        border: "border-blue-400/20",
      },
      red: {
        bg: "bg-red-400/10",
        text: "text-red-400",
        border: "border-red-400/20",
      },
    };
    return colors[color];
  };

  const handleMarkAsRead = (id) => {
    setNotifications(
      notifications.map((notif) =>
        notif.id === id ? { ...notif, isRead: true } : notif
      )
    );
    setShowMenu(null);
  };

  const handleMarkAsUnread = (id) => {
    setNotifications(
      notifications.map((notif) =>
        notif.id === id ? { ...notif, isRead: false } : notif
      )
    );
    setShowMenu(null);
  };

  const handleDelete = (id) => {
    if (confirm("Bạn có chắc muốn xóa thông báo này?")) {
      setNotifications(notifications.filter((notif) => notif.id !== id));
    }
    setShowMenu(null);
  };

  const handleMarkAllAsRead = () => {
    setNotifications(notifications.map((notif) => ({ ...notif, isRead: true })));
  };

  const handleDeleteAll = () => {
    if (confirm("Bạn có chắc muốn xóa tất cả thông báo?")) {
      setNotifications([]);
    }
  };

  const filteredNotifications = notifications.filter((notif) => {
    if (filter === "unread") return !notif.isRead;
    if (filter === "read") return notif.isRead;
    return true;
  });

  const unreadCount = notifications.filter((n) => !n.isRead).length;

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-4xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <div>
            <h1 className="text-3xl font-bold mb-2 flex items-center gap-3">
              <Bell size={32} />
              Thông báo
              {unreadCount > 0 && (
                <span className="bg-red-500 text-white text-sm px-2.5 py-0.5 rounded-full">
                  {unreadCount}
                </span>
              )}
            </h1>
            <p className="text-gray-400">Quản lý thông báo và cập nhật</p>
          </div>
          <button
            onClick={handleMarkAllAsRead}
            disabled={unreadCount === 0}
            className="px-4 py-2 bg-blue-600 hover:bg-blue-700 disabled:bg-gray-700 disabled:cursor-not-allowed rounded-lg transition-colors flex items-center gap-2"
          >
            <Check size={18} />
            Đánh dấu đã đọc tất cả
          </button>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-3 gap-4 mb-6">
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-4">
            <div className="flex items-center gap-2 mb-2">
              <Bell className="text-blue-400" size={20} />
              <span className="text-sm text-gray-400">Tổng thông báo</span>
            </div>
            <p className="text-2xl font-bold">{notifications.length}</p>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-4">
            <div className="flex items-center gap-2 mb-2">
              <Mail className="text-yellow-400" size={20} />
              <span className="text-sm text-gray-400">Chưa đọc</span>
            </div>
            <p className="text-2xl font-bold text-yellow-400">{unreadCount}</p>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-4">
            <div className="flex items-center gap-2 mb-2">
              <CheckCircle className="text-green-400" size={20} />
              <span className="text-sm text-gray-400">Đã đọc</span>
            </div>
            <p className="text-2xl font-bold text-green-400">
              {notifications.length - unreadCount}
            </p>
          </div>
        </div>

        {/* Filters & Actions */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-4 mb-6">
          <div className="flex items-center justify-between">
            {/* Filter Tabs */}
            <div className="flex items-center gap-2 bg-gray-800 rounded-lg p-1">
              <button
                onClick={() => setFilter("all")}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filter === "all"
                    ? "bg-blue-600 text-white"
                    : "text-gray-400 hover:text-white"
                }`}
              >
                Tất cả
              </button>
              <button
                onClick={() => setFilter("unread")}
                className={`px-4 py-2 rounded-md transition-colors flex items-center gap-2 ${
                  filter === "unread"
                    ? "bg-blue-600 text-white"
                    : "text-gray-400 hover:text-white"
                }`}
              >
                Chưa đọc
                {unreadCount > 0 && (
                  <span className="bg-red-500 text-white text-xs px-1.5 py-0.5 rounded-full">
                    {unreadCount}
                  </span>
                )}
              </button>
              <button
                onClick={() => setFilter("read")}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filter === "read"
                    ? "bg-blue-600 text-white"
                    : "text-gray-400 hover:text-white"
                }`}
              >
                Đã đọc
              </button>
            </div>

            {/* Actions */}
            <div className="flex items-center gap-2">
              <button
                onClick={handleDeleteAll}
                disabled={notifications.length === 0}
                className="px-4 py-2 text-red-400 hover:bg-red-400/10 disabled:text-gray-600 disabled:cursor-not-allowed rounded-lg transition-colors flex items-center gap-2"
              >
                <Trash2 size={18} />
                Xóa tất cả
              </button>
            </div>
          </div>
        </div>

        {/* Notifications List */}
        <div className="space-y-3">
          {filteredNotifications.map((notif) => {
            const Icon = notif.icon;
            const colors = getColorClasses(notif.color);

            return (
              <div
                key={notif.id}
                className={`bg-gray-900 border rounded-xl p-5 hover:border-gray-700 transition-all ${
                  notif.isRead ? "border-gray-800 opacity-60" : "border-gray-700"
                }`}
              >
                <div className="flex items-start gap-4">
                  {/* Icon */}
                  <div className={`${colors.bg} p-3 rounded-lg flex-shrink-0`}>
                    <Icon className={colors.text} size={24} />
                  </div>

                  {/* Content */}
                  <div className="flex-1 min-w-0">
                    <div className="flex items-start justify-between mb-2">
                      <h3 className="font-semibold text-lg">{notif.title}</h3>
                      <div className="flex items-center gap-2">
                        {!notif.isRead && (
                          <span className="w-2 h-2 bg-blue-500 rounded-full"></span>
                        )}
                        <div className="relative">
                          <button
                            onClick={() =>
                              setShowMenu(showMenu === notif.id ? null : notif.id)
                            }
                            className="p-1 hover:bg-gray-800 rounded transition-colors"
                          >
                            <MoreVertical size={18} className="text-gray-400" />
                          </button>

                          {/* Dropdown Menu */}
                          {showMenu === notif.id && (
                            <div className="absolute right-0 mt-1 bg-gray-800 border border-gray-700 rounded-lg shadow-xl z-10 min-w-[160px]">
                              {!notif.isRead ? (
                                <button
                                  onClick={() => handleMarkAsRead(notif.id)}
                                  className="w-full px-4 py-2 text-left hover:bg-gray-700 transition-colors flex items-center gap-2 text-sm"
                                >
                                  <Check size={16} />
                                  Đánh dấu đã đọc
                                </button>
                              ) : (
                                <button
                                  onClick={() => handleMarkAsUnread(notif.id)}
                                  className="w-full px-4 py-2 text-left hover:bg-gray-700 transition-colors flex items-center gap-2 text-sm"
                                >
                                  <Mail size={16} />
                                  Đánh dấu chưa đọc
                                </button>
                              )}
                              <button
                                onClick={() => handleDelete(notif.id)}
                                className="w-full px-4 py-2 text-left hover:bg-gray-700 transition-colors flex items-center gap-2 text-sm text-red-400"
                              >
                                <Trash2 size={16} />
                                Xóa
                              </button>
                            </div>
                          )}
                        </div>
                      </div>
                    </div>

                    <p className="text-gray-400 text-sm mb-3">{notif.message}</p>

                    <div className="flex items-center gap-2 text-xs text-gray-500">
                      <Calendar size={14} />
                      <span>{notif.timestamp}</span>
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>

        {/* Empty State */}
        {filteredNotifications.length === 0 && (
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-12 text-center">
            <Bell className="mx-auto mb-4 text-gray-600" size={48} />
            <p className="text-gray-400 mb-2">Không có thông báo nào</p>
            <p className="text-sm text-gray-500">
              {filter === "unread"
                ? "Bạn đã đọc tất cả thông báo"
                : filter === "read"
                ? "Chưa có thông báo nào được đọc"
                : "Chưa có thông báo nào"}
            </p>
          </div>
        )}

        {/* Settings Link */}
        <div className="mt-6 text-center">
          <button className="text-sm text-gray-400 hover:text-white transition-colors flex items-center gap-2 mx-auto">
            <Settings size={16} />
            Cài đặt thông báo
          </button>
        </div>
      </div>
    </div>
  );
}

export default Notification;