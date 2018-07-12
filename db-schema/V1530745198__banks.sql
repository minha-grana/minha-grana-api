SET SCHEMA 'minha-grana';

CREATE TABLE "minha-grana"."banks" (
    id uuid PRIMARY KEY,
    code text NOT NULL,
    name text NOT NULL
);
