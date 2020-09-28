CREATE TABLE IF NOT EXISTS projects (
  pro_id      SERIAL PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  insert_date TIMESTAMP NOT NULL DEFAULT now(),
  update_date TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON COLUMN projects.pro_id      IS 'Primary key.';
COMMENT ON COLUMN projects.name        IS 'Project name';
COMMENT ON COLUMN projects.insert_date IS 'Creation date of this row.';
COMMENT ON COLUMN projects.update_date IS 'Last update date of this row.';
