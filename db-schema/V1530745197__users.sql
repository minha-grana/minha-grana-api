SET SCHEMA 'minha-grana';

CREATE TABLE "minha-grana"."users" (
    id uuid PRIMARY KEY,
    created_date timestamptz NOT NULL,
    name text NOT NULL,
    email text NOT NULL
);
