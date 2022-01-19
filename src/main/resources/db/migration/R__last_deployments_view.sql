-- Ensure repeatable migration is really repeated on every start
-- ${flyway:timestamp}

DROP VIEW IF EXISTS last_deployments_view;

CREATE VIEW last_deployments_view AS
SELECT
    deployments.dpl_id                             AS "dpl_id",
    deployments.pro_id                             AS "pro_id",
    projects.name                                  AS "project",
    COALESCE(projects.color, master_project.color) AS "color",
    master_project.name                            AS "master_project",
    master_project.color                           AS "master_project_color",
    projects.pro_id = master_project.pro_id        AS "on_master_project",
    environments.name                              AS "environment",
    deployments.version                            AS "version",
    deployments.client                             AS "client",
    deployments.insert_date                        AS "insert_date",
    deployments.update_date                        AS "update_date",
    deployment_progress.dpg_id IS NOT NULL         AS "in_progress"
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
    environments.env_id = deployments.env_id
LEFT OUTER JOIN
    deployment_progress
ON
    deployment_progress.dpl_id = deployments.dpl_id
LEFT OUTER JOIN
	project_groups
ON
    projects.pro_id = project_groups.pro_id
LEFT OUTER JOIN
	groups
ON
    groups.grp_id = project_groups.grp_id
LEFT OUTER JOIN
	projects master_project
ON
    groups.pro_id = master_project.pro_id
WHERE
    deployments.dpl_id IS NOT NULL;
