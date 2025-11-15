import { userApi } from "@services/UserApi";
import { useState, useRef, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Shield, Mail, ArrowLeft } from "lucide-react";

function Otp() {
  const navigate = useNavigate();
  const location = useLocation();
  const [otp, setOtp] = useState(["", "", "", "", "", ""]);
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const inputRefs = useRef([]);
  const formData = location.state?.formData;

  useEffect(() => {
    inputRefs.current[0]?.focus();
  }, []);

  const handleChange = (index, value) => {
    if (!/^\d*$/.test(value)) return;

    const newOtp = [...otp];
    newOtp[index] = value.slice(-1);
    setOtp(newOtp);
    setError("");

    if (value && index < 5) {
      inputRefs.current[index + 1]?.focus();
    }
  };

  const handleKeyDown = (index, e) => {
    if (e.key === "Backspace" && !otp[index] && index > 0) {
      inputRefs.current[index - 1]?.focus();
    }
  };

  const handleVerify = async () => {
    const otpString = otp.join("");
    if (otpString.length !== 6) {
      setError("Vui lòng nhập đầy đủ mã OTP");
      return;
    }

    setIsLoading(true);
    setError("");

    try {
      const response = await userApi.verifyOtp(formData.email, otpString);
      if (response.code === 2000) {
        alert(response.message);
        navigate("/login");
      }
    } catch (error) {
      setError("Mã OTP không đúng hoặc đã hết hạn");
      console.log(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleResend = async () => {
    setError("");
    setOtp(["", "", "", "", "", ""]);
    inputRefs.current[0]?.focus();
    // Add resend OTP logic here
  };

  return (
    <div className="min-h-screen bg-black flex items-center justify-center px-4 py-8">
      <div className="w-full max-w-md">
        {/* Back Button */}
        <button
          onClick={() => navigate(-1)}
          className="flex items-center gap-2 text-gray-400 hover:text-white transition-colors mb-8"
        >
          <ArrowLeft size={20} />
          <span>Quay lại</span>
        </button>

        {/* Main Card */}
        <div className="bg-zinc-900 rounded-2xl p-8 shadow-2xl border border-zinc-800">
          {/* Icon */}
          <div className="flex justify-center mb-6">
            <div className="w-16 h-16 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center">
              <Shield className="text-white" size={32} />
            </div>
          </div>

          {/* Title */}
          <h1 className="text-3xl font-bold text-white text-center mb-2">
            Xác thực OTP
          </h1>

          {/* Email Info */}
          <div className="flex items-center justify-center gap-2 text-gray-400 mb-6">
            <Mail size={16} />
            <p className="text-sm">
              Mã đã được gửi đến{" "}
              <span className="text-white font-medium">{formData?.email}</span>
            </p>
          </div>

          {/* OTP Input */}
          <div className="mb-6">
            <label className="block text-gray-300 text-sm font-medium mb-3">
              Nhập mã OTP (6 chữ số)
            </label>
            <div className="flex gap-2 justify-center">
              {otp.map((digit, index) => (
                <input
                  key={index}
                  ref={(el) => (inputRefs.current[index] = el)}
                  type="text"
                  inputMode="numeric"
                  maxLength={1}
                  value={digit}
                  onChange={(e) => handleChange(index, e.target.value)}
                  onKeyDown={(e) => handleKeyDown(index, e)}
                  className="w-12 h-14 text-center text-2xl font-bold bg-zinc-800 border-2 border-zinc-700 rounded-lg text-white focus:border-blue-500 focus:outline-none transition-all"
                />
              ))}
            </div>
          </div>

          {/* Error Message */}
          {error && (
            <div className="mb-4 p-3 bg-red-500/10 border border-red-500/30 rounded-lg">
              <p className="text-red-400 text-sm text-center">{error}</p>
            </div>
          )}

          {/* Verify Button */}
          <button
            onClick={handleVerify}
            disabled={isLoading || otp.join("").length !== 6}
            className="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white font-semibold py-3.5 rounded-lg hover:from-blue-700 hover:to-purple-700 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed mb-4"
          >
            {isLoading ? (
              <span className="flex items-center justify-center gap-2">
                <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                Đang xác thực...
              </span>
            ) : (
              "Xác thực"
            )}
          </button>

          {/* Resend */}
          <div className="text-center">
            <p className="text-gray-400 text-sm">
              Không nhận được mã?{" "}
              <button
                onClick={handleResend}
                className="text-blue-500 hover:text-blue-400 font-medium transition-colors"
              >
                Gửi lại
              </button>
            </p>
          </div>
        </div>

        {/* Footer Info */}
        <p className="text-gray-500 text-xs text-center mt-6">
          Mã OTP có hiệu lực trong 5 phút
        </p>
      </div>
    </div>
  );
}

export default Otp;
