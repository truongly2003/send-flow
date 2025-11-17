import { Mail, Lock } from "lucide-react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { userApi } from "@/services/UserApi";

function Login() {
  const [data, setData] = useState({
    email: "",
    password: "12345",
  });

  const navigate = useNavigate();
  const handleChange = (e) => {
    setData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleLogin = async () => {
    if (!data.email || !data.password) {
      alert("Please enter complete information!");
      return;
    }
    try {
      const response = await userApi.login(data);
      if (response.code === 2000) {
        const user = response.data;
        alert(response.message);
        localStorage.setItem("user", JSON.stringify(user));
        if (user.role === "ADMIN")  window.location.href = "/admin/dashboard";
        if (user.role === "USER")  window.location.href = "/dashboard";
      } else {
        alert(response.message);
        navigate("/login");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="flex h-screen items-center justify-center bg-gray-900">
      <div className="w-full max-w-md border border-gray-700 bg-gray-800 p-6 rounded-lg shadow-xl">
        <h2 className="text-center text-xl font-semibold mb-4 text-white">
          Đăng nhập
        </h2>
        <div>
          <div className="mb-3">
            <div className="flex items-center border rounded p-2 border-white bg-gray-700">
              <Mail className="text-white" size={20} />
              <input
                type="email"
                className="ml-2 w-full outline-none bg-transparent text-white placeholder-gray-400"
                placeholder="Email"
                name="email"
                value={data.email}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="mb-3">
            <div className="flex items-center border rounded p-2 border-white bg-gray-700">
              <Lock className="text-white" size={20} />
              <input
                type="password"
                className="ml-2 w-full outline-none bg-transparent text-white placeholder-gray-400"
                placeholder="Password"
                name="password"
                value={data.password}
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="flex justify-between text-sm text-gray-300">
            <label>
              <input type="checkbox" className="mr-2 accent-purple-500" /> Nhớ
              Mật khẩu
            </label>
            <Link to="/forgot-password" className="text-white hover:underline">
              Quên mật khẩu
            </Link>
          </div>

          <button
            className="mt-4 w-full bg-white hover:bg-white text-black py-2 rounded font-semibold transition-colors"
            onClick={handleLogin}
          >
            Đăng nhập
          </button>

          <div className="mt-4 text-center text-sm text-gray-300">
            <div className="flex items-center my-4">
              <hr className="flex-grow border-gray-600" />
              <span className="px-3 text-gray-400">Hoặc đăng nhập với</span>
              <hr className="flex-grow border-gray-600" />
            </div>
          </div>

          <div className="mt-4 text-center text-sm text-gray-300">
            Bạn chưa có tài khoản?{" "}
            <Link to="/sign-up" className="text-white hover:underline">
              Đăng ký ngay
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
