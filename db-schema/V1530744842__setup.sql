CREATE USER "minha-grana-api" WITH password 'minha-grana-api';

CREATE SCHEMA "minha-grana";

GRANT USAGE ON SCHEMA "minha-grana" TO "minha-grana-api";
ALTER USER "minha-grana-api" SET search_path = 'minha-grana, public';

ALTER DEFAULT PRIVILEGES IN SCHEMA "minha-grana" GRANT SELECT, UPDATE, INSERT, DELETE ON TABLES TO "minha-grana-api";
ALTER DEFAULT PRIVILEGES IN SCHEMA "minha-grana" GRANT USAGE ON SEQUENCES TO "minha-grana-api";

ALTER DEFAULT PRIVILEGES REVOKE EXECUTE ON FUNCTIONS FROM PUBLIC;
REVOKE EXECUTE ON ALL FUNCTIONS IN SCHEMA PUBLIC FROM PUBLIC;