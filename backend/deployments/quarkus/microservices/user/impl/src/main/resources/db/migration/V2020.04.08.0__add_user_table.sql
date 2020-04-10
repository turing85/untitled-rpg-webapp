CREATE TABLE public.user (
  id BIGINT CONSTRAINT user_pk_id PRIMARY KEY,
  name VARCHAR(255) NOT NULL CONSTRAINT user_unique_name UNIQUE,
  email VARCHAR(255) NOT NULL CONSTRAINT user_unique_email UNIQUE,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  preferred_language_tag VARCHAR(5) NOT NULL DEFAULT 'en-US',
  birth_date DATE,
  bio TEXT,
  avatar BYTEA
);

CREATE SEQUENCE user_seq_id OWNED BY public.user.id;