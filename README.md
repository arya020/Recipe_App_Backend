# Recipe App Backend

This is the backend service for the **Recipe App**, built using **Java Spring Boot**. It provides REST APIs to fetch and manage recipe data, with an in-memory database for quick access. The backend is deployed on **Render** using **Docker image** and includes **Swagger UI** for API testing.

---

## Deployment

* **Backend URL:** [https://appserver-vtos.onrender.com](https://appserver-vtos.onrender.com)
* <span style="color:red;">⚠️ Note: This backend is deployed on Render's free tier. It may spin down when inactive and can take a few minutes to start up.</span>

* **Swagger UI:** [https://appserver-vtos.onrender.com/swagger-ui/index.html#](https://appserver-vtos.onrender.com/swagger-ui/index.html#)

---

## Features

* Load recipe data into an in-memory database.
* Fetch recipes on the basis of search query.
* Supports sorting.
* Fully documented APIs via Swagger.

---

## Technologies Used

* Java 17
* Spring Boot
* Spring Web
* Spring Validation
* In-Memory Database (H2)
* Swagger UI

---

## API Endpoints

| Method | Endpoint                 | Description               |
| ------ | ------------------------ | ------------------------- |
| GET    | `/all`                   | Get all recipes           |
| GET    | `/{id}`                  | Get recipe by ID          |
| GET    | `/search?q=`         | Search recipes by keyword |


> For a complete list of APIs with request/response models, see [Swagger UI](https://appserver-vtos.onrender.com/swagger-ui/index.html#).

---

