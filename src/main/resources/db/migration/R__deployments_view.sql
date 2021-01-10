DROP VIEW IF EXISTS deployments_view;

CREATE VIEW deployments_view AS
SELECT
    deployments.dpl_id                     AS "dpl_id",
    projects.name                          AS "project",
    master_project.name                    AS "master_project",
    environments.name                      AS "environment",
    deployments.version                    AS "version",
    deployments.client                     AS "client",
    deployments.alive                      AS "alive",
    deployments.insert_date                AS "insert_date",
    deployment_progress.dpg_id IS NOT NULL AS "in_progress"
FROM
    deployments
LEFT OUTER JOIN
    projects
ON
    projects.pro_id = deployments.pro_id
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
    groups.pro_id = master_project.pro_id;
