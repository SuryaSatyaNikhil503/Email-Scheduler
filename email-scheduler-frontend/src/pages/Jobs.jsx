/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import "./Jobs.css";

export default function Jobs() {
    const [jobs, setJobs] = useState([]);

    useEffect(() => {
        fetchJobs();
    }, []);

    const fetchJobs = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/emails/jobs?status=PENDING");
            const data = await res.json();
            setJobs(data);
        } catch (error) {
            console.error("Error fetching jobs:", error);
        }
    };

    return (
        <Layout>
            <h1 className="jobs-title">All Scheduled Jobs</h1>

            <table className="jobs-table">
                <thead>
                <tr>
                    <th>Job ID</th>
                    <th>Subject</th>
                    <th>Recipients</th>
                    <th>Scheduled Time</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {jobs.length === 0 ? (
                    <tr>
                        <td colSpan="5" style={{ textAlign: "center" }}>No scheduled jobs found.</td>
                    </tr>
                ) : (
                    jobs.map(job => (
                        <tr key={job.id}>
                            <td>{job.id}</td>
                            <td>{job.subject}</td>
                            <td>{job.recipients?.join(", ") || "N/A"}</td>
                            <td>{job.scheduledTime?.replace("T", " ")}</td>
                            <td>{job.status}</td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </Layout>
    );
}
