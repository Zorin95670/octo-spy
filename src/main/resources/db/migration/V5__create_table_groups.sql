CREATE TABLE IF NOT EXISTS groups (
  grp_id      SERIAL PRIMARY KEY,
  pro_id      INTEGER REFERENCES projects(pro_id) ON DELETE CASCADE NOT NULL,
  insert_date TIMESTAMP NOT NULL DEFAULT now(),
  update_date TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON COLUMN groups.grp_id      IS 'Primary key.';
COMMENT ON COLUMN groups.pro_id      IS 'Index of project.';
COMMENT ON COLUMN groups.insert_date IS 'Creation date of this row.';
COMMENT ON COLUMN groups.update_date IS 'Last update date of this row.';
