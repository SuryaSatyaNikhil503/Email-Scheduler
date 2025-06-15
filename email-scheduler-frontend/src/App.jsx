/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Jobs from './pages/Jobs';
import Campaigns from './pages/Campaigns';
import ScheduleEmail from './pages/ScheduleEmail';
import './App.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/dashboard" />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/jobs" element={<Jobs />} />
                <Route path="/campaigns" element={<Campaigns />} />
                <Route path="/schedule" element={<ScheduleEmail />} />
            </Routes>
        </Router>
    );
}

export default App;