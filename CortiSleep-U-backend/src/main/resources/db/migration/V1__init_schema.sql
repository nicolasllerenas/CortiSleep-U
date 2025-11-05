-- ========================================
-- Reset U - Initial Database Schema
-- ========================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ========================================
-- USERS TABLE
-- ========================================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    email_verified BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP,
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_is_active ON users(is_active);

-- ========================================
-- REFRESH TOKENS TABLE
-- ========================================
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token ON refresh_tokens(token);
CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_expires_at ON refresh_tokens(expires_at);

-- ========================================
-- USER PROFILES TABLE
-- ========================================
CREATE TABLE user_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    alias VARCHAR(50),
    faculty VARCHAR(50),
    semester INTEGER,
    career VARCHAR(100),
    bio TEXT,
    avatar_url VARCHAR(500),
    birth_date DATE,
    total_points INTEGER NOT NULL DEFAULT 0,
    stress_level INTEGER CHECK (stress_level BETWEEN 1 AND 10),
    sleep_goal_hours DECIMAL(3,1) DEFAULT 8.0,
    screen_time_limit_minutes INTEGER DEFAULT 180,
    preferred_sense_type VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_profiles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_profiles_user_id ON user_profiles(user_id);

-- ========================================
-- CHECK-INS TABLE
-- ========================================
CREATE TABLE check_ins (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    stress_level INTEGER NOT NULL CHECK (stress_level BETWEEN 1 AND 10),
    sleep_hours DECIMAL(3,1) NOT NULL,
    sleep_quality INTEGER CHECK (sleep_quality BETWEEN 1 AND 5),
    screen_time_minutes INTEGER,
    mood VARCHAR(20),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_checkins_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_checkins_user_date UNIQUE (user_id, date)
);

CREATE INDEX idx_checkins_user_id ON check_ins(user_id);
CREATE INDEX idx_checkins_date ON check_ins(date);
CREATE INDEX idx_checkins_user_date ON check_ins(user_id, date);

-- ========================================
-- FOCUS SESSIONS TABLE
-- ========================================
CREATE TABLE focus_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    duration_minutes INTEGER NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT false,
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP,
    session_type VARCHAR(20) NOT NULL DEFAULT 'POMODORO',
    task_description VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_focus_sessions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_focus_sessions_user_id ON focus_sessions(user_id);
CREATE INDEX idx_focus_sessions_started_at ON focus_sessions(started_at);

