-- Flyway migration: remove sleep_hours column from check_ins because checkins now reserve POIs
ALTER TABLE check_ins DROP COLUMN IF EXISTS sleep_hours;
