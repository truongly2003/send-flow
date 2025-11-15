import { useState } from "react";

function SmtpConfig() {
  const [smtpData, setSmtpData] = useState({
    host: "smtp.gmail.com",
    port: "587",
    username: "your-email@gmail.com",
    password: "",
    encryption: "TLS",
    senderName: "BlogHub System",
    replyTo: "noreply@bloghub.com"
  });

  const [showPassword, setShowPassword] = useState(false);

  const handleChange = (e) => {
    setSmtpData({
      ...smtpData,
      [e.target.name]: e.target.value
    });
  };

  const handleTest = () => {
    alert("Đang kiểm tra kết nối SMTP...");
  };

  const handleSave = () => {
    alert("Đã lưu cấu hình SMTP!");
  };

  const handleCancel = () => {
    alert("Đã hủy thay đổi");
  };

  return (
    <div>
      {/* Warning Alert */}
      <div className="mb-6 bg-yellow-50 border border-yellow-200 rounded-lg p-4 flex gap-3">
        <div className="text-yellow-600 mt-0.5">⚠</div>
        <div>
          <div className="font-semibold text-yellow-800 mb-1">Lưu ý quan trọng</div>
          <div className="text-sm text-yellow-700">
            Thông tin SMTP sẽ được sử dụng để gửi email thông báo, xác thực và các email hệ thống khác. Vui lòng đảm bảo thông tin chính xác.
          </div>
        </div>
      </div>

      <div className="space-y-5">
        {/* Row 1: SMTP Host and Port */}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-300">SMTP Host</label>
            <input
              type="text"
              name="host"
              value={smtpData.host}
              onChange={handleChange}
              placeholder="smtp.gmail.com"
              className="w-full px-3 py-2 bg-gray-900 border border-gray-700 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-200"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-300">Port</label>
            <input
              type="text"
              name="port"
              value={smtpData.port}
              onChange={handleChange}
              placeholder="587"
              className="w-full px-3 py-2 bg-gray-900 border border-gray-700 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-200"
            />
          </div>
        </div>

        {/* Row 2: Username and Password */}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-300">Username</label>
            <input
              type="text"
              name="username"
              value={smtpData.username}
              onChange={handleChange}
              placeholder="your-email@gmail.com"
              className="w-full px-3 py-2 bg-gray-900 border border-gray-700 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-200"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-300">Password</label>
            <div className="relative">
              <input
                type={showPassword ? "text" : "password"}
                name="password"
                value={smtpData.password}
                onChange={handleChange}
                placeholder="••••••••••••"
                className="w-full px-3 py-2 bg-gray-900 border border-gray-700 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-200 pr-10"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-200"
              >
                {showPassword ? "👁️" : "👁️‍🗨️"}
              </button>
            </div>
          </div>
        </div>

        {/* Row 3: Encryption and Sender Name */}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-300">Mã hóa</label>
            <select
              name="encryption"
              value={smtpData.encryption}
              onChange={handleChange}
              className="w-full px-3 py-2 bg-gray-900 border border-gray-700 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-200"
            >
              <option value="TLS">TLS</option>
              <option value="SSL">SSL</option>
              <option value="None">None</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-300">Tên người gửi</label>
            <input
              type="text"
              name="senderName"
              value={smtpData.senderName}
              onChange={handleChange}
              placeholder="BlogHub System"
              className="w-full px-3 py-2 bg-gray-900 border border-gray-700 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-200"
            />
          </div>
        </div>

        {/* Row 4: Reply To Email */}
        <div>
          <label className="block text-sm font-medium mb-2 text-gray-300">Email người gửi</label>
          <input
            type="text"
            name="replyTo"
            value={smtpData.replyTo}
            onChange={handleChange}
            placeholder="noreply@bloghub.com"
            className="w-full px-3 py-2 bg-gray-900 border border-gray-700 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-200"
          />
        </div>

        {/* Instructions */}
        <div className="bg-gray-900 border border-gray-700 rounded-lg p-4 mt-6">
          <h3 className="font-semibold text-gray-200 mb-3">Hướng dẫn cấu hình</h3>
          <ul className="space-y-2 text-sm text-gray-400">
            <li className="flex gap-2">
              <span className="text-purple-400">•</span>
              <span><strong className="text-gray-300">Gmail:</strong> Sử dụng smtp.gmail.com, port 587, TLS. Cần bật xác thực 2 bước và tạo App Password.</span>
            </li>
            <li className="flex gap-2">
              <span className="text-purple-400">•</span>
              <span><strong className="text-gray-300">Outlook:</strong> Sử dụng smtp-mail.outlook.com, port 587, TLS.</span>
            </li>
            <li className="flex gap-2">
              <span className="text-purple-400">•</span>
              <span><strong className="text-gray-300">Yahoo:</strong> Sử dụng smtp.mail.yahoo.com, port 587 hoặc 465, TLS/SSL.</span>
            </li>
            <li className="flex gap-2">
              <span className="text-purple-400">•</span>
              <span><strong className="text-gray-300">Custom SMTP:</strong> Liên hệ nhà cung cấp hosting để biết thông tin cấu hình chi tiết.</span>
            </li>
          </ul>
        </div>

        {/* Action Buttons */}
        <div className="flex gap-3 pt-4">
          <button
            onClick={handleTest}
            className="px-5 py-2.5 bg-green-600 hover:bg-green-700 text-white rounded-lg font-medium transition-colors flex items-center gap-2"
          >
            <span>✓</span>
            Kiểm tra kết nối
          </button>
          <button
            onClick={handleSave}
            className="px-5 py-2.5 bg-purple-600 hover:bg-purple-700 text-white rounded-lg font-medium transition-colors"
          >
            Lưu cấu hình
          </button>
          <button
            onClick={handleCancel}
            className="px-5 py-2.5 bg-gray-700 hover:bg-gray-600 text-white rounded-lg font-medium transition-colors"
          >
            Hủy bỏ
          </button>
        </div>
      </div>
    </div>
  );
}
export default SmtpConfig