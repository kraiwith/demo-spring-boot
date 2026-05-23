# Demo Spring Boot API

โปรเจกต์นี้เป็น REST API ด้วย Spring Boot สำหรับฝึกและโชว์งาน portfolio โดยมีระบบพื้นฐานสำหรับสมัครสมาชิก, login ด้วย JWT, จัดการ user และจัดการ inventory เช่น category, product, SKU และ media

## Tech Stack

- Java 21
- Spring Boot 3.5.8
- Maven Wrapper
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Docker / Docker Compose
- Springdoc OpenAPI / Swagger UI
- Spring Boot Actuator

## Project Structure

```text
src/main/java/com/krai/demo_spring_boot
├── configs        # Spring Security, password encoder, web interceptor config
├── controllers    # REST API controllers
├── dtos           # Request/response DTOs
├── enums          # Status/category/media enums
├── interceptor    # JWT auth interceptor
├── models         # JPA entities
├── repository     # Spring Data repositories
└── utils          # JWT utility
```

## Requirements

- Java 21
- Docker Desktop
- Maven ไม่จำเป็นต้องติดตั้งแยก เพราะใช้ Maven Wrapper (`mvnw` / `mvnw.cmd`)

## Configuration

ค่า default สำหรับ local อยู่ที่ `src/main/resources/application.properties` และสามารถ override ผ่าน environment variables ได้

| Variable | Default | Description |
| --- | --- | --- |
| `PORT` | `8080` | port ของ Spring Boot |
| `DATABASE_URL` | `jdbc:postgresql://localhost:5432/demodb` | JDBC URL ของ PostgreSQL |
| `DATABASE_USERNAME` | `postgres` | database username |
| `DATABASE_PASSWORD` | `postgres` | database password |
| `JWT_SECRET` | demo secret | secret สำหรับ sign JWT |
| `JWT_EXPIRATION` | `86400000` | อายุ token หน่วย milliseconds |

## Run With Docker Compose

รันทั้ง Spring Boot app, PostgreSQL และ Adminer:

```bash
docker compose up -d --build
```

ถ้าเครื่องใช้ Docker Compose รุ่นเก่า:

```bash
docker-compose up -d --build
```

Services:

- API: `http://localhost:8080`
- Adminer: `http://localhost:8081`
- PostgreSQL: `localhost:5432`

ข้อมูลสำหรับเข้า Adminer:

| Field | Value |
| --- | --- |
| System | `PostgreSQL` |
| Server | `postgres` ถ้าเข้าใน Docker network หรือ `localhost` จากเครื่อง host |
| Username | `postgres` |
| Password | `postgres` |
| Database | `demodb` |

หยุด services:

```bash
docker compose down
```

ลบ database volume ด้วย:

```bash
docker compose down -v
```

## Run Locally

ถ้าต้องการรันเฉพาะ database และ Adminer ผ่าน Docker:

```bash
docker compose up -d postgres adminer
```

รัน Spring Boot บน macOS/Linux:

```bash
./mvnw spring-boot:run
```

รัน Spring Boot บน Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

## Useful URLs

- API base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Health check: `http://localhost:8080/actuator/health`
- Adminer: `http://localhost:8081`

## Authentication

Endpoint ที่ไม่ต้องใช้ token:

- `POST /register`
- `POST /login`
- Swagger/OpenAPI paths

Endpoint อื่นต้องส่ง header:

```http
Authorization: Bearer <token>
```

บน Windows PowerShell ถ้า `curl` ถูก alias ไปที่ `Invoke-WebRequest` ให้ใช้ `curl.exe` แทน

## API Examples

### Register

```bash
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Krai",
    "email": "krai@example.com",
    "password": "password123"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "krai@example.com",
    "password": "password123"
  }'
```

ตัวอย่าง response:

```json
{
  "token": "<jwt-token>",
  "message": "Login successful"
}
```

