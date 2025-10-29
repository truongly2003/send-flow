import { NavLink } from "react-router-dom";

const Header = () => {
  const navLinkClass = ({ isActive }) =>
    `${
      isActive
        ? "text-purple-500 font-semibold"
        : "text-gray-400 hover:text-purple-500"
    } transition-colors duration-200`;

  return (
    <header className="sticky top-0 z-50 bg-gray-900 border-b border-gray-800 shadow-lg">
      <div className="max-w-7xl mx-auto flex justify-between items-center px-5 py-4 relative">
        <h1 className="text-xl font-bold text-white">
          <NavLink to="/" className="hover:opacity-80 transition-opacity">
            SendFlow
          </NavLink>
        </h1>

        {/* Menu desktop */}
        <nav className="hidden md:flex space-x-4">
          <NavLink to="/login" className= {navLinkClass}>
            <button className="px-6 py-2 w-36 rounded-lg font-semibold transition-all duration-300 text-black bg-white">
              Đăng nhập
            </button>
          </NavLink>
           <NavLink to="/sign-up" className={navLinkClass}>
            <button className="px-6 py-2 w-36 rounded-lg font-semibold transition-all duration-300 text-black bg-white">
              Đăng ký
            </button>
          </NavLink>
        </nav>
      </div>
    </header>
  );
};

export default Header;
