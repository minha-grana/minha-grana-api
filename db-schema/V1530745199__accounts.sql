set schema 'minha-grana';

create table "accounts" (
	account_id uuid PRIMARY KEY,
	some_timestamp timestamptz NOT NULL,
	some_text text,
	some_boolean boolean
);
