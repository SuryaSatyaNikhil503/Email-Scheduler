/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import { Pie, Line } from "react-chartjs-2";
import {
    Chart as ChartJS,
    ArcElement,
    CategoryScale,
    LinearScale,
    LineElement,
    PointElement,
    Tooltip,
    Legend
} from "chart.js";
import "./Dashboard.css";

ChartJS.register(
    ArcElement,
    CategoryScale,
    LinearScale,
    LineElement,
    PointElement,
    Tooltip,
    Legend
);

export default function Dashboard() {
    const [stats, setStats] = useState({});
    const [jobs, setJobs] = useState([]);
    const [dailyStats, setDailyStats] = useState([]);
    const [weeklyStats, setWeeklyStats] = useState([]);
    const [lineChartMode, setLineChartMode] = useState("daily");

    useEffect(() => {
        fetchStats();
        fetchJobs();
        fetchDailyStats();
        fetchWeeklyStats();
    }, []);

    const fetchStats = async () => {
        const res = await fetch("http://localhost:8080/api/emails/stats");
        const data = await res.json();
        setStats(data);
    };

    const fetchJobs = async () => {
        const res = await fetch("http://localhost:8080/api/emails/jobs");
        const data = await res.json();
        setJobs(data.reverse());
    };

    const fetchDailyStats = async () => {
        const res = await fetch("http://localhost:8080/api/stats/email/daily");
        const data = await res.json();
        setDailyStats(data);
    };

    const fetchWeeklyStats = async () => {
        const res = await fetch("http://localhost:8080/api/stats/email/weekly");
        const data = await res.json();
        setWeeklyStats(data);
    };

    const getLineChartData = () => {
        const source = lineChartMode === "daily" ? dailyStats : weeklyStats;
        const labels = source.map(item => item.date);
        const counts = source.map(item => item.count);

        return {
            labels,
            datasets: [
                {
                    label: "Emails Sent",
                    data: counts,
                    fill: false,
                    borderColor: "#2196f3",
                    backgroundColor: "#2196f3",
                    pointBackgroundColor: "#2196f3",
                    tension: 0.3
                }
            ]
        };
    };

    const lineChartOptions = {
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    precision: 0 // show whole numbers only
                }
            }
        },
        plugins: {
            legend: {
                display: true,
                position: 'top'
            }
        },
        responsive: true,
        maintainAspectRatio: false
    };

    const pieData = {
        labels: ["Sent", "Scheduled", "Failed"],
        datasets: [
            {
                label: "Status Distribution",
                data: [stats.totalSent || 0, stats.totalScheduled || 0, stats.totalFailed || 0],
                backgroundColor: ["#4caf50", "#ff9800", "#f44336"],
                borderWidth: 1
            }
        ]
    };

    const scheduledJobs = jobs.filter(j => j.status === "PENDING").slice(0, 5);
    const sentJobs = jobs.filter(j => j.status === "SENT").slice(0, 5);

    return (
        <Layout>
            <h1 className="title">Dashboard</h1>

            <div className="cards">
                <div className="card"><h2>{stats.totalSent ?? 0}</h2><p>Total Emails Sent</p></div>
                <div className="card"><h2>{stats.totalScheduled ?? 0}</h2><p>Total Scheduled</p></div>
                <div className="card"><h2>{stats.sentToday ?? 0}</h2><p>Emails Sent Today</p></div>
                <div className="card"><h2>{stats.totalFailed ?? 0}</h2><p>Emails Failed</p></div>
            </div>

            <div className="graph-section">
                <h2>Email Status Distribution</h2>
                <Pie data={pieData} />
            </div>

            <div className="graph-section">
                <h2>Emails Sent Over Time</h2>
                <div className="toggle-buttons">
                    <button
                        className={lineChartMode === "daily" ? "active" : ""}
                        onClick={() => setLineChartMode("daily")}
                    >
                        Daily
                    </button>
                    <button
                        className={lineChartMode === "weekly" ? "active" : ""}
                        onClick={() => setLineChartMode("weekly")}
                    >
                        Weekly
                    </button>
                </div>
                <div style={{ height: "300px" }}>
                    <Line data={getLineChartData()} options={lineChartOptions} />
                </div>
            </div>

            <div className="tables">
                <div>
                    <h3>Latest 5 Scheduled Jobs</h3>
                    <table>
                        <thead>
                        <tr><th>Job ID</th><th>Subject</th><th>Recipients</th><th>Scheduled At</th><th>Status</th></tr>
                        </thead>
                        <tbody>
                        {scheduledJobs.map(job => (
                            <tr key={job.id}>
                                <td>{job.id}</td>
                                <td>{job.subject}</td>
                                <td>{job.recipients.length}</td>
                                <td>{job.scheduledTime?.replace("T", " ")}</td>
                                <td>{job.status}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                <div>
                    <h3>Latest 5 Sent Jobs</h3>
                    <table>
                        <thead>
                        <tr><th>Job ID</th><th>Subject</th><th>Recipients</th><th>Created At</th><th>Status</th></tr>
                        </thead>
                        <tbody>
                        {sentJobs.map(job => (
                            <tr key={job.id}>
                                <td>{job.id}</td>
                                <td>{job.subject}</td>
                                <td>{job.recipients.length}</td>
                                <td>{job.createdTime?.replace("T", " ")}</td>
                                <td>{job.status}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </Layout>
    );
}
