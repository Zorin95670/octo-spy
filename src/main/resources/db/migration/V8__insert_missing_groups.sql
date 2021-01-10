INSERT INTO groups (pro_id) SELECT pro_id FROM projects;

INSERT INTO project_groups (grp_id, pro_id)
SELECT
    groups.grp_id,
    projects.pro_id
FROM
    projects
LEFT JOIN
    groups
ON
    projects.pro_id = groups.pro_id;