-- Ensure repeatable migration is really repeated on every start
-- ${flyway:timestamp}

CREATE OR REPLACE PROCEDURE delete_all_progress(_project varchar, _environment varchar, _client varchar)
LANGUAGE SQL
AS $$
DELETE FROM
    deployment_progress
WHERE
    dpl_id IN (
    SELECT
        dpl_id
    FROM
        deployments_view
    WHERE
        project = _project
    AND environment = _environment
    AND client = _client
);
$$;