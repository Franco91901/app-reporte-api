# AppReporte API

API REST para el reporte ciudadano de incidentes urbanos (tránsito, infraestructura, limpieza).
Permite a los usuarios registrados crear, buscar y votar incidentes geolocalizados con evidencia fotográfica.

## Arquitectura

```
        Mobile App (Expo)
              │
              ▼
      Spring Boot API (8080)
              │
      ┌───────┼───────┐
      ▼       ▼       ▼
   Auth   Incidentes  Usuarios
              │
              ▼
        MySQL (3306)
        bd_reporte
```

## Stack

| Capa           | Tecnología                              |
|----------------|-----------------------------------------|
| Framework      | Spring Boot 4.0.3                       |
| Lenguaje       | Java 17                                 |
| Seguridad      | JWT (HMAC-SHA256) + Spring Security     |
| ORM            | Spring Data JPA / Hibernate             |
| BD             | MySQL 8                                 |
| Build          | Maven 3.9.12                            |
| Utilidades     | Lombok                                  |
| Validación     | Jakarta Bean Validation                 |

## Endpoints

### Auth (público)

| Método | Ruta                   | Descripción       |
|--------|------------------------|-------------------|
| POST   | /api/auth/registro     | Registrar usuario |
| POST   | /api/auth/login        | Login → JWT       |

### Incidentes (USER / ADMIN)

| Método | Ruta                                    | Descripción                     |
|--------|-----------------------------------------|---------------------------------|
| GET    | /api/incidentes                         | Listar todos                    |
| GET    | /api/incidentes/{id}                    | Obtener por ID                  |
| POST   | /api/incidentes                         | Crear incidente                 |
| DELETE | /api/incidentes/{id}                    | Eliminar incidente              |
| GET    | /api/incidentes/tipo/{tipo}             | Filtrar por tipo                |
| GET    | /api/incidentes/usuario/{usuarioId}     | Filtrar por usuario             |
| POST   | /api/incidentes/{id}/foto               | Subir foto (multipart)          |
| GET    | /api/incidentes/buscar                  | Búsqueda geográfica (Haversine) |
| POST   | /api/incidentes/{id}/votar             | Votar incidente                 |
| GET    | /api/incidentes/{id}/votos             | Total de votos                  |

### Usuarios (USER / ADMIN)

| Método | Ruta                 | Descripción         |
|--------|----------------------|---------------------|
| GET    | /api/usuarios/perfil | Perfil autenticado  |

## Requisitos

- Java 17+
- MySQL 8+
- Maven 3.9+

## Configuración

Base de datos en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bd_reporte
spring.datasource.username=root
spring.datasource.password=mysql
```

## Levantar

```bash
cd AppReporte
./mvnw spring-boot:run
```

La API arranca en `http://localhost:8080`

## Estructura del proyecto

```
src/main/java/com/reporte/demo/
├── config/          # DataInitializer, WebConfig, GlobalExceptionHandler
├── controller/      # AuthController, IncidenteController, UsuarioController
├── dto/             # Request/Response DTOs
├── entity/          # Usuario, Incidente, VotoIncidente, enums
├── mapper/          # Entity ↔ DTO mappers
├── repository/      # JPA repositories
├── security/        # JwtService, JwtFilter, SecurityConfig
└── service/         # AuthService, IncidenteService + implementaciones
```
