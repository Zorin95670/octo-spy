CREATE TABLE IF NOT EXISTS project_groups (
  gpr_id      SERIAL PRIMARY KEY,
  grp_id      INTEGER REFERENCES groups(grp_id) ON DELETE CASCADE NOT NULL,
  pro_id      INTEGER REFERENCES projects(pro_id) ON DELETE CASCADE NOT NULL,
  insert_date TIMESTAMP NOT NULL DEFAULT now(),
  update_date TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON COLUMN project_groups.gpr_id      IS 'Primary key.';
COMMENT ON COLUMN project_groups.gpr_id      IS 'Index of group.';
COMMENT ON COLUMN project_groups.pro_id      IS 'Index of project.';
COMMENT ON COLUMN project_groups.insert_date IS 'Creation date of this row.';
COMMENT ON COLUMN project_groups.update_date IS 'Last update date of this row.';
