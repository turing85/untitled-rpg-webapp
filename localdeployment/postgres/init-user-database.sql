CREATE USER rpg_user PASSWORD 'rpg_user';
CREATE DATABASE "user";
GRANT ALL PRIVILEGES ON DATABASE "user" TO rpg_user;
\c "user";
CREATE TABLE "user" (
  id BIGINT PRIMARY KEY
);
