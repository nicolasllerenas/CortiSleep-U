# CortiSleep-U Backend (Spring Boot, Java 17)

Plataforma de bienestar universitario enfocada en estrés y sueño. MVP expone autenticación, perfiles, check-in diario y focus sessions. Arquitectura lista para crecer (gamificación, analytics, notificaciones, POI, screen-time, senses).

## Stack
- Java 17 (compilación con Maven Toolchains)
- Spring Boot 3, Spring Security + JWT
- PostgreSQL + Flyway (db/migration)
- Lombok para DTOs/entidades (compila con Java 17)
- OpenAPI (springdoc) / Swagger UI
- JPA/Hibernate

## Requisitos
- JDK 17 instalado (Temurin 17 recomendado)
- Maven (wrapper incluido `./mvnw`)
- PostgreSQL en local o en contenedor

La compilación usa Maven Toolchains (recomendado para equipos/CI):

1) Instalar Temurin 17:

```
brew tap homebrew/cask-versions
brew install --cask temurin@17
```

2) Crear `~/.m2/toolchains.xml` (ajusta `jdkHome` si difiere):

```
<?xml version="1.0" encoding="UTF-8"?>
<toolchains xmlns="http://maven.apache.org/TOOLCHAINS/1.1.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/TOOLCHAINS/1.1.0 https://maven.apache.org/xsd/toolchains-1.1.0.xsd">
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>17</version>
      <vendor>any</vendor>
    </provides>
    <configuration>
      <jdkHome>/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

Comprobar Toolchains:

```
./mvnw -X -DskipTests package | grep -i toolchain
```

## Perfiles y variables
Archivos:
- `src/main/resources/application.yml` (base)
- `src/main/resources/application-dev.yml`
- `src/main/resources/application-prod.yml`

Variables típicas (entorno o `application-dev.yml`):
- `spring.datasource.url=jdbc:postgresql://localhost:5432/cortisleep`
- `spring.datasource.username=postgres`
- `spring.datasource.password=postgres`
- `jwt.secret=una_clave_segura_de_>=32bytes`
- `jwt.expiration=3600000`
- `cors.allowed-origins=http://localhost:3000,http://localhost:8081`

## Construir y ejecutar
- Build sin tests (salta compilación y ejecución de tests):

```
./mvnw clean package -Dmaven.test.skip=true
```

- Ejecutar con Spring Boot (perfil dev):

```
./mvnw -Dmaven.test.skip=true -Dspring-boot.run.profiles=dev spring-boot:run
```

- Ejecutar el JAR:

```
java -jar target/cortisleep-backend-0.0.1-SNAPSHOT.jar
```

Swagger/OpenAPI:
- `http://localhost:8080/swagger-ui.html` o `/swagger-ui/index.html`
- JSON: `/v3/api-docs`

## Estructura (rápida)
- `src/main/java/com/utec/resetu`
  - `auth`: DTOs, service, controller, security (JWT, filtros)
  - `profile`: DTOs, service, controller, mapper, domain
  - `checkin`: DTOs, service, controller, domain
  - `focus`: DTOs, service, controller, domain
  - `gamification`: domain + controllers base
  - `poi`, `screentime`, `senses`: bases creadas
  - `shared`: DTOs comunes, seguridad (`CurrentUserService`), excepciones, paging

Notas:
- Mappers manuales con `@Component` (no MapStruct) para robustez con Java 17.
- Lombok en DTOs/entidades para reducir boilerplate.

## Endpoints principales (MVP)

### Auth (`/api/v1/auth`)
- `POST /register` – Body: `{ email, password, firstName, lastName }`
- `POST /login` – Body: `{ email, password }`
- `POST /refresh` – Body: `{ refreshToken }`
- `GET /me` – Header: `Authorization: Bearer <JWT>`

### Profile (`/api/v1/profile`)
- `GET /me` – Perfil del usuario autenticado.
- `POST /` – Crear/actualizar perfil. Body: `ProfileRequest` (alias, faculty, semester, career, bio, avatarUrl, birthDate, stressLevel, sleepGoalHours, screenTimeLimitMinutes, preferredSenseType)
- `PUT /` – Update parcial (nulls no sobreescriben).

### Check-in (`/api/v1/checkins`)
- `POST /` – Body: `CheckInRequest { locationName, latitude, longitude, moodScore, stressLevel, energyLevel, notes }`
- `GET /` – Lista paginada. Res: `PageResponse<CheckInResponse>`
- `GET /stats` – Res: `CheckInStatsDto` (totales, promedios, recientes, última vez)

### Focus Sessions (`/api/v1/focus`)
- `POST /` – Body: `FocusSessionRequest { durationMinutes, sessionType, taskDescription }`
- `POST /{id}/complete` – Completa sesión y suma puntos.
- `GET /` – Lista paginada de sesiones.
- `GET /stats` – `FocusStatsDto { totalSessions, completedSessions, totalMinutes, last7DaysSessions, completionRate }`

## Ejemplos cURL

Registro:
```
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{ "email":"user@utec.edu", "password":"Secret123", "firstName":"Nico", "lastName":"Lerenas" }'
```

Login:
```
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{ "email":"user@utec.edu", "password":"Secret123" }'
```

Get profile (JWT):
```
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/v1/profile/me
```

Nuevo check-in:
```
curl -X POST http://localhost:8080/api/v1/checkins \
  -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" \
  -d '{ "locationName":"Biblioteca", "latitude":-12.1, "longitude":-77.0, "moodScore":4, "stressLevel":2, "energyLevel":3, "notes":"Estudiando" }'
```

## Migraciones (Flyway)
`src/main/resources/db/migration`: `V1__init_schema.sql`, `V2__insert_initial_data.sql`, `V3__add_gamification.sql`

## Seguridad
- JWT para endpoints autenticados.
- Minimizar PII (email + alias para MVP).
- CORS en `CorsConfig` (ajustar `allowed-origins` para móvil/web).

## TODOs
- Calcular `averageEnergyLevel` en `CheckInStatsDto`.
- Endpoint `me/weekly` (panel semanal del usuario).
- Agregados semanales por `scope=utec` sin PII.
- Completar reglas de gamificación (puntos por eventos).
- Ajustar CORS para orígenes móviles específicos.
- Añadir Testcontainers para integración (opcional posterior).

## Tips de desarrollo
- Build rápido: `./mvnw clean package -Dmaven.test.skip=true`
- Run rápido: `./mvnw -Dmaven.test.skip=true -Dspring-boot.run.profiles=dev spring-boot:run`
- JAR: `java -jar target/cortisleep-backend-0.0.1-SNAPSHOT.jar`
- Health (si actuator): `/actuator/health`

