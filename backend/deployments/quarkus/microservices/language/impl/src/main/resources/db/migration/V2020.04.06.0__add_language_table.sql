CREATE TABLE public.language (
  tag VARCHAR(5) CONSTRAINT language_pk_tag PRIMARY KEY,
  name VARCHAR(255) NOT NULL CONSTRAINT language_unique_name UNIQUE
);