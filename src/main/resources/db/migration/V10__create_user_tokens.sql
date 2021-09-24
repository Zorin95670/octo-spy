CREATE TABLE IF NOT EXISTS user_tokens (
  ust_id              SERIAL PRIMARY KEY,
  usr_id              INTEGER REFERENCES users(usr_id) ON DELETE CASCADE NOT NULL,
  name                VARCHAR(255) NOT NULL,
  token               TEXT UNIQUE,
  insert_date         TIMESTAMP NOT NULL DEFAULT now(),
  update_date         TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON COLUMN user_tokens.ust_id      IS 'Primary key.';
COMMENT ON COLUMN user_tokens.usr_id      IS 'Index of user.';
COMMENT ON COLUMN user_tokens.name        IS 'Token name';
COMMENT ON COLUMN user_tokens.token       IS 'Token value, store with crypt and bf.';
COMMENT ON COLUMN user_tokens.insert_date IS 'Creation date of this row.';
COMMENT ON COLUMN user_tokens.update_date IS 'Last update date of this row.';
