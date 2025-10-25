-- ========================================
-- Reset U - Initial Data Seed
-- ========================================

-- ========================================
-- PUNTOS DE INTERÉS (POI) - UTEC Y ALREDEDORES
-- ========================================
INSERT INTO points_of_interest (name, description, category, latitude, longitude, address, benefits, opening_hours, points_reward, is_active) VALUES
('Parque Kennedy', 'Amplio parque con áreas verdes, ideal para caminar y relajarse', 'GREEN_SPACE', -12.1210, -77.0300, 'Av. Larco, Miraflores', 'Reducción de estrés, conexión con naturaleza, espacio para meditar', '24/7', 15, true),
('Biblioteca UTEC', 'Espacio tranquilo para estudiar y concentrarse', 'STUDY_ZONE', -12.1061, -76.9678, 'Campus UTEC', 'Concentración, ambiente silencioso, recursos académicos', 'Lun-Vie: 7am-10pm, Sáb: 8am-8pm', 10, true),
('Malecón de Barranco', 'Paseo con vista al mar, perfecto para caminar y despejar la mente', 'WALKING_PATH', -12.1467, -77.0250, 'Malecón Barranco', 'Ejercicio ligero, vista relajante, aire fresco', '24/7', 20, true),
('Parque del Amor', 'Zona romántica con vista al océano y arte urbano', 'GREEN_SPACE', -12.1306, -77.0306, 'Malecón Cisneros, Miraflores', 'Inspiración, paz mental, hermosas vistas', '24/7', 15, true),
('Café Blend Station', 'Cafetería aliada con descuentos para estudiantes UTEC', 'PARTNER_CAFE', -12.1072, -76.9689, 'Av. Bohemia 154, Barranco', 'Descuentos estudiantes, espacio coworking, buen café', 'Lun-Dom: 7am-11pm', 25, true),
('Zona Zen Campus UTEC', 'Espacio dedicado al bienestar dentro del campus', 'MEDITATION_SPOT', -12.1063, -76.9680, 'Campus UTEC - Edificio B', 'Meditación guiada, silencio, cojines y alfombras', 'Lun-Vie: 8am-8pm', 30, true),
('Larcomar', 'Centro comercial con vista al mar y múltiples opciones de entretenimiento', 'ENTERTAINMENT', -12.1340, -77.0290, 'Av. José Larco 1301, Miraflores', 'Diversión, socialización, vista panorámica', 'Lun-Dom: 11am-10pm', 10, true),
('Parque de la Reserva - Circuito Mágico del Agua', 'Show de agua y luces, experiencia sensorial única', 'ENTERTAINMENT', -12.0700, -77.0360, 'Jr. Madre de Dios, Cercado de Lima', 'Experiencia visual y auditiva, desconexión total', 'Mié-Dom: 3pm-10:30pm', 35, true);

-- ========================================
-- QUESTS INICIALES
-- ========================================
INSERT INTO quests (title, description, quest_type, target_value, points_reward, difficulty, duration_days, icon_name, is_active) VALUES
('Primera Sesión de Foco', 'Completa tu primera sesión Pomodoro de 25 minutos', 'FOCUS_SESSION', 1, 50, 'EASY', NULL, 'focus', true),
('Caminante Consciente', 'Visita 3 puntos de interés diferentes', 'POI_VISIT', 3, 100, 'MEDIUM', 7, 'walking', true),
('Maestro del Sueño', 'Registra 7 días seguidos durmiendo más de 7 horas', 'SLEEP_GOAL', 7, 150, 'HARD', 7, 'sleep', true),
('Check-in Diario', 'Haz check-in durante 5 días consecutivos', 'DAILY_CHECKIN', 5, 75, 'EASY', 5, 'calendar', true),
('Menos Pantalla', 'Mantén tu screen time bajo 3 horas por día durante 3 días', 'SCREEN_TIME', 3, 120, 'MEDIUM', 3, 'phone_off', true),
('Explorador Sensorial', 'Prueba contenido de los 5 sentidos diferentes', 'SENSORY_EXPLORE', 5, 80, 'MEDIUM', NULL, 'senses', true),
('Gurú del Foco', 'Completa 10 sesiones de foco en total', 'FOCUS_SESSION', 10, 200, 'HARD', 14, 'meditation', true),
('Guerrero Anti-Estrés', 'Mantén tu nivel de estrés por debajo de 5 durante 5 días', 'STRESS_CONTROL', 5, 180, 'HARD', 5, 'heart', true);

-- ========================================
-- RECOMPENSAS (REWARDS)
-- ========================================
INSERT INTO rewards (title, description, reward_type, points_cost, stock, partner_name, terms_conditions, expiration_days, is_active) VALUES
('Café Gratis - Blend Station', 'Un café americano o cappuccino gratis en Blend Station', 'CAFE_DISCOUNT', 150, 50, 'Blend Station', 'Válido de lunes a viernes. No acumulable con otras promociones.', 30, true),
('30min Masaje Relajante', 'Media hora de masaje en Spa Relax UTEC', 'WELLNESS', 500, 10, 'Spa Relax', 'Reservar con 48h de anticipación. Sujeto a disponibilidad.', 60, true),
('2x1 en Cinema UTEC', 'Dos entradas al precio de una en el cine del campus', 'ENTERTAINMENT', 300, 30, 'Cinema UTEC', 'Válido cualquier día de la semana. Excepto estrenos.', 45, true),
('Sesión Yoga Grupal', 'Clase de yoga de 1 hora en el campus', 'WELLNESS', 200, 20, 'UTEC Wellness', 'Horarios: Martes y Jueves 6pm. Traer mat.', 30, true),
('Kit Anti-Estrés', 'Kit con spinner, bola antiestrés y audífonos', 'PHYSICAL_ITEM', 800, 15, 'UTEC Store', 'Recoger en librería del campus. Sujeto a stock.', 90, true),
('Auriculares Cancelación de Ruido', 'Auriculares premium con noise cancelling', 'PHYSICAL_ITEM', 2000, 5, 'Tech Store', 'Garantía 1 año. Recoger en punto autorizado.', 120, true),
('Descuento 20% Restaurante', 'Descuento del 20% en restaurantes aliados', 'FOOD_DISCOUNT', 250, 100, 'Red Gastronómica', 'Válido en 15+ restaurantes. Ver lista en app.', 30, true),
('Clase Meditación Online', 'Acceso a 3 clases de meditación guiada online', 'DIGITAL_CONTENT', 100, 999, 'MindfulU', 'Acceso inmediato vía email. Clases de 20min c/u.', 60, true);

