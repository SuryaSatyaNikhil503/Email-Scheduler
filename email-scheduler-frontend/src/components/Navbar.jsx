/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

import { Link, useLocation } from "react-router-dom";
import "./Navbar.css";

export default function Navbar() {
    const { pathname } = useLocation();

    return (
        <nav className="navbar">
            <div className="logo">📧 Email Scheduler Admin</div>
            <ul className="nav-links">
                <li className={pathname === "/" ? "active" : ""}><Link to="/">Dashboard</Link></li>
                <li className={pathname === "/jobs" ? "active" : ""}><Link to="/jobs">Jobs</Link></li>
                <li className={pathname === "/campaigns" ? "active" : ""}><Link to="/campaigns">Campaigns</Link></li>
                <li className={pathname === "/schedule" ? "active" : ""}><Link to="/schedule">Schedule</Link></li>
            </ul>
            <div className="avatar">
                <img src="https://i.pravatar.cc/40" alt="User" />
            </div>
        </nav>
    );
}
