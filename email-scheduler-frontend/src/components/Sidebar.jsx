/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

import { Link, useLocation } from "react-router-dom";
import "./Sidebar.css";

export default function Sidebar() {
    const location = useLocation();

    const menuItems = [
        { name: "Dashboard", path: "/" },
        { name: "Campaigns", path: "/campaigns" },
        { name: "Schedule Email", path: "/schedule" },
    ];

    return (
        <div className="sidebar">
            <h2 className="logo">MailBuddy</h2>
            <ul className="nav-list">
                {menuItems.map((item) => (
                    <li
                        key={item.path}
                        className={location.pathname === item.path ? "active" : ""}
                    >
                        <Link to={item.path}>{item.name}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}
