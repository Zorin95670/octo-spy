CREATE TABLE IF NOT EXISTS dashboards (
  dbd_id              SERIAL PRIMARY KEY,
  usr_id              INTEGER REFERENCES users(usr_id) ON DELETE CASCADE NOT NULL,
  name                VARCHAR(255) NOT NULL,
  parameters          JSONB NOT NULL,
  visible             BOOLEAN default false,
  can_be_deleted      BOOLEAN default true,
  insert_date         TIMESTAMP NOT NULL DEFAULT now(),
  update_date         TIMESTAMP NOT NULL DEFAULT now()
);

COMMENT ON COLUMN dashboards.dbd_id         IS 'Primary key.';
COMMENT ON COLUMN dashboards.usr_id         IS 'Index of user (owner).';
COMMENT ON COLUMN dashboards.name           IS 'Dashboard display name';
COMMENT ON COLUMN dashboards.parameters     IS 'Query parameters to load the dashboard.';
COMMENT ON COLUMN dashboards.visible        IS 'Visibility of the dashboard.';
COMMENT ON COLUMN dashboards.can_be_deleted IS 'Able to delete the dashboard.';
COMMENT ON COLUMN dashboards.insert_date    IS 'Creation date of this row.';
COMMENT ON COLUMN dashboards.update_date    IS 'Last update date of this row.';

INSERT INTO dashboards(usr_id, name, parameters, visible, can_be_deleted) VALUES
(
  (SELECT usr_id FROM users WHERE login = 'admin'),
 'All master projects',
 '{"onMasterProject": true}',
 true,
 false
);
