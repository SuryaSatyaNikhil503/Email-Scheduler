import React, { useState } from 'react';
import Layout from "../components/Layout";
import './ScheduleEmail.css';

function ScheduleEmailPage() {
    const [formData, setFormData] = useState({
        subject: '',
        body: '',
        recipients: '',
        scheduledTime: '',
    });

    const [message, setMessage] = useState('');

    const handleChange = e => {
        setFormData(prev => ({
            ...prev,
            [e.target.name]: e.target.value,
        }));
    };

    const handleSubmit = async e => {
        e.preventDefault();

        const emailPayload = {
            subject: formData.subject,
            body: formData.body,
            recipients: formData.recipients.split(',').map(s => s.trim()),
            scheduledTime: formData.scheduledTime ? new Date(formData.scheduledTime) : null,
        };

        try {
            const res = await fetch('http://localhost:8080/api/emails', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(emailPayload),
            });

            const result = await res.json();
            setMessage(result.message || 'Email scheduled!');
            setFormData({ subject: '', body: '', recipients: '', scheduledTime: '' });
        } catch (err) {
            setMessage('Error scheduling email. Try again.');
            console.error(err);
        }
    };

    return (
        <Layout>
            <div className="schedule-form-wrapper">
                <div className="schedule-inner">
                    <h2 className="page-title">Schedule New Email</h2>
                    <form className="email-form" onSubmit={handleSubmit}>
                        <label>
                            Subject
                            <input
                                type="text"
                                name="subject"
                                value={formData.subject}
                                onChange={handleChange}
                                required
                            />
                        </label>

                        <label>
                            Body
                            <textarea
                                name="body"
                                rows="5"
                                value={formData.body}
                                onChange={handleChange}
                                required
                            ></textarea>
                        </label>

                        <label>
                            Recipients (comma-separated)
                            <input
                                type="text"
                                name="recipients"
                                value={formData.recipients}
                                onChange={handleChange}
                                required
                            />
                        </label>

                        <label>
                            Scheduled Time
                            <input
                                type="datetime-local"
                                name="scheduledTime"
                                value={formData.scheduledTime}
                                onChange={handleChange}
                            />
                        </label>

                        <button type="submit">Schedule Email</button>
                        {message && <p className="feedback-msg">{message}</p>}
                    </form>
                </div>
            </div>
        </Layout>
    );
}

export default ScheduleEmailPage;