ตั้งค่า token เพื่อใช้ในตัวอย่างถัดไป:

```bash
TOKEN="<jwt-token>"
```

บน PowerShell:

```powershell
$TOKEN = "<jwt-token>"
```

### Get Profile

```bash
curl http://localhost:8080/profile \
  -H "Authorization: Bearer $TOKEN"
```

### Get Users

```bash
curl http://localhost:8080/users \
  -H "Authorization: Bearer $TOKEN"
```

### Create User

```bash
curl -X POST http://localhost:8080/user \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Demo User",
    "email": "demo@example.com",
    "password": "password123"
  }'
```

### Create Category

```bash
curl -X POST http://localhost:8080/inventory/category \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Shoes",
    "parentCategoryId": null,
    "status": "ACTIVE"
  }'
```

### Get Categories

```bash
curl http://localhost:8080/inventory/category \
  -H "Authorization: Bearer $TOKEN"
```

### Create Product

```bash
curl -X POST http://localhost:8080/inventory/product \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Running Shoes",
    "description": "Lightweight running shoes",
    "skus": [
      {
        "name": "RUN-001-BLACK-42",
        "price": 1990.0,
        "stockQuantity": 20,
        "reorderLevel": 5,
        "status": "ACTIVE"
      }
    ],
    "medias": [
      {
        "url": "https://example.com/running-shoes.jpg",
        "type": "IMAGE"
      }
    ],
    "status": "ACTIVE"
  }'
```

### Get Products

```bash
curl http://localhost:8080/inventory/products \
  -H "Authorization: Bearer $TOKEN"
```

## Main Endpoints

| Method | Path | Description | Auth |
| --- | --- | --- | --- |
| `POST` | `/register` | สมัครสมาชิก | No |
| `POST` | `/login` | login และรับ JWT | No |
| `GET` | `/profile` | ดูข้อมูล user จาก token | Yes |
| `GET` | `/users` | ดู user ทั้งหมด | Yes |
| `GET` | `/user/{id}` | ดู user ตาม id | Yes |
| `POST` | `/user` | สร้าง user | Yes |
| `PUT` | `/user/{id}` | แก้ไข user | Yes |
| `DELETE` | `/user/{id}` | ลบ user | Yes |
| `POST` | `/inventory/category` | สร้าง category | Yes |
| `PUT` | `/inventory/category/{id}` | แก้ไข category | Yes |
| `GET` | `/inventory/category` | ดู category ทั้งหมด | Yes |
| `POST` | `/inventory/product` | สร้าง product พร้อม SKU/media | Yes |
| `GET` | `/inventory/products` | ดู product ทั้งหมด | Yes |

## Build And Test

รัน tests:

```bash
./mvnw test
```

บน Windows:

```powershell
.\mvnw.cmd test
```

Build JAR:

```bash
./mvnw package
```

บน Windows:

```powershell
.\mvnw.cmd package
```

## Docker Image

Build image:

```bash
docker build -t demo-spring-boot .
```

Run image โดยต่อ database ที่รันอยู่บน host:

```bash
docker run --rm -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/demodb \
  -e DATABASE_USERNAME=postgres \
  -e DATABASE_PASSWORD=postgres \
  -e JWT_SECRET=ThisIsMyDemoSpringBoot8080SecureSecretKey86400000 \
  demo-spring-boot
```

## Notes

- `docker-compose.yml` ตั้งค่าให้ app ต่อ PostgreSQL ผ่าน host name `postgres` ซึ่งเป็นชื่อ service ใน Docker network
- Database schema ถูกจัดการโดย Hibernate ด้วย `spring.jpa.hibernate.ddl-auto=update`
- JWT ถูกตรวจผ่าน `AuthInterceptor` แม้ `SecurityConfig` จะตั้ง `permitAll` ไว้
- สำหรับ production ควรเปลี่ยน `JWT_SECRET` เป็นค่าที่ปลอดภัยและเก็บผ่าน environment variable
