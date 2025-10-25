create extension if not exists pgcrypto;

create table if not exists users (
    id uuid primary key default gen_random_uuid(),
    email varchar(255) unique not null,
    password_hash varchar(255) not null,
    created_at timestamptz not null default now()
);