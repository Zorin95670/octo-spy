-- Ensure repeatable migration is really repeated on every start
-- ${flyway:timestamp}

DROP VIEW IF EXISTS information_view;

CREATE VIEW information_view AS
SELECT
    1         AS "id",
    version() AS "version";
