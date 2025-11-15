import { BrowserRouter, Routes, Route } from "react-router-dom";
import DefaultLayout from "@/layouts/DefaultLayout";
import HomeLayout from "@/layouts/HomeLayout";
import Home from "./pages/Home";
import Login from "@/pages/users/Login";
import SignUp from "@/pages/users/SignUp";
import Otp from "@/pages/users/Otp";

import DashBoard from "@/pages/clients/DashBoard";
import ContactList from "@/pages/clients/ContactList";
import Campaign from "@/pages/clients/Campaign";
import CampaignDetail from "@/pages/clients/Campaign/CampaignDetail";
import SendLog from "@/pages/clients/Campaign/SendLog";
// import SendLog from "../src/pages/clients/Campaign/SendLog.jsx"
import Settings from "@pages/Settings"
import Plan from "@/pages/clients/Plan";
import Template from "@/pages/clients/Template";
import TemplateDetail from "@/pages/clients/Template/TemplateDetail";
import Contact from "@/pages/clients/ContactList/Contact";

import DashBoardAdmin from "@/pages/admins/DashBoard";
import PackageAdmin from "@/pages/admins/Package";
import Transaction from "@/pages/admins/Transaction";
import User from "@/pages/admins/User";

import Notification from "./pages/Notification";
import Payment from "./pages/Payment";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* home */}
        <Route element={<HomeLayout />}>
          <Route path="/" element={<Home />} />
        </Route>
        {/* payment */}
        <Route path="/payment" element={<Payment />} />

        {/* login */}
        <Route path="/login" element={<Login />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/verify-otp" element={<Otp />} />

        {/*  */}
        <Route element={<DefaultLayout />}>
          {/* admin */}
          <Route path="/admin/dashboard" element={<DashBoardAdmin />} />
          <Route path="/admin/package" element={<PackageAdmin />} />
          <Route path="/admin/transaction" element={<Transaction />} />
          <Route path="/admin/user" element={<User />} />
          {/* client */}
          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/campaign" element={<Campaign />} />
          <Route path="/campaign/:id" element={<CampaignDetail />} />
          <Route path="/campaign/:id/sendlog" element={<SendLog />} />


          <Route path="/contact-list" element={<ContactList />} />
          <Route path="/contact-list/:id" element={<Contact />} />
          <Route path="/plan" element={<Plan />} />
          <Route path="/template" element={<Template />} />
          <Route path="/template/:id" element={<TemplateDetail />} />

          <Route path="/notification" element={<Notification />} />
          <Route path="/setting" element={<Settings />} />

        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
