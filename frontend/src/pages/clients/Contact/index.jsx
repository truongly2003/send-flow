import { useState } from "react";
import {
  Plus,
  Search,
  Upload,
  Download,
  Trash2,
  Edit,
  Users,
  Mail,
  Phone,
  FolderOpen,
  X,
  Save,
  FileText,
} from "lucide-react";

function Contact() {
  const [activeView, setActiveView] = useState("lists"); // lists, listDetail, createList
  const [selectedList, setSelectedList] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [showAddContactModal, setShowAddContactModal] = useState(false);
  const [showImportModal, setShowImportModal] = useState(false);

  // Sample data
  const [contactLists, setContactLists] = useState([
    {
      id: 1,
      name: "VIP Customers",
      description: "Khách hàng VIP",
      contactCount: 1200,
      createdAt: "2025-10-01",
    },
    {
      id: 2,
      name: "All Subscribers",
      description: "Tất cả người đăng ký",
      contactCount: 850,
      createdAt: "2025-09-15",
    },
    {
      id: 3,
      name: "Newsletter List",
      description: "Danh sách nhận newsletter",
      contactCount: 980,
      createdAt: "2025-09-20",
    },
  ]);

  const [contacts, setContacts] = useState([
    {
      id: 1,
      name: "Nguyễn Văn A",
      phone: "0901234567",
      email: "nguyenvana@example.com",
      listId: 1,
    },
    {
      id: 2,
      name: "Trần Thị B",
      phone: "0912345678",
      email: "tranthib@example.com",
      listId: 1,
    },
    {
      id: 3,
      name: "Lê Văn C",
      phone: "0923456789",
      email: "levanc@example.com",
      listId: 1,
    },
  ]);

  const [newList, setNewList] = useState({
    name: "",
    description: "",
  });

  const [newContact, setNewContact] = useState({
    name: "",
    phone: "",
    email: "",
  });

  const handleCreateList = () => {
    const list = {
      id: Date.now(),
      ...newList,
      contactCount: 0,
      createdAt: new Date().toISOString().split("T")[0],
    };
    setContactLists([...contactLists, list]);
    setNewList({ name: "", description: "" });
    setActiveView("lists");
  };

  const handleDeleteList = (id) => {
    if (confirm("Bạn có chắc muốn xóa danh sách này?")) {
      setContactLists(contactLists.filter((list) => list.id !== id));
    }
  };

  const handleViewListDetail = (list) => {
    setSelectedList(list);
    setActiveView("listDetail");
  };

  const handleAddContact = () => {
    const contact = {
      id: Date.now(),
      ...newContact,
      listId: selectedList.id,
    };
    setContacts([...contacts, contact]);
    setNewContact({ name: "", phone: "", email: "" });
    setShowAddContactModal(false);
  };

  const handleDeleteContact = (id) => {
    if (confirm("Bạn có chắc muốn xóa liên hệ này?")) {
      setContacts(contacts.filter((contact) => contact.id !== id));
    }
  };

  const handleImportCSV = (e) => {
    const file = e.target.files[0];
    if (file) {
      console.log("Importing CSV:", file.name);
      // Xử lý import CSV ở đây
      setShowImportModal(false);
    }
  };

  const handleExportCSV = () => {
    const listContacts = contacts.filter((c) => c.listId === selectedList.id);
    const csvContent =
      "Name,Phone,Email\n" +
      listContacts.map((c) => `${c.name},${c.phone},${c.email}`).join("\n");

    const blob = new Blob([csvContent], { type: "text/csv" });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `${selectedList.name}_contacts.csv`;
    a.click();
  };

  const filteredContacts = contacts.filter(
    (contact) =>
      contact.listId === selectedList?.id &&
      (contact.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        contact.email.toLowerCase().includes(searchQuery.toLowerCase()) ||
        contact.phone.includes(searchQuery))
  );

  // Create List View
  if (activeView === "createList") {
    return (
      <div className="min-h-screen bg-gray-950 text-white p-6">
        <div className="max-w-3xl mx-auto">
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-3xl font-bold mb-2">Tạo danh sách mới</h1>
              <p className="text-gray-400">Tạo danh sách liên hệ mới</p>
            </div>
            <button
              onClick={() => setActiveView("lists")}
              className="px-4 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
            >
              Hủy
            </button>
          </div>

          <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 space-y-6">
            <div>
              <label className="block text-sm font-medium mb-2">Tên danh sách</label>
              <input
                type="text"
                value={newList.name}
                onChange={(e) => setNewList({ ...newList, name: e.target.value })}
                placeholder="Ví dụ: Khách hàng VIP"
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
              />
            </div>

            <div>
              <label className="block text-sm font-medium mb-2">Mô tả</label>
              <textarea
                value={newList.description}
                onChange={(e) => setNewList({ ...newList, description: e.target.value })}
                placeholder="Mô tả về danh sách này..."
                rows={4}
                className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500 resize-none"
              />
            </div>

            <button
              onClick={handleCreateList}
              className="w-full py-3 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors flex items-center justify-center gap-2"
            >
              <Save size={20} />
              Tạo danh sách
            </button>
          </div>
        </div>
      </div>
    );
  }

  // List Detail View
  if (activeView === "listDetail" && selectedList) {
    return (
      <div className="min-h-screen bg-gray-950 text-white p-6">
        <div className="max-w-7xl mx-auto">
          {/* Header */}
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-3xl font-bold mb-2">{selectedList.name}</h1>
              <p className="text-gray-400">{selectedList.description}</p>
            </div>
            <button
              onClick={() => setActiveView("lists")}
              className="px-4 py-2 bg-gray-800 hover:bg-gray-700 rounded-lg transition-colors"
            >
              Quay lại
            </button>
          </div>

          {/* Stats */}
          <div className="grid grid-cols-3 gap-4 mb-6">
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-5">
              <div className="flex items-center gap-2 mb-2">
                <Users className="text-blue-400" size={20} />
                <span className="text-sm text-gray-400">Tổng liên hệ</span>
              </div>
              <p className="text-3xl font-bold">{filteredContacts.length}</p>
            </div>
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-5">
              <div className="flex items-center gap-2 mb-2">
                <Mail className="text-green-400" size={20} />
                <span className="text-sm text-gray-400">Email hợp lệ</span>
              </div>
              <p className="text-3xl font-bold">
                {filteredContacts.filter((c) => c.email).length}
              </p>
            </div>
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-5">
              <div className="flex items-center gap-2 mb-2">
                <Phone className="text-purple-400" size={20} />
                <span className="text-sm text-gray-400">SĐT hợp lệ</span>
              </div>
              <p className="text-3xl font-bold">
                {filteredContacts.filter((c) => c.phone).length}
              </p>
            </div>
          </div>

          {/* Actions Bar */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-4 mb-6">
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-3 flex-1">
                <div className="relative flex-1 max-w-md">
                  <Search
                    className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                    size={20}
                  />
                  <input
                    type="text"
                    placeholder="Tìm kiếm theo tên, email, số điện thoại..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    className="w-full pl-10 pr-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
                  />
                </div>
              </div>

              <div className="flex items-center gap-3">
                <button
                  onClick={() => setShowAddContactModal(true)}
                  className="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg transition-colors flex items-center gap-2"
                >
                  <Plus size={18} />
                  Thêm liên hệ
                </button>
                <button
                  onClick={() => setShowImportModal(true)}
                  className="px-4 py-2 bg-green-600 hover:bg-green-700 rounded-lg transition-colors flex items-center gap-2"
                >
                  <Upload size={18} />
                  Import CSV
                </button>
                <button
                  onClick={handleExportCSV}
                  className="px-4 py-2 bg-gray-700 hover:bg-gray-600 rounded-lg transition-colors flex items-center gap-2"
                >
                  <Download size={18} />
                  Export CSV
                </button>
              </div>
            </div>
          </div>

          {/* Contacts Table */}
          <div className="bg-gray-900 border border-gray-800 rounded-xl overflow-hidden">
            <table className="w-full">
              <thead className="bg-gray-800/50">
                <tr>
                  <th className="text-left px-6 py-4 text-sm font-medium text-gray-400">
                    Tên
                  </th>
                  <th className="text-left px-6 py-4 text-sm font-medium text-gray-400">
                    Số điện thoại
                  </th>
                  <th className="text-left px-6 py-4 text-sm font-medium text-gray-400">
                    Email
                  </th>
                  <th className="text-right px-6 py-4 text-sm font-medium text-gray-400">
                    Hành động
                  </th>
                </tr>
              </thead>
              <tbody>
                {filteredContacts.map((contact) => (
                  <tr key={contact.id} className="border-t border-gray-800 hover:bg-gray-800/30">
                    <td className="px-6 py-4">{contact.name}</td>
                    <td className="px-6 py-4 text-gray-400">{contact.phone}</td>
                    <td className="px-6 py-4 text-gray-400">{contact.email}</td>
                    <td className="px-6 py-4 text-right">
                      <div className="flex items-center justify-end gap-2">
                        <button className="p-2 text-blue-400 hover:bg-gray-800 rounded-lg transition-colors">
                          <Edit size={18} />
                        </button>
                        <button
                          onClick={() => handleDeleteContact(contact.id)}
                          className="p-2 text-red-400 hover:bg-gray-800 rounded-lg transition-colors"
                        >
                          <Trash2 size={18} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            {filteredContacts.length === 0 && (
              <div className="p-12 text-center">
                <Users className="mx-auto mb-4 text-gray-600" size={48} />
                <p className="text-gray-400">Chưa có liên hệ nào</p>
              </div>
            )}
          </div>
        </div>

        {/* Add Contact Modal */}
        {showAddContactModal && (
          <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 w-full max-w-md">
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-xl font-semibold">Thêm liên hệ mới</h2>
                <button
                  onClick={() => setShowAddContactModal(false)}
                  className="text-gray-400 hover:text-white"
                >
                  <X size={24} />
                </button>
              </div>

              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium mb-2">Tên</label>
                  <input
                    type="text"
                    value={newContact.name}
                    onChange={(e) => setNewContact({ ...newContact, name: e.target.value })}
                    placeholder="Nguyễn Văn A"
                    className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium mb-2">Số điện thoại</label>
                  <input
                    type="tel"
                    value={newContact.phone}
                    onChange={(e) => setNewContact({ ...newContact, phone: e.target.value })}
                    placeholder="0901234567"
                    className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium mb-2">Email</label>
                  <input
                    type="email"
                    value={newContact.email}
                    onChange={(e) => setNewContact({ ...newContact, email: e.target.value })}
                    placeholder="email@example.com"
                    className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
                  />
                </div>

                <button
                  onClick={handleAddContact}
                  className="w-full py-2 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors"
                >
                  Thêm liên hệ
                </button>
              </div>
            </div>
          </div>
        )}

        {/* Import CSV Modal */}
        {showImportModal && (
          <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 w-full max-w-md">
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-xl font-semibold">Import CSV</h2>
                <button
                  onClick={() => setShowImportModal(false)}
                  className="text-gray-400 hover:text-white"
                >
                  <X size={24} />
                </button>
              </div>

              <div className="space-y-4">
                <div className="bg-gray-800/50 rounded-lg p-4 text-sm text-gray-400">
                  <p className="mb-2">File CSV phải có định dạng:</p>
                  <code className="text-blue-400">Name,Phone,Email</code>
                </div>

                <label className="block">
                  <div className="border-2 border-dashed border-gray-700 rounded-lg p-8 text-center cursor-pointer hover:border-blue-500 transition-colors">
                    <Upload className="mx-auto mb-2 text-gray-400" size={32} />
                    <p className="text-sm text-gray-400">Click để chọn file CSV</p>
                    <input
                      type="file"
                      accept=".csv"
                      onChange={handleImportCSV}
                      className="hidden"
                    />
                  </div>
                </label>
              </div>
            </div>
          </div>
        )}
      </div>
    );
  }

  // Contact Lists View
  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <div>
            <h1 className="text-3xl font-bold mb-2">Danh sách liên hệ</h1>
            <p className="text-gray-400">Quản lý danh sách liên hệ của bạn</p>
          </div>
          <button
            onClick={() => setActiveView("createList")}
            className="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors flex items-center gap-2"
          >
            <Plus size={20} />
            Tạo danh sách mới
          </button>
        </div>

        {/* Lists Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {contactLists.map((list) => (
            <div
              key={list.id}
              className="bg-gray-900 border border-gray-800 rounded-xl p-6 hover:border-gray-700 transition-colors"
            >
              <div className="flex items-start justify-between mb-4">
                <div className="bg-blue-500/10 p-3 rounded-lg">
                  <FolderOpen className="text-blue-400" size={24} />
                </div>
                <div className="flex items-center gap-2">
                  <button
                    onClick={() => handleDeleteList(list.id)}
                    className="p-2 text-red-400 hover:bg-gray-800 rounded-lg transition-colors"
                  >
                    <Trash2 size={18} />
                  </button>
                </div>
              </div>

              <h3 className="text-xl font-semibold mb-2">{list.name}</h3>
              <p className="text-sm text-gray-400 mb-4">{list.description}</p>

              <div className="flex items-center justify-between pt-4 border-t border-gray-800">
                <div className="flex items-center gap-2 text-sm text-gray-400">
                  <Users size={16} />
                  <span>{list.contactCount} liên hệ</span>
                </div>
                <button
                  onClick={() => handleViewListDetail(list)}
                  className="px-3 py-1 bg-blue-600 hover:bg-blue-700 rounded-lg text-sm font-medium transition-colors"
                >
                  Xem chi tiết
                </button>
              </div>
            </div>
          ))}
        </div>

        {contactLists.length === 0 && (
          <div className="bg-gray-900 border border-gray-800 rounded-xl p-12 text-center">
            <FolderOpen className="mx-auto mb-4 text-gray-600" size={48} />
            <p className="text-gray-400">Chưa có danh sách nào</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default Contact;