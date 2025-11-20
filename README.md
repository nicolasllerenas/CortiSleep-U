# CortiSleep-U

CortiSleep U – Backend (MVP)

Plataforma de bienestar universitario enfocada en estrés y sueño. Arquitectura de microservicios en Spring Boot con Maven, JWT, PostgreSQL y mensajería opcional.

🔭 Objetivo del MVP
	•	Auth & Profile para cuentas mínimas (email + alias; sin PII sensible por defecto).
	•	Check-in diario (estrés 1–5, horas de sueño, hora de última pantalla).
	•	Recomendaciones (micro-intervenciones de 1–3 min).
	•	Analítica agregada (tendencias anónimas por semana).
	•	Notificaciones (recordatorios diarios; opcional en MVP 1).

🏗️ Arquitectura (resumen)
	•	Spring Boot 3, Java 17+, Maven.
	•	Spring Security + JWT (opcional OAuth2 con Google).
	•	PostgreSQL (única instancia o por servicio según recursos).
	•	Flyway para migraciones.
	•	MapStruct / Lombok (boilerplate).
	•	OpenAPI (springdoc) por servicio (/swagger-ui.html).
	•	Eureka + Spring Cloud Gateway.
	•	Test: JUnit5, Testcontainers.




🧩 Roadmap técnico sugerido
	•	S1–S2: auth, profile, checkin, gateway, discovery, config.
	•	S3–S4: recommendation (reglas), analytics (materialized views).
	•	S5–S6: notificaciones (scheduler Redis/Quartz) + panel admin básico.
	•	S7+: multi-tenant por universidad, OAuth2 Google, métricas Prometheus.

⸻

✅ Definición de “Hecho” (MVP)
	•	Registro, login y refresh operativos.
	•	Crear/consultar check-ins con token.
	•	Recomendaciones por reglas funcionando.
	•	Panel semanal del usuario (endpoint me/weekly) OK.
	•	Agregados semanales por “scope=utec” sin PII.
	•	OpenAPI publicado y tests básicos con Testcontainers.

⸻

🧯 Riesgos y mitigaciones
	•	Sobrecarga de endpoints → Gateway con rate-limit; cache de recomendaciones.
	•	Datos sensibles → minimizar PII; cifrado en reposo; retención limitada.
	•	Costos → un solo PostgreSQL para MVP; migrar a per-service si escala.

---

Ejecución local (dev)

Rápido — pasos para levantar el proyecto en modo desarrollo en tu máquina.

Requisitos previos
- Java 17+ (JDK)
- Maven (si no usas el wrapper, aunque el proyecto incluye `./mvnw`)
- Node 18+ y npm (o pnpm/yarn si prefieres)
- Docker (opcional, recomendado para PostgreSQL local)

1) Base de datos (PostgreSQL)

Opciones:
- Usar Docker Compose (recomendado para desarrollo): desde la carpeta `CortiSleep-U-backend` hay un `docker-compose.dev.yml` preparado.

	cd CortiSleep-U-backend
	docker compose -f docker-compose.dev.yml up -d

- Si prefieres una instalación local de PostgreSQL, crea una base de datos y actualiza las variables de conexión en los `application-*.yml` o exporta variables de entorno (ver sección Configuración abajo).

2) Backend (Spring Boot)

Desde la raíz del backend:

	cd CortiSleep-U-backend
	# usa el wrapper incluido
	./mvnw -Dspring-boot.run.profiles=dev spring-boot:run

Esto levanta la API en http://localhost:8080 por defecto (puede cambiar según `application-dev.yml`). Las migraciones Flyway se ejecutan automáticamente al iniciar.

Comandos útiles (backend):
- Compilar y ejecutar tests: `./mvnw clean verify`
- Empaquetar jar: `./mvnw -DskipTests package`

3) Frontend (Vite + React + TypeScript)

	cd cortisleep-u-frontend
	npm install
	npm run dev

El servidor de desarrollo de Vite arranca normalmente en http://localhost:5173 y hará HMR (hot reload) mientras editas.

Comandos útiles (frontend):
- Build producción: `npm run build`
- Ejecutar preview del build: `npm run preview`

4) Flujo típico (levantar todo)

- Levanta Postgres (docker compose o instancia local).
- Inicia backend: `./mvnw -Dspring-boot.run.profiles=dev spring-boot:run`.
- Inicia frontend: desde `cortisleep-u-frontend` ejecutar `npm run dev`.

5) Variables de configuración

El backend lee las propiedades desde `src/main/resources/application*.yml`. Para desarrollo se usa `application-dev.yml` cuando pasas el perfil `dev`. Puedes exportar variables de entorno para credenciales y conexiones (por ejemplo `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`).

6) Troubleshooting rápido
- Si el backend no se conecta a la base de datos, revisa que PostgreSQL esté en ejecución y que las credenciales y el host/puerto en `application-dev.yml` coincidan.
- Si Vite no arranca por falta de dependencias, corre `npm install` dentro de `cortisleep-u-frontend`.
- Si ves CORS o 401 en llamadas desde frontend, asegúrate de que estás enviando el token (login) o que el backend está en modo `dev` con la configuración esperada.

7) Tests

- Backend: `./mvnw test` o `./mvnw verify` para ejecutar con integración mínima.
- Frontend: si hay tests configurados, `npm test` (o ver `package.json`).

Notas
- El repositorio incluye configuraciones y ejemplos para local dev (docker-compose, profiles de Spring). Si quieres, puedo agregar un script `dev.sh` que arranque Postgres + backend + frontend en una sola orden.

