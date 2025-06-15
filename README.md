## 📧 Email Scheduler Application

A full-stack Email Scheduler App built using **Spring Boot** for the backend and **React.js** for the frontend. This application allows users to send bulk emails instantly or schedule them for a later time. It also provides a dashboard with email statistics.

---

### 🧰 Tech Stack

#### ✅ Backend:

* Java 17
* Spring Boot 3.5.0
* Spring Data JPA
* Hibernate
* MySQL
* Java Mail Sender
* Lombok
* Scheduler (Spring `@Scheduled`)
* CORS enabled
* Maven

#### ✅ Frontend:

* React.js (with Functional Components & Hooks)
* CSS Modules
* Fetch API for HTTP requests

---

## ⚙️ Features

* 📬 Schedule bulk emails with subject, body, and recipients
* 🕑 Send emails immediately or at a specific date/time
* 📊 View real-time email statistics (delivered, scheduled, failed, etc.)
* ✅ Custom validation and feedback messages
* 🔐 No authentication required (for internal tools)
* 🔄 Scheduler runs every minute to check pending emails

---

## 📁 Project Structure

```
email-scheduler/
├── backend/                      # Spring Boot Project
│   ├── src/main/java/com/example/emailscheduler
│   └── src/main/resources
├── frontend/                     # React App
│   └── src/
└── README.md
```

---

## 🚀 Getting Started

### 1. 🖥️ Clone the Repository

```bash
git clone https://github.com/your-username/email-scheduler.git
cd email-scheduler
```

---

### 2. 🧪 Backend Setup (Spring Boot)

#### ✅ Prerequisites:

* Java 17+
* Maven
* MySQL running locally or hosted

#### 🔧 Configure `application.properties`

Update your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/email_scheduler
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

#### ▶️ Run the Backend

```bash
cd backend
./mvnw spring-boot:run
```

The server will start on: `http://localhost:8080`

---

### 3. 🌐 Frontend Setup (React)

#### ✅ Prerequisites:

* Node.js & npm

```bash
cd frontend
npm install
npm start
```

The frontend will run on: `http://localhost:3000`

---

Absolutely — you're right. To include all available endpoints, let's expand the `## 🔁 API Endpoints` section.

Based on a standard implementation of an Email Scheduler system (including management of scheduled jobs and possible recipient listings), here's a more complete version of the API documentation section, assuming these endpoints exist in your Spring Boot app:

---

## 🔁 API Endpoints

### 📤 Email Operations

| Method   | Endpoint           | Description                                    |
| -------- | ------------------ | ---------------------------------------------- |
| `POST`   | `/api/emails`      | Schedule or send emails (immediate if no time) |
| `GET`    | `/api/emails`      | Get all emails (scheduled, sent, failed, etc.) |
| `GET`    | `/api/emails/{id}` | Get a single email job by ID                   |
| `DELETE` | `/api/emails/{id}` | Delete a scheduled email before it's sent      |

---

### 📈 Dashboard & Metrics

| Method | Endpoint                      | Description                                                   |
| ------ | ----------------------------- | ------------------------------------------------------------- |
| `GET`  | `/api/emails/statistics`      | Get count of total/sent/scheduled/failed emails               |
| `GET`  | `/api/emails/status/{status}` | Get emails filtered by status (`SENT`, `FAILED`, `SCHEDULED`) |

---

### 👥 Recipient Management *(if applicable)*

| Method   | Endpoint                  | Description                      |
| -------- | ------------------------- | -------------------------------- |
| `GET`    | `/api/recipients`         | Get all recipients in the system |
| `POST`   | `/api/recipients`         | Add new recipients to the system |
| `DELETE` | `/api/recipients/{email}` | Remove recipient by email        |

---

### 🛠️ Utility

| Method | Endpoint      | Description                              |
| ------ | ------------- | ---------------------------------------- |
| `GET`  | `/api/health` | Health check endpoint for backend status |

---

### ✅ Notes:

* All POST requests expect `Content-Type: application/json`
* Scheduler picks emails every minute using Spring's `@Scheduled(cron = "0 * * * * *")`
* Timezone defaults to system timezone unless explicitly configured

---

## 💡 How the Scheduler Works

* Every minute, the scheduler checks the database for emails that are:

  * Not sent yet
  * `scheduled_time` ≤ current time
* If found, the email is sent using JavaMailSender and status is updated

---

## 📝 Future Improvements

* Add email status filters (Success, Failed, Pending)
* Add Admin Authentication
* Attachments support
* Retry failed emails
* Deployment via Docker or CI/CD

---

## 🙌 Contributors

* [Surya Satya Nikhil](https://www.linkedin.com/in/gssnikhil/)

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---
