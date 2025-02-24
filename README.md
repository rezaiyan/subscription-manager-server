```md
# Subscription Manager Backend 🚀

This is the backend service for the **Subscription Manager** application, built using **Spring Boot, Kotlin, and JPA**.  
It provides a secure **REST API** for managing user subscriptions, handling authentication with Google Sign-In, and calculating total subscription costs.

---

## 📖 Features

✅ **User Authentication & Authorization** via Google OAuth  
✅ **Secure API with JWT Authentication**  
✅ **Subscription Management (CRUD operations)**  
✅ **Calculate Total Monthly & Yearly Subscription Costs**  
✅ **Global Exception Handling for Better Error Responses**  
✅ **Role-Based Access Control (Future Improvement)**  

---

## 🛠️ Tech Stack

| Technology | Description |
|------------|------------|
| **Kotlin** | Primary programming language |
| **Spring Boot** | Backend framework |
| **Spring Security** | Authentication & authorization |
| **Google OAuth** | User authentication |
| **Spring Data JPA** | Database management |
| **H2 / PostgreSQL** | Database (H2 for dev, PostgreSQL for prod) |
| **Gradle** | Dependency management |
| **Docker (Future)** | Containerized deployment |

---

## 🔐 Authentication & Security

- **Google OAuth 2.0** authentication using `Authorization: Bearer <JWT_TOKEN>` in headers.
- Secured endpoints require authentication.
- **Spring Security** manages session-less authentication.

---

## 🚀 Getting Started

### **1️⃣ Clone the Repository**
```bash
git clone https://github.com/rezaiyan/subscription-manager-server.git
cd subscription-manager
```

### **2️⃣ Set Up Environment Variables**
Create a `.env` file and add your **Google OAuth Client ID** and **Database Credentials**:
```env
GOOGLE_CLIENT_ID=your-google-client-id
DATABASE_URL=jdbc:postgresql://localhost:5432/subscription_db
DATABASE_USERNAME=your_db_user
DATABASE_PASSWORD=your_db_password
```

### **3️⃣ Run the Application**
#### **With Gradle**
```bash
./gradlew bootRun
```

#### **With Docker (Future)**
```bash
docker-compose up
```

---

## 📡 API Endpoints

### **1️⃣ Authentication**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/auth/google` | Redirects to Google OAuth login |
| `GET` | `/auth/user` | Returns authenticated user info |

### **2️⃣ Subscription Management**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/api/subscriptions` | Get all subscriptions for logged-in user |
| `GET` | `/api/subscriptions/{id}` | Get a single subscription by ID |
| `POST` | `/api/subscriptions` | Create a new subscription |
| `DELETE` | `/api/subscriptions/{id}` | Delete a subscription |

### **3️⃣ Subscription Totals**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/api/subscriptions/total` | Get total monthly & yearly subscription costs |

---

## ⚠️ Error Handling
Errors follow a consistent format:
```json
{
  "message": "User not found",
  "errorCode": "USER_NOT_FOUND"
}
```
| HTTP Code | Error | Description |
|-----------|-------|------------|
| `400` | `USER_NOT_FOUND` | User does not exist |
| `401` | `UNAUTHORIZED` | Invalid or missing token |
| `403` | `ACCESS_DENIED` | User not allowed to access resource |
| `404` | `NOT_FOUND` | Subscription not found |

---

## 📌 Testing the API
- Use **Postman** or **cURL**:
```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/subscriptions
```

---

## ✅ Future Enhancements
- 🛠 **Implement Role-Based Access Control (RBAC)**
- 🔐 **Enhance Security with Refresh Tokens**
- 📊 **Add Subscription Statistics & Reports**
- 🐳 **Docker & Kubernetes Deployment**
- ✅ **GraphQL Support (Optional)**

---

## 🤝 Contributing
🚀 Contributions are welcome! Feel free to:
1. Fork the repo
2. Create a feature branch (`git checkout -b feature-branch`)
3. Commit changes (`git commit -m "Add feature"`)
4. Push to your branch (`git push origin feature-branch`)
5. Open a PR 🎉

---

## 📝 License
This project is licensed under the **MIT License**. Feel free to use and modify.

---

## 💡 Credits & Contact
Created by [Ali Rezaiyan](https://github.com/rezaiyan).  
For questions, feel free to open an **Issue** or reach out! 📩

---

## 🚀 Happy Coding! 🎉