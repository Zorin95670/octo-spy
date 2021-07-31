CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS users (
  usr_id              SERIAL PRIMARY KEY,
  authentication_type VARCHAR(100) NOT NULL,
  login               VARCHAR(255) NOT NULL,
  firstname           VARCHAR(255),
  lastname            VARCHAR(255),
  password            TEXT,
  email               TEXT,
  active              BOOLEAN DEFAULT TRUE,
  insert_date         TIMESTAMP NOT NULL DEFAULT now(),
  update_date         TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON COLUMN users.usr_id              IS 'Primary key.';
COMMENT ON COLUMN users.authentication_type IS 'Type of authentication.';
COMMENT ON COLUMN users.login               IS 'User login.';
COMMENT ON COLUMN users.firstname           IS 'User first name.';
COMMENT ON COLUMN users.lastname            IS 'User last name.';
COMMENT ON COLUMN users.password            IS 'User password, store with crypt and bf.';
COMMENT ON COLUMN users.email               IS 'User email.';
COMMENT ON COLUMN users.active              IS 'Can user be used for authentication.';
COMMENT ON COLUMN users.insert_date         IS 'Creation date of this row.';
COMMENT ON COLUMN users.update_date         IS 'Last update date of this row.';

INSERT INTO users
	(login, firstname, lastname, password, active, authentication_type, email) 
VALUES
	('admin', 'Administrator', NULL, crypt('admin', gen_salt('bf')), TRUE, 'LOCAL', 'no-reply@change.it');
	