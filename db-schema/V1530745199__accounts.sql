SET SCHEMA 'minha-grana';

CREATE TABLE "minha-grana"."accounts" (
	id uuid PRIMARY KEY,
	created_date timestamptz NOT NULL,
	user_id uuid REFERENCES users (id) NOT NULL,
	bank_id uuid REFERENCES banks (id) NOT NULL,
	agency_number text,
	account_number text
);

CREATE INDEX ON "minha-grana"."accounts" (user_id);
