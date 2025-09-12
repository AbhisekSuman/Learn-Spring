import React from "react";
import { Route, Routes, Navigate } from "react-router-dom";
import RegisterForm from "./src/pages/Register";
import LoginPage from "./src/pages/Login";
import Dashboard from "./src/pages/Dashboard";
import CustomerHomePage from "./src/pages/CustomerHomePage";

const AppRoutes = () => {
    return (
        <>
            <Routes>
                <Route exact path="/register" element={<RegisterForm role={"CUSTOMER"} />} />
                <Route exact path="/admin/register" element={<RegisterForm role={"ADMIN"} />} />
                <Route exact path="/login" element={<LoginPage />} />
                <Route exact path="/customer/dashboard" element={<CustomerHomePage />} />
                <Route path="/" element={<Navigate to="/login" />} />
            </Routes>
        </>
    );
};


export default AppRoutes;