import { BrowserRouter, Routes, Route } from "react-router-dom";
import DefaultLayout from "@/layouts/DefaultLayout";
import HomeLayout from "@/layouts/HomeLayout";
import Home from "./pages/Home";
import Login from "@/pages/users/Login";
import SignUp from "@/pages/users/SignUp";

import DashBoard from "@/pages/clients/DashBoard";
import Contact from "@/pages/clients/Contact";
import Campaign from "@/pages/clients/Campaign";
import Package from "@/pages/clients/Package";
import Template from "@/pages/clients/Template";

import DashBoardAdmin from "@/pages/admins/DashBoard";
import PackageAdmin from "@/pages/admins/Package";
import SendLog from "@/pages/admins/SendLog";
import Transaction from "@/pages/admins/Transaction";
import User from "@/pages/admins/User";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* home */}
        <Route element={<HomeLayout />}>
          <Route path="/" element={<Home />} />
        </Route>
        {/* login */}
        <Route path="/login" element={<Login />} />
        <Route path="/sign-up" element={<SignUp />} />
        {/*  */}
        <Route element={<DefaultLayout />}>
          {/* admin */}
          <Route path="/admin/dashboard" element={<DashBoardAdmin />} />
          <Route path="/admin/package" element={<PackageAdmin />} />
          <Route path="/admin/sendlog" element={<SendLog />} />
          <Route path="/admin/transaction" element={<Transaction />} />
          <Route path="/admin/user" element={<User />} />
          {/* client */}
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/campaign" element={<Contact />} />
          <Route path="/contact" element={<Campaign />} />
          <Route path="/package" element={<Package />} />
          <Route path="/template" element={<Template />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
