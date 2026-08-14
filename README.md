# My Notes App Backend (Spring Boot)

Backend API for a notes application built with **Spring Boot + Kotlin**, secured with **JWT authentication**, and powered by **MongoDB**.

## Tech Stack

- Kotlin
- Spring Boot
- Spring Security (stateless JWT auth)
- Spring Data MongoDB
- JJWT (token generation/validation)
- Gradle (Kotlin DSL)
- Java 17

## Features

- User registration and login
- Access token + refresh token flow
- Refresh token rotation with hashed token storage
- Auth-protected notes APIs
- Input validation for auth and notes payloads

## Project Structure

- `controller/` - REST APIs (`/auth`, `/notes`)
- `security/` - JWT service, auth filter, security config
- `database/model/` - MongoDB document models
- `database/repository/` - Spring Data repositories

## Environment Variables

Configure these before running:

- `MONGO_DB_CONNECTION_STRING`
- `JWT_SECRET_BASE64`

`src/main/resources/application.properties` uses:

- `spring.mongodb.uri=${MONGO_DB_CONNECTION_STRING}`
- `jwt.secret=${JWT_SECRET_BASE64}`
- `server.port=8080`

## API Endpoints

### Auth

- `POST /auth/register`
- `POST /auth/login`
- `POST /auth/refresh`

### Notes (requires `Authorization: Bearer <access_token>`)

- `POST /notes` - create/update note
- `GET /notes` - get current user notes
- `DELETE /notes/{id}` - delete note by id (owner only)

## Run Locally
