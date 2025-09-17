# 🛒 SalesSavvy

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-green?logo=springboot)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue?logo=react)](https://reactjs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Build](https://img.shields.io/badge/Build-Passing-brightgreen)]()
[![Contributions](https://img.shields.io/badge/Contributions-Welcome-orange)]()

SalesSavvy is a full-stack **e-commerce application** built with **Spring Boot, React, and MySQL**.  
It provides a seamless shopping experience for customers while offering powerful admin controls to manage products, orders, and sales reports.

---

## 🚀 Tech Stack

- **Frontend:** React  
- **Backend:** Spring Boot  
- **Database:** MySQL  

---

## ✨ Features

- 🔐 **User Authentication** (Signup, Login, JWT-based security)  
- 🛍️ **Product Catalog** with categories and search  
- 🛒 **Shopping Cart** with real-time updates  
- 📦 **Order Management** (place, track, cancel orders)  
- 💳 **Payment Integration**  
- 🛠️ **Admin Dashboard** for managing users, products, and orders  
- 📊 **Sales Reports** and analytics  

---

## 📂 Project Structure

salessavvy/
├── Frontend/
│ └── salessavvy/ # React frontend
│
└── BackEnd/
└── salessavvy/ # Spring Boot backend



---

## ⚙️ Installation & Setup

### Prerequisites
- [Node.js](https://nodejs.org/) (v14+)
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) (v17+ recommended)
- [MySQL](https://dev.mysql.com/downloads/) (v8+)

### Backend Setup (Spring Boot)
```bash
cd BackEnd/salessavvy
# Configure your MySQL credentials in application.properties
# Example:
# spring.datasource.url=jdbc:mysql://localhost:3306/salessavvy
# spring.datasource.username=root
# spring.datasource.password=yourpassword

./mvnw spring-boot:run

cd Frontend/salessavvy
npm install
npm start

cd Frontend/salessavvy
npm install
npm start
📖 Usage

Visit the app in your browser (http://localhost:3000)

Sign up or log in

Browse products, add to cart, and place orders

Use the Admin Dashboard (accessible for admin users) to:

Manage products

View customer orders

Generate sales reports

🛠️ Future Enhancements

🌐 Multi-language & multi-currency support

📱 Mobile app integration

🤖 AI-based product recommendations

🔔 Email & SMS notifications

🤝 Contributing

Contributions are welcome!
Feel free to fork the repo and create a pull request.

📜 License

This project is licensed under the MIT License – you are free to use, modify, and distribute it.
