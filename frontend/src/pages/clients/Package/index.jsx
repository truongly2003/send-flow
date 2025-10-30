import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Package,
  Check,
  X,
  Zap,
  Crown,
  Rocket,
  Mail,
  Users,
  Calendar,
  ChevronRight,
  Star,
} from "lucide-react";

function PackagePage() {
  const navigate = useNavigate();

  // Current subscription
  const [currentSubscription] = useState({
    packageId: 2,
    packageName: "Professional",
    expiryDate: "2025-12-31",
    status: "active",
  });

  // Available packages
  const packages = [
    {
      id: 1,
      name: "Starter",
      price: 299000,
      duration: "1 tháng",
      icon: Zap,
      color: "blue",
      features: [
        { text: "1,000 email/tháng", included: true },
        { text: "3 chiến dịch/tháng", included: true },
        { text: "500 liên hệ", included: true },
        { text: "2 template", included: true },
        { text: "Báo cáo cơ bản", included: true },
        { text: "Hỗ trợ email", included: true },
        { text: "API access", included: false },
        { text: "Hỗ trợ ưu tiên", included: false },
      ],
      popular: false,
    },
    {
      id: 2,
      name: "Professional",
      price: 699000,
      duration: "1 tháng",
      icon: Crown,
      color: "purple",
      features: [
        { text: "10,000 email/tháng", included: true },
        { text: "Không giới hạn chiến dịch", included: true },
        { text: "5,000 liên hệ", included: true },
        { text: "Không giới hạn template", included: true },
        { text: "Báo cáo chi tiết", included: true },
        { text: "Hỗ trợ email + chat", included: true },
        { text: "API access", included: true },
        { text: "Hỗ trợ ưu tiên", included: false },
      ],
      popular: true,
    },
    {
      id: 3,
      name: "Enterprise",
      price: 1499000,
      duration: "1 tháng",
      icon: Rocket,
      color: "orange",
      features: [
        { text: "50,000 email/tháng", included: true },
        { text: "Không giới hạn chiến dịch", included: true },
        { text: "Không giới hạn liên hệ", included: true },
        { text: "Không giới hạn template", included: true },
        { text: "Báo cáo nâng cao + Analytics", included: true },
        { text: "Hỗ trợ 24/7", included: true },
        { text: "API access", included: true },
        { text: "Hỗ trợ ưu tiên", included: true },
      ],
      popular: false,
    },
  ];

  const handleSelectPackage = () => {
    // Navigate to payment page
    navigate("/payment");
  };

  const getColorClasses = (color) => {
    const colors = {
      blue: {
        bg: "from-blue-900/30 to-blue-800/20",
        border: "border-blue-500/30",
        button: "bg-blue-600 hover:bg-blue-700",
        icon: "text-blue-400 bg-blue-400/10",
        badge: "bg-blue-400/10 text-blue-400",
      },
      purple: {
        bg: "from-purple-900/30 to-purple-800/20",
        border: "border-purple-500/30",
        button: "bg-purple-600 hover:bg-purple-700",
        icon: "text-purple-400 bg-purple-400/10",
        badge: "bg-purple-400/10 text-purple-400",
      },
      orange: {
        bg: "from-orange-900/30 to-orange-800/20",
        border: "border-orange-500/30",
        button: "bg-orange-600 hover:bg-orange-700",
        icon: "text-orange-400 bg-orange-400/10",
        badge: "bg-orange-400/10 text-orange-400",
      },
    };
    return colors[color];
  };

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold mb-4">Chọn gói dịch vụ phù hợp</h1>
          <p className="text-gray-400 text-lg">
            Nâng cấp để mở khóa nhiều tính năng hơn
          </p>
        </div>

        {/* Current Subscription Info */}
        {currentSubscription.status === "active" && (
          <div className="bg-gradient-to-r from-green-900/20 to-green-800/10 border border-green-500/20 rounded-xl p-6 mb-12">
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-4">
                <div className="bg-green-400/10 p-3 rounded-lg">
                  <Package className="text-green-400" size={24} />
                </div>
                <div>
                  <p className="text-sm text-gray-400 mb-1">Gói hiện tại</p>
                  <p className="text-2xl font-bold text-green-400">
                    {currentSubscription.packageName}
                  </p>
                </div>
              </div>
              <div className="text-right">
                <p className="text-sm text-gray-400 mb-1">Hết hạn</p>
                <p className="text-lg font-semibold flex items-center gap-2">
                  <Calendar size={18} />
                  {currentSubscription.expiryDate}
                </p>
              </div>
            </div>
          </div>
        )}

        {/* Packages Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-12">
          {packages.map((pkg) => {
            const Icon = pkg.icon;
            const colors = getColorClasses(pkg.color);
            const isCurrent = currentSubscription.packageId === pkg.id;

            return (
              <div
                key={pkg.id}
                className={`relative bg-gradient-to-b ${colors.bg} border ${colors.border} rounded-2xl p-8 ${
                  pkg.popular ? "ring-2 ring-purple-500/50 scale-105" : ""
                } transition-all hover:scale-105`}
              >
                {/* Popular Badge */}
                {pkg.popular && (
                  <div className="absolute -top-4 left-1/2 transform -translate-x-1/2">
                    <div className="bg-purple-600 text-white px-4 py-1 rounded-full text-sm font-semibold flex items-center gap-1">
                      <Star size={14} fill="currentColor" />
                      Phổ biến nhất
                    </div>
                  </div>
                )}

                {/* Current Badge */}
                {isCurrent && (
                  <div className="absolute top-4 right-4">
                    <div className="bg-green-400/10 text-green-400 px-3 py-1 rounded-full text-xs font-semibold">
                      Đang dùng
                    </div>
                  </div>
                )}

                {/* Package Header */}
                <div className="text-center mb-8">
                  <div className={`inline-flex p-4 rounded-2xl ${colors.icon} mb-4`}>
                    <Icon size={32} />
                  </div>
                  <h3 className="text-2xl font-bold mb-2">{pkg.name}</h3>
                  <div className="flex items-baseline justify-center gap-1 mb-2">
                    <span className="text-4xl font-bold">
                      {pkg.price.toLocaleString("vi-VN")}
                    </span>
                    <span className="text-gray-400">đ</span>
                  </div>
                  <p className="text-sm text-gray-400">{pkg.duration}</p>
                </div>

                {/* Features */}
                <div className="space-y-3 mb-8">
                  {pkg.features.map((feature, index) => (
                    <div key={index} className="flex items-start gap-3">
                      {feature.included ? (
                        <Check className="text-green-400 flex-shrink-0 mt-0.5" size={18} />
                      ) : (
                        <X className="text-gray-600 flex-shrink-0 mt-0.5" size={18} />
                      )}
                      <span
                        className={`text-sm ${
                          feature.included ? "text-gray-300" : "text-gray-600"
                        }`}
                      >
                        {feature.text}
                      </span>
                    </div>
                  ))}
                </div>

                {/* CTA Button */}
                <button
                  onClick={() => handleSelectPackage()}
                  disabled={isCurrent}
                  className={`w-full py-3 rounded-lg font-semibold transition-colors flex items-center justify-center gap-2 ${
                    isCurrent
                      ? "bg-gray-700 cursor-not-allowed"
                      : colors.button
                  }`}
                >
                  {isCurrent ? (
                    "Gói hiện tại"
                  ) : (
                    <>
                      {currentSubscription.packageId > pkg.id ? "Hạ cấp" : "Nâng cấp"}
                      <ChevronRight size={18} />
                    </>
                  )}
                </button>
              </div>
            );
          })}
        </div>

        {/* Features Comparison */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-8">
          <h2 className="text-2xl font-bold mb-6 text-center">So sánh tính năng</h2>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-gray-800">
                  <th className="text-left py-4 px-4 text-gray-400 font-medium">
                    Tính năng
                  </th>
                  {packages.map((pkg) => (
                    <th key={pkg.id} className="text-center py-4 px-4">
                      <div className="font-bold">{pkg.name}</div>
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody>
                <tr className="border-b border-gray-800">
                  <td className="py-4 px-4 flex items-center gap-2">
                    <Mail size={18} className="text-gray-400" />
                    <span>Email/tháng</span>
                  </td>
                  <td className="text-center py-4 px-4">1,000</td>
                  <td className="text-center py-4 px-4 text-purple-400 font-semibold">
                    10,000
                  </td>
                  <td className="text-center py-4 px-4 text-orange-400 font-semibold">
                    50,000
                  </td>
                </tr>
                <tr className="border-b border-gray-800">
                  <td className="py-4 px-4 flex items-center gap-2">
                    <Users size={18} className="text-gray-400" />
                    <span>Liên hệ</span>
                  </td>
                  <td className="text-center py-4 px-4">500</td>
                  <td className="text-center py-4 px-4 text-purple-400 font-semibold">
                    5,000
                  </td>
                  <td className="text-center py-4 px-4 text-orange-400 font-semibold">
                    Không giới hạn
                  </td>
                </tr>
                <tr className="border-b border-gray-800">
                  <td className="py-4 px-4">API Access</td>
                  <td className="text-center py-4 px-4">
                    <X className="text-gray-600 inline" size={20} />
                  </td>
                  <td className="text-center py-4 px-4">
                    <Check className="text-green-400 inline" size={20} />
                  </td>
                  <td className="text-center py-4 px-4">
                    <Check className="text-green-400 inline" size={20} />
                  </td>
                </tr>
                <tr>
                  <td className="py-4 px-4">Hỗ trợ ưu tiên</td>
                  <td className="text-center py-4 px-4">
                    <X className="text-gray-600 inline" size={20} />
                  </td>
                  <td className="text-center py-4 px-4">
                    <X className="text-gray-600 inline" size={20} />
                  </td>
                  <td className="text-center py-4 px-4">
                    <Check className="text-green-400 inline" size={20} />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        {/* FAQ or Additional Info */}
        <div className="mt-12 text-center text-gray-400 text-sm">
          <p>Có câu hỏi? Liên hệ với chúng tôi qua email: support@example.com</p>
        </div>
      </div>
    </div>
  );
}

export default PackagePage;