import { Outlet } from "react-router-dom";
import { useState } from "react"; // Nếu cần toggle sidebar trên mobile
import Sidebar from "../Sidebar";

function DefaultLayout() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(true); 

  return (
    <div className="bg-gray-100 dark:bg-gray-900 min-h-screen">
      <Sidebar role="user" isOpen={isSidebarOpen} />{" "}
      <div
        className={`min-h-screen flex flex-col transition-all duration-300 ${
          isSidebarOpen ? "md:ml-80" : "ml-0" 
        }`}
      >
        <main className="flex-1 p-4 md:p-6" role="main">
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default DefaultLayout;
