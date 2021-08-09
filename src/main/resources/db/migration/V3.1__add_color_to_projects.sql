ALTER TABLE projects ADD color VARCHAR(20);

COMMENT ON COLUMN projects.color IS 'Project color, format: "R,G,B".';