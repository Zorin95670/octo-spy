CREATE TABLE IF NOT EXISTS deployments (
  dpl_id      SERIAL PRIMARY KEY,
  env_id      INTEGER REFERENCES  environments(env_id) ON DELETE CASCADE NOT NULL,
  pro_id      INTEGER REFERENCES  projects(pro_id) ON DELETE CASCADE NOT NULL,
  version     VARCHAR(100) NOT NULL,
  client      VARCHAR(100) NOT NULL DEFAULT 'Internal',
  alive       BOOLEAN DEFAULT TRUE,
  insert_date timestamp NOT NULL DEFAULT now(),
  update_date timestamp NOT NULL DEFAULT now()
);

COMMENT ON COLUMN deployments.dpl_id      IS 'Primary key.';
COMMENT ON COLUMN deployments.env_id      IS 'Index of environment.';
COMMENT ON COLUMN deployments.pro_id      IS 'Index of project.';
COMMENT ON COLUMN deployments.version     IS 'Version of deployed project.';
COMMENT ON COLUMN deployments.client      IS 'Client of deployed project.';
COMMENT ON COLUMN deployments.alive       IS 'Indicate if deployment is still active.';
COMMENT ON COLUMN deployments.insert_date IS 'Creation date of this row.';
COMMENT ON COLUMN deployments.update_date IS 'Last update date of this row.';
