import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import {
  CreditCard,
  Wallet,
  Check,
  ArrowLeft,
  Package,
  Calendar,
  Mail,
  Phone,
  User,
  ShieldCheck,
  Loader,
} from "lucide-react";

function Payment() {
  const location = useLocation();
  const navigate = useNavigate();
  const selectedPackage = location.state?.package;

  const [paymentMethod, setPaymentMethod] = useState("vnpay");
  const [isProcessing, setIsProcessing] = useState(false);

  const [billingInfo, setBillingInfo] = useState({
    fullName: "",
    email: "",
    phone: "",
  });

  const paymentMethods = [
    {
      id: "vnpay",
      name: "VNPay",
      icon: Wallet,
      description: "Thanh toán qua VNPay QR",
      logo: "🏦",
    },
    {
      id: "momo",
      name: "Momo",
      icon: Wallet,
      description: "Ví điện tử Momo",
      logo: "💳",
    },
    {
      id: "banking",
      name: "Chuyển khoản ngân hàng",
      icon: CreditCard,
      description: "Chuyển khoản trực tiếp",
      logo: "🏦",
    },
  ];

 


  const handlePayment = async () => {
    // Validate billing info
    if (!billingInfo.fullName || !billingInfo.email || !billingInfo.phone) {
      alert("Vui lòng điền đầy đủ thông tin thanh toán");
      return;
    }

    setIsProcessing(true);

    // Simulate API call
    setTimeout(() => {
      const transaction = {
        id: `TXN${Date.now()}`,
        packageId: selectedPackage.id,
        packageName: selectedPackage.name,
        amount: selectedPackage.price,
        paymentMethod,
        billingInfo,
        status: "pending",
        createdAt: new Date().toISOString(),
      };

      console.log("Creating transaction:", transaction);

      // Redirect to payment gateway or success page
      setIsProcessing(false);
      alert(`Đang chuyển hướng đến ${paymentMethods.find(m => m.id === paymentMethod)?.name}...`);
      
      // Navigate to transaction page or success page
      // navigate("/transaction", { state: { transaction } });
    }, 2000);
  };

  return (
    <div className="min-h-screen bg-gray-950 text-white p-6">
      <div className="max-w-5xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <button
            onClick={() => navigate("/plan")}
            className="flex items-center gap-2 text-gray-400 hover:text-white mb-4 transition-colors"
          >
            <ArrowLeft size={20} />
            Quay lại
          </button>
          <h1 className="text-3xl font-bold mb-2">Thanh toán</h1>
          <p className="text-gray-400">Hoàn tất thanh toán để kích hoạt gói dịch vụ</p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Left Column - Payment Form */}
          <div className="lg:col-span-2 space-y-6">
            {/* Billing Information */}
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
              <h2 className="text-xl font-semibold mb-4 flex items-center gap-2">
                <User size={20} />
                Thông tin thanh toán
              </h2>
              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium mb-2">Họ và tên</label>
                  <input
                    type="text"
                    value={billingInfo.fullName}
                    onChange={(e) =>
                      setBillingInfo({ ...billingInfo, fullName: e.target.value })
                    }
                    placeholder="Nguyễn Văn A"
                    className="w-full px-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium mb-2">Email</label>
                  <div className="relative">
                    <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={18} />
                    <input
                      type="email"
                      value={billingInfo.email}
                      onChange={(e) =>
                        setBillingInfo({ ...billingInfo, email: e.target.value })
                      }
                      placeholder="email@example.com"
                      className="w-full pl-10 pr-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-sm font-medium mb-2">Số điện thoại</label>
                  <div className="relative">
                    <Phone className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={18} />
                    <input
                      type="tel"
                      value={billingInfo.phone}
                      onChange={(e) =>
                        setBillingInfo({ ...billingInfo, phone: e.target.value })
                      }
                      placeholder="0901234567"
                      className="w-full pl-10 pr-4 py-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:border-blue-500"
                    />
                  </div>
                </div>
              </div>
            </div>

            {/* Payment Method */}
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-6">
              <h2 className="text-xl font-semibold mb-4 flex items-center gap-2">
                <CreditCard size={20} />
                Phương thức thanh toán
              </h2>
              <div className="space-y-3">
                {paymentMethods.map((method) => {
                  // const Icon = method.icon;
                  return (
                    <button
                      key={method.id}
                      onClick={() => setPaymentMethod(method.id)}
                      className={`w-full p-4 rounded-lg border-2 transition-all flex items-center gap-4 ${
                        paymentMethod === method.id
                          ? "border-blue-500 bg-blue-500/10"
                          : "border-gray-700 bg-gray-800 hover:border-gray-600"
                      }`}
                    >
                      <div className="text-3xl">{method.logo}</div>
                      <div className="flex-1 text-left">
                        <p className="font-semibold">{method.name}</p>
                        <p className="text-sm text-gray-400">{method.description}</p>
                      </div>
                      {paymentMethod === method.id && (
                        <div className="bg-blue-500 rounded-full p-1">
                          <Check size={16} />
                        </div>
                      )}
                    </button>
                  );
                })}
              </div>
            </div>

            {/* Security Notice */}
            <div className="bg-green-900/20 border border-green-500/20 rounded-xl p-4 flex items-start gap-3">
              <ShieldCheck className="text-green-400 flex-shrink-0 mt-0.5" size={20} />
              <div className="text-sm">
                <p className="font-semibold text-green-400 mb-1">Thanh toán an toàn</p>
                <p className="text-gray-400">
                  Thông tin thanh toán của bạn được mã hóa và bảo mật. Chúng tôi không lưu trữ
                  thông tin thẻ của bạn.
                </p>
              </div>
            </div>
          </div>

          {/* Right Column - Order Summary */}
          <div className="lg:col-span-1">
            <div className="bg-gray-900 border border-gray-800 rounded-xl p-6 sticky top-6">
              <h2 className="text-xl font-semibold mb-4">Tóm tắt đơn hàng</h2>

              {/* Package Info */}
              <div className="bg-gray-800/50 rounded-lg p-4 mb-6">
                <div className="flex items-center gap-3 mb-3">
                  <div className="bg-purple-400/10 p-2 rounded-lg">
                    <Package className="text-purple-400" size={20} />
                  </div>
                  <div>
                    <p className="font-semibold">
                      {/* {selectedPackage.name} */}
                      dfd
                    </p>
                    <p className="text-sm text-gray-400">
                      {/* {selectedPackage.duration} */}
                      df
                    </p>
                  </div>
                </div>

                <div className="space-y-2 text-sm">
                  <div className="flex items-center gap-2 text-gray-400">
                    <Check size={14} className="text-green-400" />
                    {/* <span>{selectedPackage.features[0].text}</span> */}
                  </div>
                  <div className="flex items-center gap-2 text-gray-400">
                    <Check size={14} className="text-green-400" />
                    {/* <span>{selectedPackage.features[1].text}</span> */}
                  </div>
                  <div className="flex items-center gap-2 text-gray-400">
                    <Check size={14} className="text-green-400" />
                    {/* <span>{selectedPackage.features[2].text}</span> */}
                  </div>
                </div>
              </div>

              {/* Price Breakdown */}
              <div className="space-y-3 mb-6 pb-6 border-b border-gray-800">
                <div className="flex items-center justify-between text-sm">
                  <span className="text-gray-400">Giá gói</span>
                  {/* <span>{selectedPackage.price.toLocaleString("vi-VN")} đ</span> */}
                </div>
                <div className="flex items-center justify-between text-sm">
                  <span className="text-gray-400">VAT (0%)</span>
                  <span>0 đ</span>
                </div>
              </div>

              {/* Total */}
              <div className="flex items-center justify-between mb-6">
                <span className="text-lg font-semibold">Tổng cộng</span>
                <span className="text-2xl font-bold text-blue-400">
                  {/* {selectedPackage.price.toLocaleString("vi-VN")} đ */}
                </span>
              </div>

              {/* Payment Button */}
              <button
                onClick={handlePayment}
                disabled={isProcessing}
                className="w-full py-3 bg-blue-600 hover:bg-blue-700 disabled:bg-gray-700 disabled:cursor-not-allowed rounded-lg font-semibold transition-colors flex items-center justify-center gap-2"
              >
                {isProcessing ? (
                  <>
                    <Loader className="animate-spin" size={20} />
                    Đang xử lý...
                  </>
                ) : (
                  <>
                    <CreditCard size={20} />
                    Thanh toán ngay
                  </>
                )}
              </button>

              {/* Activation Info */}
              <div className="mt-4 text-xs text-gray-400 text-center">
                <Calendar className="inline mr-1" size={14} />
                Gói dịch vụ sẽ được kích hoạt ngay sau khi thanh toán thành công
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Payment;