/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

import { Routes, Route } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import Jobs from "./pages/Jobs";
import Campaigns from "./pages/Campaigns";
import ScheduleEmail from "./pages/ScheduleEmail";

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/jobs" element={<Jobs />} />
            <Route path="/campaigns" element={<Campaigns />} />
            <Route path="/schedule" element={<ScheduleEmail />} />
        </Routes>
    );
}
