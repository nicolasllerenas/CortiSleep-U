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