CREATE TABLE IF NOT EXISTS environments (
  env_id      SERIAL PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  insert_date TIMESTAMP NOT NULL DEFAULT now(),
  update_date TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON COLUMN environments.env_id      IS 'Primary key.';
COMMENT ON COLUMN environments.name        IS 'Environement name';
COMMENT ON COLUMN environments.insert_date IS 'Creation date of this row.';
COMMENT ON COLUMN environments.update_date IS 'Last update date of this row.';

INSERT INTO environments (name) VALUES
  ('Development'),
  ('Integration'),
  ('Pre-production'),
  ('Production');
