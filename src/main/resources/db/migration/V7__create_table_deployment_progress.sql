CREATE TABLE IF NOT EXISTS deployment_progress (
  dpg_id      SERIAL PRIMARY KEY,
  dpl_id      INTEGER REFERENCES deployments(dpl_id) ON DELETE CASCADE NOT NULL,
  insert_date TIMESTAMP NOT NULL DEFAULT now(),
  update_date TIMESTAMP NOT NULL DEFAULT now(),
  UNIQUE(dpl_id)
);

COMMENT ON COLUMN deployment_progress.dpg_id      IS 'Primary key.';
COMMENT ON COLUMN deployment_progress.dpl_id      IS 'Index of deployment.';
COMMENT ON COLUMN deployment_progress.insert_date IS 'Creation date of this row.';
COMMENT ON COLUMN deployment_progress.update_date IS 'Last update date of this row.';
