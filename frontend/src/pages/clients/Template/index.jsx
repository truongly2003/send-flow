import { useState } from "react";
import {
  Plus,
  Search,
  Edit,
  Trash2,
  Copy,
  Mail,
  MessageSquare,
  FileText,
  X,
  Save,
  Eye,
} from "lucide-react";

function Template() {
  const [activeView, setActiveView] = useState("list"); // list, create, edit, preview
  const [selectedTemplate, setSelectedTemplate] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [filterType, setFilterType] = useState("all");

  const [templates, setTemplates] = useState([
    {
      id: 1,
      name: "Promo Template 1",
      type: "email",
      subject: "🎉 Flash Sale - Giảm giá 50%",
      body: "Chào {name},\n\nChúng tôi có chương trình Flash Sale đặc biệt với ưu đãi giảm giá lên đến 50%!\n\nThời gian: Từ ngày 01/11 đến 05/11\n\nTruy cập ngay: https://example.com/sale\n\nTrân trọng,\nĐội ngũ Marketing",
      createdAt: "2025-10-20",
      usageCount: 15,
    },
    {
      id: 2,
      name: "Announcement Template",
      type: "zalo",
      subject: "Thông báo sản phẩm mới",
      body: "Xin chào {name}!\n\nChúng tôi vừa ra mắt sản phẩm mới với nhiều tính năng ưu việt.\n\nXem ngay tại: https://example.com/new-product\n\nCảm ơn bạn!",
      createdAt: "2025-10-15",
      usageCount: 8,
    },
    {
      id: 3,
      name: "Newsletter Template",
      type: "email",
      subject: "📰 Bản tin hàng tháng - Tháng 10",
      body: "Kính gửi {name},\n\nDưới đây là những tin tức nổi bật trong tháng:\n\n1. Sự kiện A\n2. Khuyến mãi B\n3. Cập nhật C\n\nĐọc thêm: https://example.com/newsletter\n\nTrân trọng!",
      createdAt: "2025-10-10",
      usageCount: 22,
    },
  ]);

  const [formData, setFormData] = useState({
    name: "",
    type: "email",
    subject: "",
    body: "",
  });

  const handleCreate = () => {
    const newTemplate = {
      id: Date.now(),
      ...formData,
      createdAt: new Date().toISOString().split("T")[0],
      usageCount: 0,
    };
    setTemplates([...templates, newTemplate]);
    setFormData({ name: "", type: "email", subject: "", body: "" });
    setActiveView("list");
  };

  const handleEdit = (template) => {
    setSelectedTemplate(template);
    setFormData(template);
    setActiveView("edit");
  };

  const handleUpdate = () => {
    setTemplates(
      templates.map((t) =>
        t.id === selectedTemplate.id ? { ...t, ...formData } : t
      )
    );
    setActiveView("list");
    setSelectedTemplate(null);
  };

  const handleClone = (template) => {
    const clonedTemplate = {
      ...template,
      id: Date.now(),
      name: `${template.name} (Copy)`,
      createdAt: new Date().toISOString().split("T")[0],
      usageCount: 0,
    };
    setTemplates([...templates, clonedTemplate]);
  };

  const handleDelete = (id) => {
    if (confirm("Bạn có chắc muốn xóa template này?")) {
      setTemplates(templates.filter((t) => t.id !== id));
    }
  };

  const handlePreview = (template) => {
    setSelectedTemplate(template);
    setActiveView("preview");
  };

  const filteredTemplates = templates.filter((template) => {
    const matchesType = filterType === "all" || template.type === filterType;
    const matchesSearch =
      template.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      template.subject.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesType && matchesSearch;
  });

  // Create/Edit Form
  if (activeView === "create" || activeView === "edit") {
    return (
      <div className="min-h-screen bg-gray-950 text-white p-6">
        <div className="max-w-4xl mx-auto">
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-3xl font-bold mb-2">
                {activeView === "create" ? "Tạo template mới" : "Chỉnh sửa template"}
              </h1>
              <p className="text-gray-400">
                {activeView === "create"
                  ? "Tạo template email hoặc Zalo mới"
                  : "Cập nhật thông tin template"}
              </p>
            </div>
            <button
              onClick={() => {
                setActiveView("list");
                setSelectedTemplate(null);
              }}
              className="px-4 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
            >
              Hủy
            </button>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 space-y-6">
            {/* Template Name */}
            <div>
              <label className="block text-sm font-medium mb-2">Tên template</label>
              <input
                type="text"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                placeholder="Ví dụ: Promo Template 1"
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Type */}
            <div>
              <label className="block text-sm font-medium mb-2">Loại template</label>
              <div className="grid grid-cols-2 gap-4">
                <button
                  onClick={() => setFormData({ ...formData, type: "email" })}
                  className={`p-4 rounded-lg border-2 transition-colors flex items-center gap-3 ${
                    formData.type === "email"
                      ? "border-blue-500 bg-blue-500/10"
                      : "border-gray-700 bg-gray-800 hover:border-gray-600"
                  }`}
                >
                  <Mail size={24} className={formData.type === "email" ? "text-blue-400" : "text-gray-400"} />
                  <div className="text-left">
                    <p className="font-semibold">Email</p>
                    <p className="text-xs text-gray-400">Gửi qua email</p>
                  </div>
                </button>

                <button
                  onClick={() => setFormData({ ...formData, type: "zalo" })}
                  className={`p-4 rounded-lg border-2 transition-colors flex items-center gap-3 ${
                    formData.type === "zalo"
                      ? "border-blue-500 bg-blue-500/10"
                      : "border-gray-700 bg-gray-800 hover:border-gray-600"
                  }`}
                >
                  <MessageSquare size={24} className={formData.type === "zalo" ? "text-blue-400" : "text-gray-400"} />
                  <div className="text-left">
                    <p className="font-semibold">Zalo</p>
                    <p className="text-xs text-gray-400">Gửi qua Zalo</p>
                  </div>
                </button>
              </div>
            </div>

            {/* Subject */}
            <div>
              <label className="block text-sm font-medium mb-2">
                Tiêu đề {formData.type === "email" && "(Subject)"}
              </label>
              <input
                type="text"
                value={formData.subject}
                onChange={(e) => setFormData({ ...formData, subject: e.target.value })}
                placeholder="Nhập tiêu đề..."
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Body */}
            <div>
              <label className="block text-sm font-medium mb-2">Nội dung (Body)</label>
              <div className="mb-2 text-xs text-gray-400 bg-gray-800/50 rounded-lg p-3">
                <p className="mb-1">💡 Bạn có thể sử dụng các biến:</p>
                <code className="text-blue-400">{"{name}"}</code>,{" "}
                <code className="text-blue-400">{"{email}"}</code>,{" "}
                <code className="text-blue-400">{"{phone}"}</code>
              </div>
              <textarea
                value={formData.body}
                onChange={(e) => setFormData({ ...formData, body: e.target.value })}
                placeholder="Nhập nội dung template..."
                rows={12}
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500 resize-none font-mono text-sm"
              />
            </div>

            {/* Actions */}
            <div className="flex items-center gap-3">
              <button
                onClick={activeView === "create" ? handleCreate : handleUpdate}
                className="flex-1 py-3 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors flex items-center justify-center gap-2"
              >
                <Save size={20} />
                {activeView === "create" ? "Tạo template" : "Cập nhật"}
              </button>
              <button
                onClick={() => handlePreview({ ...formData })}
                className="px-6 py-3 bg-gray-700 hover:bg-gray-600 rounded-lg font-semibold transition-colors flex items-center gap-2"
              >
                <Eye size={20} />
                Xem trước
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  // Preview Modal
  if (activeView === "preview" && selectedTemplate) {
    return (
      <div className="min-h-screen bg-gray-950 text-white p-6">
        <div className="max-w-3xl mx-auto">
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-3xl font-bold mb-2">Xem trước template</h1>
              <p className="text-gray-400">{selectedTemplate.name}</p>
            </div>
            <button
              onClick={() => setActiveView("list")}
              className="px-4 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
            >
              Đóng
            </button>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl overflow-hidden">
            {/* Preview Header */}
            <div className="bg-gray-800/50 px-6 py-4 border-b border-gray-800">
              <div className="flex items-center gap-3 mb-3">
                {selectedTemplate.type === "email" ? (
                  <Mail className="text-blue-400" size={20} />
                ) : (
                  <MessageSquare className="text-blue-400" size={20} />
                )}
                <span className="text-sm text-gray-400">
                  {selectedTemplate.type === "email" ? "Email" : "Zalo"}
                </span>
              </div>
              <div className="text-sm">
                <span className="text-gray-400">Subject: </span>
                <span className="font-medium">{selectedTemplate.subject}</span>
              </div>
            </div>

            {/* Preview Body */}
            <div className="p-6">
              <div className="bg-white text-gray-900 rounded-lg p-6 min-h-[300px]">
                <pre className="whitespace-pre-wrap font-sans text-sm">
                  {selectedTemplate.body.replace("{name}", "Khách hàng").replace("{email}", "customer@example.com").replace("{phone}", "0901234567")}
                </pre>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  // Template List
  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <div>
            <h1 className="text-3xl font-bold mb-2">Templates</h1>
            <p className="text-gray-400">Quản lý template email và Zalo</p>
          </div>
          <button
            onClick={() => {
              setFormData({ name: "", type: "email", subject: "", body: "" });
              setActiveView("create");
            }}
            className="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors flex items-center gap-2"
          >
            <Plus size={20} />
            Tạo template mới
          </button>
        </div>

        {/* Filters */}
        <div className="bg-gray-900 border border-gray-800 rounded-xl p-4 mb-6">
          <div className="flex items-center gap-4">
            {/* Search */}
            <div className="flex-1 relative">
              <Search
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={20}
              />
              <input
                type="text"
                placeholder="Tìm kiếm template..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-10 pr-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            {/* Type Filter */}
            <div className="flex items-center gap-2 bg-gray-800 rounded-lg p-1">
              <button
                onClick={() => setFilterType("all")}
                className={`px-4 py-2 rounded-md transition-colors ${
                  filterType === "all"
                    ? "bg-blue-600 text-white"
                    : "text-gray-400 hover:text-white"
                }`}
              >
                Tất cả
              </button>
              <button
                onClick={() => setFilterType("email")}
                className={`px-4 py-2 rounded-md transition-colors flex items-center gap-2 ${
                  filterType === "email"
                    ? "bg-blue-600 text-white"
                    : "text-gray-400 hover:text-white"
                }`}
              >
                <Mail size={16} />
                Email
              </button>
              <button
                onClick={() => setFilterType("zalo")}
                className={`px-4 py-2 rounded-md transition-colors flex items-center gap-2 ${
                  filterType === "zalo"
                    ? "bg-blue-600 text-white"
                    : "text-gray-400 hover:text-white"
                }`}
              >
                <MessageSquare size={16} />
                Zalo
              </button>
            </div>
          </div>
        </div>

        {/* Templates Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredTemplates.map((template) => (
            <div
              key={template.id}
              className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors"
            >
              {/* Header */}
              <div className="flex items-start justify-between mb-4">
                <div className="flex items-center gap-3">
                  <div className={`p-2 rounded-lg ${template.type === "email" ? "bg-blue-500/10" : "bg-green-500/10"}`}>
                    {template.type === "email" ? (
                      <Mail className="text-blue-400" size={20} />
                    ) : (
                      <MessageSquare className="text-green-400" size={20} />
                    )}
                  </div>
                  <div>
                    <span className={`text-xs px-2 py-1 rounded-full ${template.type === "email" ? "bg-blue-400/10 text-blue-400" : "bg-green-400/10 text-green-400"}`}>
                      {template.type === "email" ? "Email" : "Zalo"}
                    </span>
                  </div>
                </div>
              </div>

              {/* Content */}
              <h3 className="text-lg font-semibold mb-2">{template.name}</h3>
              <p className="text-sm text-gray-400 mb-1 line-clamp-1">
                {template.subject}
              </p>
              <p className="text-sm text-gray-500 line-clamp-2 mb-4">
                {template.body}
              </p>

              {/* Stats */}
              <div className="flex items-center gap-4 text-xs text-gray-400 mb-4 pt-4 border-t border-gray-800">
                <span>Sử dụng: {template.usageCount} lần</span>
                <span>•</span>
                <span>{template.createdAt}</span>
              </div>

              {/* Actions */}
              <div className="flex items-center gap-2">
                <button
                  onClick={() => handlePreview(template)}
                  className="flex-1 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors flex items-center justify-center gap-2 text-sm"
                >
                  <Eye size={16} />
                  Xem
                </button>
                <button
                  onClick={() => handleEdit(template)}
                  className="flex-1 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg transition-colors flex items-center justify-center gap-2 text-sm"
                >
                  <Edit size={16} />
                  Sửa
                </button>
                <button
                  onClick={() => handleClone(template)}
                  className="p-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
                  title="Clone"
                >
                  <Copy size={16} />
                </button>
                <button
                  onClick={() => handleDelete(template.id)}
                  className="p-2 text-red-400 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
                  title="Xóa"
                >
                  <Trash2 size={16} />
                </button>
              </div>
            </div>
          ))}
        </div>

        {filteredTemplates.length === 0 && (
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-12 text-center">
            <FileText className="mx-auto mb-4 text-gray-600" size={48} />
            <p className="text-gray-400">Không tìm thấy template nào</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default Template;