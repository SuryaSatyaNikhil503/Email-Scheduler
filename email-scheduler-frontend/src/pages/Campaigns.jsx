/*
 * Copyright © 2025 All Rights Reserved | Developer: Nikhil
 */

import React, { useEffect, useState } from 'react';
import Layout from "../components/Layout";
import './Campaigns.css';

function CampaignsPage() {
    const [campaigns, setCampaigns] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/emails/jobs')
            .then(res => res.json())
            .then(data => setCampaigns(data))
            .catch(err => console.error('Error fetching campaigns:', err));
    }, []);

    return (
        <Layout> {/* ✅ Wrap with Layout */}
            <div className="campaigns-container">
                <h2 className="page-title">Email Campaigns</h2>
                <div className="campaigns-table-wrapper">
                    <table className="campaigns-table">
                        <thead>
                        <tr>
                            <th>Subject</th>
                            <th>Recipients</th>
                            <th>Status</th>
                            <th>Scheduled Time</th>
                            <th>Sent</th>
                        </tr>
                        </thead>
                        <tbody>
                        {campaigns.map(campaign => (
                            <tr key={campaign.id}>
                                <td>{campaign.subject || 'N/A'}</td>
                                <td>{campaign.recipients ? campaign.recipients.join(', ') : 'N/A'}</td>
                                <td className={`status-${campaign.status ? campaign.status.toLowerCase() : 'unknown'}`}>
                                    {campaign.status || 'Unknown'}
                                </td>
                                <td>{campaign.scheduledTime ? campaign.scheduledTime.replace('T', ' ').slice(0, 16) : 'N/A'}</td>
                                <td>{campaign.sent ? '✅' : '❌'}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    {campaigns.length === 0 && <p>No campaigns found.</p>}
                </div>
            </div>
        </Layout>
    );
}

export default CampaignsPage;
