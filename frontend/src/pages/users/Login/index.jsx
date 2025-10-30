import {
  Mail,
  Lock,
  Chrome,
  Facebook,
  Twitter,
  Linkedin,
} from "lucide-react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
// import { loginWithEmail } from "@/services/AuthService";
// import useAuth from "@/context/useAuth";
// import useNotification from "@/context/useNotification";

import axios from "axios";

function Login() {
//   const { login } = useAuth();
//   const { notify } = useNotification();
  const [data, setData] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();
  const handleChange = (e) => {
    setData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };
  
  const handleLogin = async () => {
    // try {
    //   const response = await loginWithEmail(data);
    //   if (response.status) {
        // notify("Login successfully! 🎉", "success");
        // // login(response.accessToken);
        // localStorage.setItem("accessToken", response.accessToken);
        // localStorage.setItem("refreshToken", response.refreshToken);
        // localStorage.setItem("userId", response.userId);
        // localStorage.setItem("userName", response.userName);

        navigate("/dashboard");
    //   } else {
        // notify("Incorrect email or password 🎉", "error");
    //     navigate("/login");
    //   }
    // } catch (error) {
    //   console.log(error);
    // }
  };
  
  const handleLoginWithGoogle = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/oauth/google"
      );

      window.location.href = response.data.authUrl;
    } catch (error) {
      console.error("Lỗi khi gọi backend để đăng nhập với Google", error);
    }
  };
  
  const handleLoginWithFacebook = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/oauth/facebook"
      );

      const { authUrl } = response.data;
      window.location.href = authUrl;
    } catch (error) {
      console.error("Lỗi khi gọi backend để đăng nhập với Facebook", error);
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
              <input type="checkbox" className="mr-2 accent-purple-500" /> Nhớ Mật
              khẩu
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
            <div className="flex justify-center gap-3 mt-2">
              <button
                className="p-2 bg-gray-700 border border-gray-600 rounded hover:bg-gray-600 transition-colors"
                onClick={handleLoginWithGoogle}
              >
                <Chrome className="text-white" size={20} />
              </button>
              <button
                className="p-2 bg-gray-700 border border-gray-600 rounded hover:bg-gray-600 transition-colors"
                onClick={handleLoginWithFacebook}
              >
                <Facebook className="text-white" size={20} />
              </button>
              <button className="p-2 bg-gray-700 border border-gray-600 rounded hover:bg-gray-600 transition-colors">
                <Twitter className="text-white" size={20} />
              </button>
              <button className="p-2 bg-gray-700 border border-gray-600 rounded hover:bg-gray-600 transition-colors">
                <Linkedin className="text-white" size={20} />
              </button>
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