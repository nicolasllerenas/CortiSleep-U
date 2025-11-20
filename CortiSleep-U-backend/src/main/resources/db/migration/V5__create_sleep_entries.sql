-- Flyway migration: create sleep_entries table for manual sleep records
CREATE TABLE IF NOT EXISTS sleep_entries (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    sleep_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    wake_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    duration_minutes INTEGER NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT fk_sleep_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_sleep_entries_user_created_at ON sleep_entries(user_id, created_at);
