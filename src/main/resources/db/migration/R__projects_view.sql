DROP VIEW IF EXISTS projects_view;

CREATE VIEW projects_view AS
SELECT
  projects.pro_id                                AS "pro_id",
  projects.name                                  AS "name",
  COALESCE(projects.color, master_project.color) AS "color",
  master_project.name                            AS "master_project",
  master_project.color                           AS "master_project_color",
  projects.insert_date                           AS "insert_date",
  projects.update_date                           AS "update_date"
FROM
	projects
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