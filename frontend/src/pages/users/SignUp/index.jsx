import { Mail, Facebook, User2 } from "lucide-react";
import { userApi } from "@/services/UserApi";
import GppMaybeIcon from "@mui/icons-material/GppMaybe";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Google } from "@mui/icons-material";
import LoadingModal from "@/components/LoadingModal";
function SignUp() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    name: "",
    password: "12345",
    confirmPassword: "12345",
  });

  const [isProcessing, setIsProcessing] = useState(false);
  const handleSubmit = async () => {
    if (!formData.email || !formData.password || !formData.name) {
      alert("Please enter complete information!");
      return;
    }
    if (formData.password !== formData.confirmPassword) {
      alert("Incorrect password re-entered!");
      return;
    }
    setIsProcessing(true);
    try {
      const res = await userApi.register(formData);
      console.log(res);
      navigate("/verify-otp", { state: { formData } });
    } catch (err) {
      console.error(err);
    }finally{
      setIsProcessing(false);
    }
  };

  // Function to handle input change
  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  return (
    <div className="flex text-white h-screen items-center justify-center bg-gray-900">
      <div className="w-full max-w-md  border-gray-700 bg-gray-800  border p-6 rounded-lg shadow-md">
        <h2 className="text-white text-center text-2xl font-semibold mb-4">
          Đăng ký
        </h2>

        <div className="">
          <div className="mb-3">
            <div className="flex items-center border bg-gray-700 border-white rounded p-2 ">
              <Mail className="text-white" size={20} />
              <input
                name="email"
                type="email"
                className="ml-2 w-full outline-none bg-transparent text-white "
                placeholder="Email"
                required
                value={formData.email}
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="mb-3">
            <div className="flex items-center border bg-gray-700 border-white rounded p-2 ">
              <User2 className="text-white" size={20} />
              <input
                name="name"
                type="name"
                className="ml-2 w-full outline-none bg-transparent text-white "
                placeholder="name"
                required
                value={formData.name}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="mb-3">
            <div className="flex items-center border bg-gray-700 border-white rounded p-2 ">
              <GppMaybeIcon className="text-white" />
              <input
                name="password"
                type="password"
                className="ml-2 w-full outline-none bg-transparent text-white "
                placeholder="Password"
                required
                value={formData.password}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="mb-3">
            <div className="flex items-center border bg-gray-700 border-white rounded p-2 ">
              <GppMaybeIcon className="text-white" />
              <input
                name="confirmPassword"
                type="password"
                className="ml-2 w-full outline-none bg-transparent text-white "
                placeholder="Confirm password"
                required
                value={formData.confirmPassword}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="flex items-center text-white">
            <input type="checkbox" id="terms" className="mr-2" />
            <label htmlFor="terms " className="text-sm">
              Tôi đồng ý với các điều khoản
            </label>
          </div>

          <button
            className="mt-4 w-full bg-white text-black py-2 rounded font-semibold"
            onClick={handleSubmit}
          >
            Đăng ký
          </button>
        </div>

        <div className="flex items-center my-4">
          <hr className="flex-grow border-gray-300" />
          <span className="px-3 text-white">Hoặc đăng nhập với</span>
          <hr className="flex-grow border-gray-300" />
        </div>

        <div className="flex justify-center space-x-3">
          <button className="p-2 bg-white  rounded border">
            <Google className="text-white" />
          </button>
          <button className="p-2 bg-white  rounded border">
            <Facebook className="text-white" />
          </button>
        </div>

        <div className="text-center mt-4">
          <span className="text-sm">
            Bạn đã có tài khoản?{" "}
            <Link to="/login" className="text-gray-500 hover:underline">
              Đăng nhập ngay
            </Link>
          </span>
        </div>
      </div>

      <LoadingModal isProcessing={isProcessing} />
    </div>
  );
}

export default SignUp;
