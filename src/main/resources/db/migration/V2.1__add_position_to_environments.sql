ALTER TABLE environments ADD position integer default 0;

COMMENT ON COLUMN environments.position IS 'Sort position.';

UPDATE environments SET position = 0 WHERE name = 'Development';
UPDATE environments SET position = 1 WHERE name = 'Integration';
UPDATE environments SET position = 2 WHERE name = 'Pre-production';
UPDATE environments SET position = 3 WHERE name = 'Production';