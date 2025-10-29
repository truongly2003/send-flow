import { useState } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import {
  Home,
  Send,
  Users,
  FileText,
  Package,
  LogOut,
  X,
  Settings,
  BarChart,
  Shield,
  UserCog,
  Mail,
  Bell,
} from "lucide-react";

// Sidebar Component
function Sidebar({ role = "user", isOpen=true, onClose }) {
  const [activeItem, setActiveItem] = useState("Thống kê");
  const navigate = useNavigate();
  // Menu items cho người dùng
  const userMenuItems = [
    { icon: Home, label: "Thống kê", to: "/dashboard" },
    { icon: Send, label: "Chiến dịch", to: "/campaign" },
    { icon: Users, label: "Liên hệ", to: "/contact" },
    { icon: FileText, label: "Mẫu tin", to: "/template" },
    { icon: Package, label: "Gói dịch vụ", to: "/package" },
  ];

  // Menu items cho quản trị viên
  const adminMenuItems = [
    { icon: Home, label: "Thống kê", to: "/admin/dashboard/" },
    { icon: UserCog, label: "Người dùng", to: "/admin/user" },
    { icon: Package, label: "Gói dịch vụ", to: "/admin/package" },
    { icon: Shield, label: "Giao dịch", to: "/admin/transaction" },
    { icon: Settings, label: "Giám sát", to: "/admin/sendlog" },
  ];

  const menuItems = role === "admin" ? adminMenuItems : userMenuItems;

  const handleItemClick = (item) => {
    setActiveItem(item.label);
    navigate(item.to);
  };

  return (
    <div
      className={`fixed left-0 top-0 h-full w-80 bg-black text-white transition-transform duration-300 ease-in-out z-50 flex flex-col ${
        isOpen ? "translate-x-0" : "-translate-x-full"
      }`}
    >
      {/* Header */}
      <div className="flex items-center justify-between p-6 border-b border-gray-700">
        <h1 className="text-xl font-bold">
          {role === "admin" ? "Admin Panel" : "ZaloMarketing"}
        </h1>
        <button
          onClick={onClose}
          className="p-1 hover:bg-gray-800 rounded transition-colors"
        >
          <X size={24} />
        </button>
      </div>

      {/* Menu Items */}
      <nav className="flex-1 overflow-y-auto py-4">
        {menuItems.map((item) => {
          const Icon = item.icon;
          const isActive = activeItem === item.label;

          return (
            <button
              key={item.label}
              onClick={() => handleItemClick(item)}
              className={`w-full flex items-center gap-4 px-6 py-3 transition-colors ${
                isActive
                  ? "bg-gray-800 border-l-4 border-blue-500"
                  : "hover:bg-gray-800 border-l-4 border-transparent"
              }`}
            >
              <Icon size={20} />
              <span className="text-sm font-medium">{item.label}</span>
            </button>
          );
        })}
      </nav>

      {/* Footer - Logout */}
      <div className="border-t border-gray-700">
        <button
          onClick={() => console.log("Logout clicked")}
          className="w-full flex items-center gap-4 px-6 py-4 hover:bg-gray-800 transition-colors text-red-400"
        >
          <LogOut size={20} />
          <span className="text-sm font-medium">Đăng xuất</span>
        </button>
      </div>
    </div>
  );
}
export default Sidebar;
Sidebar.propTypes = {
  role: PropTypes.oneOf(["user", "admin"]),
  isOpen: PropTypes.bool,
  onClose: PropTypes.func,
};
