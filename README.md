# AniMy 🎌 | Anime Listing & Tracking Platform

AniMy is a full-stack anime platform where users can explore trending shows, track what they've watched, rate favorites, and connect with fellow anime lovers.

Built with **Spring Boot** and **React.js**, AniMy features secure JWT authentication, friend systems, and dynamic anime data via the Jikan API.

---

## 🔥 Features

### 🔐 Authentication & Security
- JWT-based Authentication (Access + Refresh Tokens)
- Email verification for new accounts
- Role-based access control (`ROLE_ADMIN`, `ROLE_USER`)
- Secure password hashing, input validation, and CSRF-safe token flow

### 🎥 Anime Listings
- Categorized Sliders:
  - Trending
  - Top Rated
  - This Season
  - Recommended
- Search & Filter functionality
- Anime details with MAL integration via [Jikan API](https://jikan.moe/)

### 🧑‍🤝‍🧑 Social Features
- Friend Requests (Send / Accept / Reject)
- User Profiles with:
  - Watched List
  - Favorite Anime
  - Ratings & Reviews

---

## 🧠 Tech Stack

### ⚙️ Backend (Spring Boot)
- Spring Security 6 (JWT filter chains, role management)
- RESTful APIs
- DTO/Entity Mapping (MapStruct)
- Builder + Factory Patterns
- MySQL with JPA/Hibernate

### 🎨 Frontend (React.js)
- Axios with token interceptors
- Modular component architecture
- Responsive UI (Custom CSS)
- React Router + Protected Routes

---

## 🚀 Deployment

| Layer       | Platform     |
|-------------|--------------|
| Frontend    | Vercel       |
| Backend     | Render / Railway |
| Database    | Hosted MySQL (e.g., PlanetScale, ElephantSQL) |

---

## 🛠️ Setup Instructions

### Backend
```bash
cd backend
./mvnw spring-boot:run