-- ========================================
-- POINTS OF INTEREST (POI) TABLE
-- ========================================
CREATE TABLE points_of_interest (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(30) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    address VARCHAR(200),
    image_url VARCHAR(500),
    benefits TEXT,
    opening_hours VARCHAR(100),
    contact_info VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT true,
    points_reward INTEGER NOT NULL DEFAULT 10,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_poi_category ON points_of_interest(category);
CREATE INDEX idx_poi_location ON points_of_interest(latitude, longitude);
CREATE INDEX idx_poi_is_active ON points_of_interest(is_active);

-- ========================================
-- USER POI VISITS TABLE
-- ========================================
CREATE TABLE user_poi_visits (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    poi_id BIGINT NOT NULL,
    visited_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    points_earned INTEGER NOT NULL DEFAULT 10,
    duration_minutes INTEGER,
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    CONSTRAINT fk_poi_visits_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_poi_visits_poi FOREIGN KEY (poi_id) REFERENCES points_of_interest(id) ON DELETE CASCADE
);

CREATE INDEX idx_poi_visits_user_id ON user_poi_visits(user_id);
CREATE INDEX idx_poi_visits_poi_id ON user_poi_visits(poi_id);
CREATE INDEX idx_poi_visits_visited_at ON user_poi_visits(visited_at);

-- ========================================
-- QUESTS TABLE
-- ========================================
CREATE TABLE quests (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    quest_type VARCHAR(30) NOT NULL,
    target_value INTEGER NOT NULL,
    points_reward INTEGER NOT NULL,
    difficulty VARCHAR(20) NOT NULL,
    duration_days INTEGER,
    is_active BOOLEAN NOT NULL DEFAULT true,
    icon_name VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_quests_type ON quests(quest_type);
CREATE INDEX idx_quests_is_active ON quests(is_active);

-- ========================================
-- USER QUESTS TABLE
-- ========================================
CREATE TABLE user_quests (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    quest_id BIGINT NOT NULL,
    current_progress INTEGER NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS',
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    CONSTRAINT fk_user_quests_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_quests_quest FOREIGN KEY (quest_id) REFERENCES quests(id) ON DELETE CASCADE,
    CONSTRAINT uk_user_quests_user_quest UNIQUE (user_id, quest_id)
);

CREATE INDEX idx_user_quests_user_id ON user_quests(user_id);
CREATE INDEX idx_user_quests_quest_id ON user_quests(quest_id);
CREATE INDEX idx_user_quests_status ON user_quests(status);

-- ========================================
-- REWARDS TABLE
-- ========================================
CREATE TABLE rewards (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    reward_type VARCHAR(30) NOT NULL,
    points_cost INTEGER NOT NULL,
    stock INTEGER,
    image_url VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    partner_name VARCHAR(100),
    terms_conditions TEXT,
    expiration_days INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_rewards_type ON rewards(reward_type);
CREATE INDEX idx_rewards_is_active ON rewards(is_active);

-- ========================================
-- USER REWARDS TABLE
-- ========================================
CREATE TABLE user_rewards (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    reward_id BIGINT NOT NULL,
    redemption_code VARCHAR(50) UNIQUE,
    redeemed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    used_at TIMESTAMP,
    expires_at TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_user_rewards_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_rewards_reward FOREIGN KEY (reward_id) REFERENCES rewards(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_rewards_user_id ON user_rewards(user_id);
CREATE INDEX idx_user_rewards_reward_id ON user_rewards(reward_id);
CREATE INDEX idx_user_rewards_status ON user_rewards(status);

-- ========================================
-- SCREEN TIME ENTRIES TABLE
-- ========================================
CREATE TABLE screen_time_entries (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    total_minutes INTEGER NOT NULL,
    device_type VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_screen_time_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_screen_time_user_date UNIQUE (user_id, date)
);

CREATE INDEX idx_screen_time_user_id ON screen_time_entries(user_id);
CREATE INDEX idx_screen_time_date ON screen_time_entries(date);

-- ========================================
-- APP USAGE TABLE
-- ========================================
CREATE TABLE app_usage (
    id BIGSERIAL PRIMARY KEY,
    screen_time_entry_id BIGINT NOT NULL,
    app_name VARCHAR(100) NOT NULL,
    app_category VARCHAR(50),
    usage_minutes INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_usage_screen_time FOREIGN KEY (screen_time_entry_id) REFERENCES screen_time_entries(id) ON DELETE CASCADE
);

CREATE INDEX idx_app_usage_screen_time_id ON app_usage(screen_time_entry_id);

-- ========================================
-- SENSORY CONTENT TABLE
-- ========================================
CREATE TABLE sensory_content (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    sense_type VARCHAR(20) NOT NULL,
    content_url VARCHAR(500) NOT NULL,
    thumbnail_url VARCHAR(500),
    duration_seconds INTEGER,
    tags VARCHAR(200),
    stress_level_min INTEGER CHECK (stress_level_min BETWEEN 1 AND 10),
    stress_level_max INTEGER CHECK (stress_level_max BETWEEN 1 AND 10),
    is_active BOOLEAN NOT NULL DEFAULT true,
    view_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sensory_content_sense_type ON sensory_content(sense_type);
CREATE INDEX idx_sensory_content_stress_levels ON sensory_content(stress_level_min, stress_level_max);
CREATE INDEX idx_sensory_content_is_active ON sensory_content(is_active);

-- ========================================
-- USER SENSORY PREFERENCES TABLE
-- ========================================
CREATE TABLE user_sensory_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content_id BIGINT NOT NULL,
    favorite BOOLEAN NOT NULL DEFAULT false,
    play_count INTEGER NOT NULL DEFAULT 0,
    last_played_at TIMESTAMP,
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sensory_prefs_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_sensory_prefs_content FOREIGN KEY (content_id) REFERENCES sensory_content(id) ON DELETE CASCADE,
    CONSTRAINT uk_sensory_prefs_user_content UNIQUE (user_id, content_id)
);

CREATE INDEX idx_sensory_prefs_user_id ON user_sensory_preferences(user_id);
CREATE INDEX idx_sensory_prefs_content_id ON user_sensory_preferences(content_id);

-- ========================================
-- TRIGGER FOR UPDATED_AT TIMESTAMP
-- ========================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_user_profiles_updated_at BEFORE UPDATE ON user_profiles
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_poi_updated_at BEFORE UPDATE ON points_of_interest
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_quests_updated_at BEFORE UPDATE ON quests
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_rewards_updated_at BEFORE UPDATE ON rewards
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_sensory_content_updated_at BEFORE UPDATE ON sensory_content
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_sensory_prefs_updated_at BEFORE UPDATE ON user_sensory_preferences
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();