-- ========================================
-- CONTENIDO SENSORIAL (SAMPLES)
-- ========================================
INSERT INTO sensory_content (title, description, sense_type, content_url, duration_seconds, tags, stress_level_min, stress_level_max, is_active) VALUES
-- AUDIO
('Lluvia en Bosque Tropical', 'Sonidos relajantes de lluvia cayendo en un bosque', 'AUDIO', 'https://example.com/audio/rain-forest.mp3', 600, 'naturaleza,lluvia,bosque', 5, 10, true),
('Olas del Mar', 'Sonido continuo de olas rompiendo en la playa', 'AUDIO', 'https://example.com/audio/ocean-waves.mp3', 900, 'mar,olas,playa', 4, 9, true),
('Música Lo-Fi para Estudiar', 'Beats suaves ideales para concentración', 'AUDIO', 'https://example.com/audio/lofi-study.mp3', 1800, 'lofi,estudio,concentración', 3, 7, true),
('Campanas Tibetanas', 'Sonidos de cuencos tibetanos para meditación profunda', 'AUDIO', 'https://example.com/audio/tibetan-bowls.mp3', 720, 'meditación,tibetano,zen', 6, 10, true),

-- VISUAL
('Atardecer en Playa', 'Video time-lapse de un hermoso atardecer', 'VISUAL', 'https://example.com/video/sunset-beach.mp4', 180, 'atardecer,playa,colores', 4, 8, true),
('Cascada en Slow Motion', 'Cascada fluyendo en cámara lenta', 'VISUAL', 'https://example.com/video/waterfall-slow.mp4', 240, 'cascada,agua,naturaleza', 5, 9, true),
('Galaxia y Estrellas', 'Viaje por el espacio exterior', 'VISUAL', 'https://example.com/video/galaxy-stars.mp4', 300, 'espacio,estrellas,cosmos', 3, 7, true),
('Jardín Japonés', 'Recorrido tranquilo por un jardín zen japonés', 'VISUAL', 'https://example.com/video/japanese-garden.mp4', 420, 'jardín,japón,zen', 4, 8, true),

-- TACTILE (descripciones de ejercicios)
('Respiración 4-7-8', 'Técnica de respiración: inhala 4s, retén 7s, exhala 8s', 'TACTILE', 'https://example.com/guide/breathing-478.pdf', 300, 'respiración,relajación,técnica', 5, 10, true),
('Meditación Body Scan', 'Escaneo corporal de pies a cabeza', 'TACTILE', 'https://example.com/guide/body-scan.pdf', 600, 'meditación,cuerpo,conciencia', 4, 9, true),

-- OLFACTORY (recomendaciones)
('Guía Aromaterapia Lavanda', 'Beneficios y usos de la lavanda para reducir estrés', 'OLFACTORY', 'https://example.com/guide/lavender-aromatherapy.pdf', 0, 'lavanda,aromaterapia,calma', 5, 10, true),
('Aceites Esenciales para Dormir', 'Mejores aceites para mejorar calidad de sueño', 'OLFACTORY', 'https://example.com/guide/sleep-oils.pdf', 0, 'aceites,sueño,aroma', 4, 8, true),

-- TASTE (recetas relajantes)
('Receta Té de Manzanilla', 'Preparación perfecta de té relajante', 'TASTE', 'https://example.com/recipe/chamomile-tea.pdf', 0, 'té,manzanilla,calma', 3, 7, true),
('Smoothie Anti-Estrés', 'Batido con ingredientes que reducen cortisol', 'TASTE', 'https://example.com/recipe/antistress-smoothie.pdf', 0, 'smoothie,saludable,nutrición', 4, 8, true);

-- ========================================
-- COMENTARIOS
-- ========================================
COMMENT ON TABLE users IS 'Tabla principal de usuarios del sistema';
COMMENT ON TABLE refresh_tokens IS 'Tokens de refresco para autenticación JWT';
COMMENT ON TABLE user_profiles IS 'Perfiles extendidos de usuarios con preferencias';
COMMENT ON TABLE check_ins IS 'Registro diario de estado de ánimo y métricas';
COMMENT ON TABLE focus_sessions IS 'Sesiones de productividad tipo Pomodoro';
COMMENT ON TABLE points_of_interest IS 'Lugares físicos anti-estrés cerca del campus';
COMMENT ON TABLE quests IS 'Misiones/Hábitos gamificados';
COMMENT ON TABLE rewards IS 'Premios canjeables con puntos';
COMMENT ON TABLE sensory_content IS 'Contenido multimedia para los 5 sentidos';
COMMENT ON TABLE screen_time_entries IS 'Registro de tiempo en pantalla diario';
COMMENT ON TABLE app_usage IS 'Detalle de uso por aplicación';