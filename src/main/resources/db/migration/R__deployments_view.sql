DROP VIEW IF EXISTS deployments_view;

CREATE OR REPLACE VIEW deployments_view AS
SELECT
  deployments.dpl_id      AS "dpl_id",
  projects.name           AS "project",
  environments.name       AS "environment",
  deployments.version     AS "version",
  deployments.client      AS "client",
  deployments.insert_date AS "insert_date"
FROM
  projects
LEFT OUTER JOIN (
  SELECT
    MAX(insert_date) AS "insert_date",
    pro_id,
    env_id,
    client
  FROM
    deployments
  WHERE
    alive = true
  GROUP BY
    pro_id,
    env_id,
    client) LAST_DEPLOYMENT
ON
  projects.pro_id = LAST_DEPLOYMENT.pro_id
LEFT OUTER JOIN
  deployments
ON
  deployments.insert_date = LAST_DEPLOYMENT.insert_date
AND deployments.pro_id = LAST_DEPLOYMENT.pro_id
AND deployments.env_id = LAST_DEPLOYMENT.env_id
AND deployments.client = LAST_DEPLOYMENT.client
AND deployments.alive = true
LEFT OUTER JOIN
  environments
ON
  environments.env_id = deployments.env_id;
